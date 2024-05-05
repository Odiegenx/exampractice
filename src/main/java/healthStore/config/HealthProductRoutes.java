package healthStore.config;

import healthStore.controllers.HealthProductController;
import healthStore.controllers.SecurityController;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class HealthProductRoutes {
   private static HealthProductController healthProductController;
   private static SecurityController securityController;
    public static EndpointGroup setRoutes(){
        healthProductController = HealthProductController.getInstance(true,false);
        securityController = SecurityController.getInstance(false);
        return () -> {
            before(SecurityController::authenticate);
            path("/security", getSecurityRoutes());
            path("/protected",getProtectedRoutes());
            path("/healthshop", getHealthProductsRoutes());
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
                get("/user", ctx -> ctx.result("great success from User Route"),RoleType.USER);
                get("/admin", ctx -> ctx.result("great success from Admin Route"),RoleType.ADMIN);
            });
        };
    }

    private static EndpointGroup getHealthProductsRoutes(){
        return () -> {
            path("/api",() ->{
                get("/healthproducts", healthProductController.getAll(),RoleType.ANYONE);
                get("/healthproducts/{id}", healthProductController.getById(),RoleType.ANYONE);
                post("/healthproducts", healthProductController.create(),RoleType.ANYONE);
                put("/healthproducts/{id}", healthProductController.update(),RoleType.ANYONE);
            });
        };
    }
    public enum RoleType implements RouteRole {
        USER,
        ADMIN,
        ANYONE
    }
}
