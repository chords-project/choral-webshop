package webshop.events.channel;

import choral.lang.Unit;
import choral.runtime.Media.MessageQueue;

import java.util.concurrent.ExecutionException;

public class LocalTypeChannel<T> implements TypeSymChannelImpl<T> {

    private final MessageQueue queueOut;
    private final MessageQueue queueIn;

    public LocalTypeChannel(MessageQueue queueOut, MessageQueue queueIn) {
        this.queueOut = queueOut;
        this.queueIn = queueIn;
    }

    @Override
    public <S extends T> Unit com(S m) {
        queueOut.send(m);
        return Unit.id;
    }

    @Override
    public <M extends Enum<M>> Unit select(M m) {
        queueOut.send(m);
        return Unit.id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends T> S com() {
        try {
            return (S) queueIn.recv();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null; // it should never happen
    }

    @Override
    public <S extends T> S com(Unit unit) {
        return com();
    }

    @Override
    public <M extends Enum<M>> M select() {
        return com();
    }

    @Override
    public <M extends Enum<M>> M select(Unit unit) {
        return select();
    }

    @Override
    public <S extends T> Unit tselect(S m) {
        return com(m);
    }

    @Override
    public <S extends T> S tselect(Unit m) {
        return tselect();
    }

    @Override
    public <S extends T> S tselect() {
        return com();
    }
}
