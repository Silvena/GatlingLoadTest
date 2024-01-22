package acetoy.pageObject;
import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {
    public static ChainBuilder homePage =
            exec(
                    http("Load home page")
                            .get("/")
                            .check(status().is(200))
                            .check(status().not(404),status().not(405))
                            .check(substring("<h3 class=\"display-4\" id=\"CategoryHeader\">Categories</h3>"))
                            .check(css("#_csrf", "content").saveAs("csrfToken"))

            );
    public static ChainBuilder ourStoryPage =
            exec(
            http("Our-story page")
                    .get("/our-story")
                    .check(regex("founded online in \\d{4}"))
    );
    public static ChainBuilder getInTouchPage =
            exec(
                    http("Get-in-touch page")
                            .get("/get-in-touch")
                            .check(substring("a real store!"))
            );
}
