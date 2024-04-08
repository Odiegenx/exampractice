package config;

import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import restSecrurity.controllers.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Routes {
    public static EndpointGroup setRoutes(){
        return () -> {
            before(SecurityController::authenticate);
            path("/security", getSecurityRoutes());
            path("/protected",getProtectedRoutes());
        };
    }
    private static EndpointGroup getSecurityRoutes() {
        return () -> {
            path("/auth", () -> {
                // before(SecurityController::authenticate);
                post("/register", SecurityController::register, RoleType.ANYONE);
                post("/login",SecurityController::login, RoleType.ANYONE);
            });
        };
    }
    private static EndpointGroup getProtectedRoutes(){
        return () -> {
            path("/protected",() ->{
                // before(SecurityController::authenticate);
                get("/user", ctx -> ctx.result("great success from User Route"),RoleType.USER);
                get("/admin", ctx -> ctx.result("great success from Admin Route"),RoleType.ADMIN);
            });
        };
    }
    public enum RoleType implements RouteRole {
        USER,
        ADMIN,
        ANYONE
    }
}
