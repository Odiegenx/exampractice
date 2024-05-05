package healthStore;


import healthStore.DOA.HealthProductDAO;
import healthStore.DOA.HealthProductDAOMock;
import healthStore.DOA.iDAO;
import healthStore.DOA.iDAOMock;
import healthStore.DTO.HealthProductDTO;
import healthStore.config.ApplicationConfig;
import healthStore.config.HealthProductRoutes;
import healthStore.exceptions.ApiException;
import healthStore.model.HealthProduct;

public class Main {
    public static void main(String[] args){
        HealthProductDTO productDTO1 = new HealthProductDTO("Vitamins", "Multivitamin", 25.99, 20, "A comprehensive daily multivitamin");
        HealthProductDTO productDTO2 = new HealthProductDTO("Supplements", "Omega-3", 19.50, 15, "Fish oil supplement rich in omega-3");
        HealthProductDTO productDTO3 = new HealthProductDTO("Personal Care", "Aloe Vera Gel", 12.99, 5, "Soothing and moisturizing aloe vera gel");
        HealthProductDTO productDTO4 = new HealthProductDTO("Vitamins", "Vitamin C", 9.99, 0, "Immune system support with vitamin C");
        HealthProductDTO productDTO5 = new HealthProductDTO("Supplements", "Protein Powder", 29.99, 120, "Whey protein powder for muscle recovery");

        iDAO<HealthProductDTO, Integer> dao = new HealthProductDAOMock();
        iDAO<HealthProduct, Integer> dao1 = new HealthProductDAO(false);


        HealthProductDAOMock healthProductDAOMock = HealthProductDAOMock.getInstance();
        try {
            healthProductDAOMock.create(productDTO1);
            healthProductDAOMock.create(productDTO2);
            healthProductDAOMock.create(productDTO3);
            healthProductDAOMock.create(productDTO4);
            healthProductDAOMock.create(productDTO5);
        }catch (ApiException e){
            // ignored just used for adding data for tests.
        }

        startServer(7070);
    }
    private static void startServer(int port) {
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance().getInstance();
        applicationConfig.initiateServer()
                .startServer(port)
                .setExceptionOverallHandling()
                .checkSecurityRoles()
                .setRoute(HealthProductRoutes.setRoutes());
    }
}
