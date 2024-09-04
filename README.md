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
