package acetoy.pageObject;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Customer {
   // custom feeder randomly to regenerate users
    public static Iterator<Map<String, Object>> loginFeeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                        Random rand = new Random();
                        int userId= rand.nextInt(3 - 1 + 1)+ 1;

                HashMap<String,Object> hmap = new HashMap<String,Object>();
                hmap.put("userId", "user"+ userId);
                hmap.put("password", "pass");
                return hmap;
                    }).iterator();

    public static ChainBuilder Login=
            feed(loginFeeder)
                    .exec(
                    http("Login")
                            .post("/login")
                            .formParam("_csrf", "#{csrfToken}")
                            .formParam("username", "#{userId}")
                            .formParam("password", "#{password}")
                            .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))
            )
                    .exec(session -> session.set("customerLoggedIn", true));


    public static ChainBuilder LogOut=
            randomSwitch().on(new Choice.WithWeight( 10 , exec( http("Logout")
                    .post("/logout")
                    .formParam("_csrf", "#{csrfTokenLoggedIn}")
                    .check(css("#LoginLink").is("Login")))));

}
