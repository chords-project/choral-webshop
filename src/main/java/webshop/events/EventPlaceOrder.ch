package webshop.events;

class EventPlaceOrder@A extends Event@A {
    public String@A userID;
    public Cart@A cart;
    public Order@A order;
    public String@A shippingAddress;

    public EventPlaceOrder() {
        this.userID = null@A;
        this.cart = null@A;
        this.order = null@A;
        this.shippingAddress = null@A;
    }

    public void addUserID(String@A userID) {
        this.userID = userID;
    }

    public void addUserCart(Cart@A cart) {
        this.cart = cart;
    }

    public void addOrder(Order@A order) {
        this.order = order;
    }

    public void addShipment(String@A address) {
        this.shippingAddress = address;
    }

    public Command@A getCommand() {
        return Command@A.PLACE_ORDER;
    }
}