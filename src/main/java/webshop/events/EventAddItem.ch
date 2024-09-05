package webshop.events;

public class EventAddItem@A extends Event@A {
    public String@A userID;
    public final CartItem@A item;

    public EventAddItem(CartItem@A item) {
        this.userID = null@A;
        this.item = item;
    }

    public void addUserID(String@A userID) {
        this.userID = userID;
    }

    public Command@A getCommand() {
        return Command@A.ADD_ITEM;
    }
}