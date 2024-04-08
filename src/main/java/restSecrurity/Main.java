package restSecrurity;

import config.ApplicationConfig;
import config.Routes;
import restSecrurity.controllers.SecurityController;

public class Main {
    public static void main(String[] args) {
        SecurityController securityController = new SecurityController(false);
        startServer(7007);
    }
    private static void startServer(int port) {
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(port)
                .setExceptionOverallHandling()
                .checkSecurityRoles()
                .setRoute(Routes.setRoutes());
    }
}
