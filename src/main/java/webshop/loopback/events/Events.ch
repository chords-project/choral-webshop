package webshop.loopback.events;

public interface Events@(A, B)<T@X> {
    void run(
        EventHandler@(A, B)<T> eventHandlerA,
        EventHandler@(B, A)<T> eventHandlerB,
        LocalHandler@A localHandlerA,
        LocalHandler@B localHandlerB
    );

    EventQueue@A<T> queueA();
    EventQueue@B<T> queueB();
}