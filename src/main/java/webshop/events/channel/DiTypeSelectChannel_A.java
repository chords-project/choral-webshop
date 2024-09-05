package webshop.events.channel;

import choral.lang.Unit;

public interface DiTypeSelectChannel_A<T> {
	public <S extends T> Unit tselect(S m);
}
