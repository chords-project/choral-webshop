package webshop.choreographic;

import java.util.List;

import choral.runtime.LocalChannel.LocalChannel_A;
import choral.runtime.LocalChannel.LocalChannel_B;
import choral.runtime.Media.MessageQueue;
import choral.utils.Pair;
import webshop.common.models.BillingState;
import webshop.common.models.CartItem;
import webshop.common.models.CartState;
import webshop.common.models.ClientState;
import webshop.common.models.ShippingState;

public class Main {
        public static void main(String[] args) {
                System.out.println("Choreographic example");

                Pair<LocalChannel_A, LocalChannel_B> ch_ClientCart = makeChannel();
                Pair<LocalChannel_A, LocalChannel_B> ch_ClientShipping = makeChannel();
                Pair<LocalChannel_A, LocalChannel_B> ch_CartBilling = makeChannel();
                Pair<LocalChannel_A, LocalChannel_B> ch_BillingShipping = makeChannel();

                CartState cartState = new CartState();
                cartState.addItem("1000", new CartItem("product1", 1));

                Choreography_Client client = new Choreography_Client(ch_ClientCart.left(), ch_ClientShipping.left(),
                                new ClientState("1000"));
                Choreography_Cart cart = new Choreography_Cart(ch_ClientCart.right(), ch_CartBilling.left(), cartState);
                Choreography_Billing billing = new Choreography_Billing(ch_CartBilling.right(),
                                ch_BillingShipping.left(),
                                new BillingState());
                Choreography_Shipping shipping = new Choreography_Shipping(
                                ch_ClientShipping.right(), ch_BillingShipping.right(), new ShippingState());

                List<Runnable> services = List.of(
                                () -> client.placeOrder(),
                                () -> cart.placeOrder(),
                                () -> billing.placeOrder(),
                                () -> shipping.placeOrder());

                services.parallelStream().forEach(Runnable::run);
        }

        public static Pair<LocalChannel_A, LocalChannel_B> makeChannel() {
                MessageQueue msqA = new MessageQueue();
                MessageQueue msqB = new MessageQueue();

                return new Pair<LocalChannel_A, LocalChannel_B>(
                                new LocalChannel_A(msqA, msqB), new LocalChannel_B(msqB, msqA));
        }
}
