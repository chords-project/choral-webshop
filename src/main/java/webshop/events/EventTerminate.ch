package webshop.events;

class EventTerminate@A extends Event@A {
    public EventTerminate() {}

    public Command@A getCommand() {
        return Command@A.TERMINATE;
    }
}