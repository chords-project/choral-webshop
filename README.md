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
