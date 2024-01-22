package acetoy;

import acetoy.pageObject.*;
import acetoy.session.UserSession;
import acetoy.simulation.testPopulation;
import acetoy.simulation.testScenario;
import acetoy.simulation.userJourney;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AceToysSimulation extends Simulation {

  private static final String TEST_TYPE = System.getenv("TEST_TYPE");

  private static final String domainName = "acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + domainName)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,bg;q=0.8");

  {
    if (TEST_TYPE.equals("INSTANT_USERS")){
      setUp(testPopulation.instantUsers).protocols(httpProtocol);

    } else if (TEST_TYPE.equals("RAMP_USERS")){
      setUp(testPopulation.rampUsers).protocols(httpProtocol);
    } else if (TEST_TYPE.equals("COMPLEX_INJECTION")){
      setUp(testPopulation.complexInjection).protocols(httpProtocol);
    } else if (TEST_TYPE.equals("CLOSE_MODEL")){
      setUp(testPopulation.closeModel).protocols(httpProtocol);
    } else {
      setUp(testPopulation.instantUsers).protocols(httpProtocol);
    }


  }
}

/* private ScenarioBuilder scn = scenario("AceToysSimulation")
    .exec(UserSession.initSession)
    .exec(StaticPages.homePage)
    .pause(2)
    .exec(StaticPages.ourStoryPage)
    .pause(2)
    .exec(StaticPages.getInTouchPage)
    .pause(2)
    .exec(Category.productListByCategory)
    .pause(2)
    .exec(Product.loadColouredProductDetails)
    .pause(2)
    .exec(Product.addProductToCart)
    .pause(2)
    .exec(Category.cyclePagesOfProducts)
    .pause(2)
    .exec(Category.productListByCategory)
    .pause(2)
    .exec(Product.addProductToCart)
    .pause(2)
    .exec(Cart.viewCart)
    .pause(2)
    .exec(Cart.increaseQuantityInCard)
    .pause(2)
    .exec(Cart.increaseQuantityInCard)
    .pause(2)
    .exec(Cart.decreaseQuantityInCard)
    .pause(2)
    .exec(Cart.checkOut)
    .pause(2)
    .exec(Customer.LogOut);

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

    */