package webshop.channel;

import choral.channels.SymChannelImpl;
import choral.lang.Unit;

public class RpcChannelImpl<Message> implements SymChannelImpl<Message> {

    @Override
    public <S extends Message> Unit com(S m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'com'");
    }

    @Override
    public <T extends Enum<T>> Unit select(T m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'select'");
    }

    @Override
    public <S extends Message> S com(Unit m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'com'");
    }

    @Override
    public <S extends Message> S com() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'com'");
    }

    @Override
    public <T extends Enum<T>> T select(Unit m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'select'");
    }

    @Override
    public <T extends Enum<T>> T select() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'select'");
    }

    public <M extends Message> Unit tselect(M message) {
        return com(message);
    }

    public <M extends Message> M tselect(Unit u) {
        return tselect();
    }

    public <M extends Message> M tselect() {
        return com();
    }

}
