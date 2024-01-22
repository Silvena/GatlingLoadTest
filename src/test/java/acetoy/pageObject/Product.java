package acetoy.pageObject;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static acetoy.session.UserSession.increaseItemsInBasketForSession;
import static acetoy.session.UserSession.increaseSessionBasketTotal;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Product {
    public static FeederBuilder<Object> productFeeder =
            jsonFile("data/productDetails.json").random();

    public static ChainBuilder loadColouredProductDetails =
            feed(productFeeder)
                    .exec( http("Load products details page- Product: #{name}")
                          .get("/product/#{slug}")
                          .check(css("#ProductDescription").isEL("#{description}"))
            );
    public static ChainBuilder addProductToCart =
            exec(increaseItemsInBasketForSession)
                  .exec( http("Add product with name #{name} Id #{id}")
                    .get("/cart/add/#{id}")
                    .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket")))
            .exec(increaseSessionBasketTotal);
/*
    public static ChainBuilder addProductToCart2 =
            feed(productFeeder)
                    .exec(
                    http("Add 2 product with Id #{id} and #{name}")
                    .get("/cart/add/#{id}")
                    .check(substring("You have <span>2</span> products in your Basket"))
            );
    public static ChainBuilder addProductToCart3 =
            feed(productFeeder)
                    .exec(
                    http("Add 3 product with Id #{id} and #{name}")
                    .get("/cart/add/#{id}")
                    .check(substring("You have <span>3</span> products in your Basket"))
            );
            */

}
