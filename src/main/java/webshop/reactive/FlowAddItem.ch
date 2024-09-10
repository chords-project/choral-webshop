package webshop.reactive;

import choral.channels.DiChannel;

import webshop.common.models.*;

public class FlowAddItem@(Client, Cart) {
    ClientState@Client clientState;
    CartState@Cart cartState;

    DiChannel@(Client, Cart)<Object> ch_clientCart;
    DiChannel@(Cart, Client)<Object> ch_cartClient;

    public FlowAddItem(
        ClientState@Client clientState,
        CartState@Cart cartState,
        DiChannel@(Client, Cart)<Object> ch_clientCart,
        DiChannel@(Cart, Client)<Object> ch_cartClient
    ) {
        this.clientState = clientState;
        this.cartState = cartState;
        this.ch_clientCart = ch_clientCart;
        this.ch_cartClient = ch_cartClient;
    }

    public void addItem(CartItem@Client item) {
        System@Client.out.println("CLIENT: Add item started"@Client);
        System@Cart.out.println("CART: Add item started"@Cart);

        String@Cart userID_cart = ch_clientCart.<String>com(clientState.userID);
        CartItem@Cart item_cart = ch_clientCart.<CartItem>com(item);

        cartState.addItem(userID_cart, item_cart);

        // Send back a reply in order for the client to be sure that the transaction has finished.
        ch_cartClient.<Boolean>com(true@Cart);

        System@Client.out.println("CLIENT: Add item done"@Client);
        System@Cart.out.println("CART: Add item done"@Cart);
    }
}