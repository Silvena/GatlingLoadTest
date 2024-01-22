package acetoy.simulation;
import acetoy.pageObject.*;
import io.gatling.javaapi.core.ChainBuilder;
import java.time.Duration;
import static io.gatling.javaapi.core.CoreDsl.*;
import static acetoy.session.UserSession.*;

public class userJourney {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);  // 1sec
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder browseStore =
            exec(initSession)
                    .exec(StaticPages.homePage)
                    .pause(HIGH_PAUSE)
                    .exec(StaticPages.ourStoryPage)
                    .pause(LOW_PAUSE,HIGH_PAUSE) //pause will be in the middle of 1-3 sec
                    .exec(StaticPages.getInTouchPage)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .repeat(3).on(
                            exec(Category.productListByCategory)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(Category.cyclePagesOfProducts)
                                    .pause(LOW_PAUSE,HIGH_PAUSE)
                                    .exec(Product.loadColouredProductDetails)
                    );
    public static ChainBuilder abandonBasket =
            exec(initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Category.productListByCategory)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Product.loadColouredProductDetails)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Product.addProductToCart);

    public static ChainBuilder completePurchase =
            exec(initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Category.productListByCategory)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Product.loadColouredProductDetails)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Product.addProductToCart)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Cart.viewCart)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Cart.increaseQuantityInCard)
                    .pause(LOW_PAUSE)
                    .exec(Cart.decreaseQuantityInCard)
                    .pause(LOW_PAUSE)
                    .exec(Cart.checkOut)
                    .pause(LOW_PAUSE,HIGH_PAUSE)
                    .exec(Customer.LogOut);



    public static ChainBuilder test =
            repeat(4).on(
            exec(abandonBasket));

}
