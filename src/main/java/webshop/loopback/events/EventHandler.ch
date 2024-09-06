package webshop.loopback.events;

public interface EventHandler@(Sender, Receiver)<T@X> {
    void on(T@Sender event);
}