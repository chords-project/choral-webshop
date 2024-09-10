package webshop.reactive.driver;

import java.util.concurrent.ExecutionException;

import choral.channels.DiChannel_B;
import choral.lang.Unit;

public class ReactiveChannel_B<Action> implements DiChannel_B<Object> {
    private final IntegrityKey<Action> key;
    private final ReactiveQueue<Action> queue;

    public ReactiveChannel_B(IntegrityKey<Action> key, ReactiveQueue<Action> queue) {
        this.key = key;
        this.queue = queue;
    }

    @Override
    public <S> S com() {
        try {
            return queue.<S>recv(key);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <S> S com(Unit unit) {
        return com();
    }

    @Override
    public <T extends Enum<T>> T select() {
        return com();
    }

    @Override
    public <T extends Enum<T>> T select(Unit unit) {
        return select();
    }

}
