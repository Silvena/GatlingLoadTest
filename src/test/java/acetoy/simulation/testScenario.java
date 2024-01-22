package acetoy.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class testScenario {

    private static final Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION")));

    public static ScenarioBuilder defaultLoadTest =
            scenario("Default Load Test")
                    .during(TEST_DURATION)
                    .on(randomSwitch()
                          .on(
                                  new Choice.WithWeight(60, exec(userJourney.browseStore)),
                                  new Choice.WithWeight(30, exec(userJourney.abandonBasket)),
                                  new Choice.WithWeight(10, exec(userJourney.completePurchase))
                          )
                    );

    public static ScenarioBuilder highPurchaseLoadTest =
            scenario("High Purchase Load Test")
                    .during(TEST_DURATION)
                    .on(randomSwitch()
                            .on(
                                    new Choice.WithWeight(30, exec(userJourney.browseStore)),
                                    new Choice.WithWeight(30, exec(userJourney.abandonBasket)),
                                    new Choice.WithWeight(40, exec(userJourney.completePurchase))
                            )
                    );
}
