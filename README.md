# Examples of modelling a web shop in Choral

This project explores different approaches to model a microservice web shop inspired by [microservices-demo](https://github.com/GoogleCloudPlatform/microservices-demo/tree/main) project.

## Installation

Make sure Choral and the associated Maven libraries are installed.

1. Follow the [installation guide](https://www.choral-lang.org/install.html) for Choral
2. Clone the [Choral repository](https://github.com/choral-lang/choral) and run `mvn install` to install the libraries locally.
   This step is needed since the Choral dependencies are not yet published to the Maven Central.

### Running the examples

The examples can now be executed by running the following commands.

```sh
$ make run-orchestrated
$ make run-choreographic
$ make run-events
```

## The examples

### [Orchestrated](./src/main/java/webshop/orchestrated/)

The orchestrated example models each service as a choreography, providing RPCs for each action available for each service.
This structures the code in a way that is close to the traditional OOP approach where each class has a single responsibility.

E.g. the [CheckoutService](./src/main/java/webshop/orchestrated/checkout/CheckoutService.ch), which is responsible for the logic of the checkout microservice, has a reference to the [CartService](./src/main/java/webshop/orchestrated/cart/CartService.ch),
such that when an order is placed on the checkout service, the products in the cart can easily be retrieved using an RPC to `CartService.getCart`.

The flow of messages is as follows.

```
Frontend ->
  Checkout -> Cart ->
  Checkout -> Billing ->
  Checkout -> Frontend
```

### [Choreographic](./src/main/java/webshop/choreographic/)

The choreographic example instead models everything in a single class, and instead of having RPCs that immediately reply to the caller, a request can flow through multiple services, and it is not necessarily the service that receives a request that replies to it later.

In this example the flow of a place order request goes as follows.

```
Client -> Cart -> Billing -> Shipping -> Client
```

### [Events](./src/main/java/webshop/events/)

This examples models the approach described in [this](https://arxiv.org/pdf/2303.03983) paper.

This example is an extension of the Choreographic example, where instead of only having a single action (to place an order),
this approach has multiple actions that can be run multiple times and in any order.
The client dictates what event to run next and the other processes follow.

The example has the following events.

- `ADD_ITEM`: Adds an item to the shopping cart of the client
- `PLACE_ORDER`: Buys and ships the items in the shopping cart
- `TERMINATE`: Exists the event loop and terminates the program

In order to implement knowledge-of-choice, the `@TypeSelectionMethod` annotation is used, which is not a part of the Choral standard library yet.
Therefore, a custom [LocalTypeChannel](./src/main/java/webshop/events/channel/LocalTypeChannel.java) has been implemented which extends the `LocalChannel` implementation with a `tselect` method as described in the paper above.

The main choreography is driven by the [EventHandler](./src/main/java/webshop/events/EventHandler.ch) in the `on(Event@Client event)` method.
This method is set up to run in a loop in the [`Main`](./src/main/java/webshop/events/Main.java) driver class.

An interesting note is that knowledge of choice could potentially be optimized in the `ADD_ITEM` event,
since the `Billing` and `Shipping` processes simply loop and do nothing in this event, it would have the same effect as never being notified about the event.
Being able to merge this, could be used as a pattern to only send messages to processes involved in the current event.

```java
void on(Event@Client event) {
  switch (event.getCommand()) {
    FIRST_EVENT -> {
      ch_AB.select(FIRST_EVENT);
      ch_BC.select(FIRST_EVENT);
      ch_CD.select(FIRST_EVENT);
      System@D.out.println("First event");
    },
    SECOND_EVENT -> {
      ch_AB.select(SECOND_EVENT);
      System@B.out.println("Second event");
    }
  }

  on(Client.next_event());
}
```

The example above lacks knowledge-of-choice in Choral, but there is no reason this could not run theoretically.

### [Loopback](./src/main/java/webshop/loopback)

An attempt to model requests between two services as an event loop.
More closely related to the IRC paper than the Events example is.

Only a single event loop from client to cart has been modeled,
but this shows the general design.

This approach uses a lot of extra code, and the benefits from choreographies are nearly lost,
since most events consists of a single message passing,
which could just as well have been made without choreographies.

### [Reactive](./src/main/java/webshop/reactive)

#### Background discussion

I think the most natural place to split a microservice project like this into different choreographies, is by modeling each request flow as its own choreography.
I.e. authenticate, add item to cart, place order, etc. would each be its own choreography involving the relevant roles needed to perform that action.

Making it reactive, I imagine that the first message received by each choreography could kick-start the choreography for that service.
That way services can sit idle until they receive the first message in the request flow where after the choreography to handle that request is started.
If another request flow is received - e.g. by another client - then a new choreography is spawned to handle that request concurrently.

Similar to Ozone, integrity keys could be associated with each request flow, in order to know which request flow a message is a part of.
That means, if a new integrity key is received, start a new choreography,
and if a previously seen key is received, continue the execution of the associated choreography.

##### Place order example

```
(1) Client -> (2) Cart -> (3) Billing -> (4) Cart
```

- The request flow is started by the Client, e.g. when the user clicks the button to place the order.
- The Client sends the first message in the choreography to the Cart, when Cart receives the message the choreography is started at the Cart role.
- The Cart now sends the next message to the Billing role, wherefrom the Billing choreography is started.
- Lastly, Billing sends a message back to Client with the result, but since this message is a part of the same choreography, the Client will simply continue the flow of the existing choreography instead of starting a new one.

#### Introduction

This example extends the [Choreographic](#choreographic) example by making it reactive.
The main idea is to "spawn" the projected choreography in a new thread when the first message is received.
In order to do this, an [integrity key](./src/main/java/webshop/reactive/driver/IntegrityKey.java) is send along with each message.
The recipient can then use this key to determine which running choreography is the intended recipient.
If no choreography is actively running for the given key, a new choreography is started to handle it.

The example consists of two choreographies, [`FlowAddItem`](./src/main/java/webshop/reactive/FlowAddItem.ch) and [`FlowPlaceOrder`](./src/main/java/webshop/reactive/FlowPlaceOrder.ch).
Each choreography is very simple to understand and only uses `DiChannel`s for communication.

The choreographies are made reactive by the [driver library](./src/main/java/webshop/reactive/driver/) written purely in Java.
The library consists of a custom reactive channel, that implements the `DiChannel` interface,
as well as a custom reactive queue, used by the channel implementation.

#### Putting everything together

Everything is combined in the [Main](./src/main/java/webshop/reactive/Main.java) class.
A new queue is created for each role, and each role registers the `onNewFlowReceived` callback on it.
This callback is called every time a message is received with a not previously seen integrity key for the given queue.
In this callback the integrity key is used to determine which choreography to spawn.
The correct choreography is instantiated with the same integrity key for its channels.
Lastly, the choreography is executed in a new thread where it will immediately receive the message.
