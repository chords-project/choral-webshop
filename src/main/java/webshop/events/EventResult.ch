package webshop.events;

class EventResult@(Client, Cart, Billing, Shipping) {
    public final Boolean@Client terminate_client;
    public final Boolean@Cart terminate_cart;
    public final Boolean@Billing terminate_billing;
    public final Boolean@Shipping terminate_shipping;

    public EventResult(
        Boolean@Client terminate_client,
        Boolean@Cart terminate_cart,
        Boolean@Billing terminate_billing,
        Boolean@Shipping terminate_shipping
    ) {
        this.terminate_client = terminate_client;
        this.terminate_cart = terminate_cart;
        this.terminate_billing = terminate_billing;
        this.terminate_shipping = terminate_shipping;
    }

    public EventResult() {
        this.terminate_client = false@Client;
        this.terminate_cart = false@Cart;
        this.terminate_billing = false@Billing;
        this.terminate_shipping = false@Shipping;
    }
}