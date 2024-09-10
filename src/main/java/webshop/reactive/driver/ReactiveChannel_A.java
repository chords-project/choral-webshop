package webshop.reactive.driver;

import choral.channels.DiChannel_A;
import choral.lang.Unit;

public class ReactiveChannel_A<Action> implements DiChannel_A<Object> {

    public final IntegrityKey<Action> key;
    private final ReactiveQueue<Action> queue;

    public ReactiveChannel_A(IntegrityKey<Action> key, ReactiveQueue<Action> queue) {
        this.key = key;
        this.queue = queue;
    }

    @Override
    public <S> Unit com(S msg) {
        // Associates each message with the key
        queue.send(key, msg);
        return Unit.id;
    }

    @Override
    public <T extends Enum<T>> Unit select(T msg) {
        return com(msg);
    }
}
