package acetoy.simulation;

import acetoy.session.UserSession;
import io.gatling.javaapi.core.PopulationBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class testPopulation {

    private static final int USER_COUNT = Integer.parseInt(System.getenv("USERS"));
    private static final Duration RAMP_DURATION = Duration.ofSeconds(Integer.parseInt(System.getenv("RAMP_DURATION")));

    public static PopulationBuilder instantUsers =
        testScenario.defaultLoadTest
                .injectOpen(
                        nothingFor(5),
                        atOnceUsers(USER_COUNT));

    public static PopulationBuilder rampUsers =
            testScenario.defaultLoadTest
                    .injectOpen(
                            nothingFor(5),
                            rampUsers(10).during(RAMP_DURATION));

    public static PopulationBuilder complexInjection =
            testScenario.defaultLoadTest
                    .injectOpen(
                            constantUsersPerSec(10).during(20).randomized(),
                            rampUsersPerSec(10).to(20).during(20));

    public static PopulationBuilder closeModel=
            testScenario.highPurchaseLoadTest
                    .injectClosed(
                            constantConcurrentUsers(10).during(20),
                            rampConcurrentUsers(10).to(20).during(20)
                    );
}
