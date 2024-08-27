package webshop.cart;

import webshop.Util;
import webshop.channel.RpcChannel;

public class CartService@(Client, Cart) {
    private RpcChannel@(Client, Cart)<CartServiceMessage> ch;
    private CartState@Cart state;

    public CartService(RpcChannel@(Client, Cart)<CartServiceMessage> ch) {
        this.ch = ch;
        this.state = new CartState@Cart();
    }

    public void call(CartServiceMessage@Client msg) {
        Util@Client.<CartServiceMessageAddItem>downcast(msg);

        /*switch (msg.getCommand()) {
        case ADD_ITEM -> {
            //Util.<CartServiceMessageAddItem>downcast(msg);
            //ch.<CartServiceMessageAddItem>tselect();
        }*/
        }
    }
}