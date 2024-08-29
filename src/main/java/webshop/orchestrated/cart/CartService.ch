package webshop.orchestrated.cart;

import choral.channels.SymChannel;

import java.util.List;

import webshop.orchestrated.cart.messages.CartMessage;
import webshop.orchestrated.cart.messages.AddItemReq;
import webshop.orchestrated.cart.messages.EmptyCartReq;
import webshop.orchestrated.cart.messages.GetCartReq;
import webshop.orchestrated.cart.messages.GetCartRes;

public class CartService@(Cart, Client) {
    private SymChannel@(Cart, Client)<Object> ch;
    private CartState@Cart state;

    public CartService(SymChannel@(Cart, Client)<Object> ch) {
        this.ch = ch;
        this.state = new CartState@Cart();
    }

    public void addItem(String@Client userID, CartItem@Client item) {
        AddItemReq@Cart req = ch.<AddItemReq>com(new AddItemReq@Client(userID, item));
        state.addItem(req.userID(), req.item());
    }

    public void emptyCart(String@Client userID) {
        EmptyCartReq@Cart req = ch.<EmptyCartReq>com(new EmptyCartReq@Client(userID));
        state.emptyCart(req.userID());
    }

    public List@Client<CartItem> getCart(String@Client userID) {
        GetCartReq@Cart req = ch.<GetCartReq>com(new GetCartReq@Client(userID));
        
        List@Cart<CartItem> userItems = this.state.getUserCart(req.userID());
        GetCartRes@Client res = ch.<GetCartRes>com(new GetCartRes@Cart(req.userID(), userItems));
        
        return res.items();
    }
}