package acetoy.pageObject;

import io.gatling.javaapi.core.ChainBuilder;

import static acetoy.session.UserSession.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Cart {
    public static ChainBuilder viewCart =
            doIf(session -> !session.getBoolean("customerLoggedIn"))
                    .then(exec(Customer.Login))
                    .exec(
                    http("Cart view")
                    .get("/cart/view")

            );
    public static ChainBuilder increaseQuantityInCard=
            exec(increaseItemsInBasketForSession)
            .exec(increaseSessionBasketTotal)
                    .exec( http("Increase quantity for product with Id #{id} and #{name}")
                    .get("/cart/add/#{id}?cartPage=true")
                    .check(css("#grandTotal").isEL("$#{basketTotal}"))
            );
    public static  ChainBuilder decreaseQuantityInCard =
            exec(decreaseItemsInBasketForSession)
            .exec(decreaseSessionBasketTotal)
                    .exec( http("Decrease quantity from product with Id #{id} and #{name}")
                    .get("/cart/subtract/#{id}")
                    .check(css("#grandTotal").isEL("$#{basketTotal}"))
            );
    public static ChainBuilder checkOut =
            exec(
                    http("Cart checkout")
                            .get("/cart/checkout")
                            .check(substring("Your products are on their way to you now"))
            );
}
