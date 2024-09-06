package webshop.common.channel;

import choral.lang.Unit;

public interface DiTypeSelectChannel_B<T> {
	public <S extends T> S tselect(Unit m);

	public <S extends T> S tselect();
}
