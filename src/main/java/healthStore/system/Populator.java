package healthStore.system;
import healthStore.config.HibernateConfig;
import healthStore.model.HealthProduct;
import healthStore.model.Storage;
import jakarta.persistence.EntityManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Populator {

    private static EntityManager entityManager;

    public static void populateDatabase(boolean isTest) {
        entityManager = HibernateConfig.getEntityManagerFactoryConfig(isTest).createEntityManager();
        // Create health products
        List<HealthProduct> healthProducts = createHealthProducts();

        // Create storage objects
        List<Storage> storages = createStorages(healthProducts);

        // Save health products and storages to the database
        for (HealthProduct healthProduct : healthProducts) {
            entityManager.persist(healthProduct);
        }

        for (Storage storage : storages) {
            entityManager.persist(storage);
        }
    }

    private static List<HealthProduct> createHealthProducts() {
        List<HealthProduct> healthProducts = new ArrayList<>();
        healthProducts.add(new HealthProduct(null, "Vitamins", "Multivitamin", 20, 25.99, "A comprehensive daily multivitamin", null));
        healthProducts.add(new HealthProduct(null, "Supplements", "Omega-3", 15, 19.50, "Fish oil supplement rich in omega-3", null));
        healthProducts.add(new HealthProduct(null, "Personal Care", "Aloe Vera Gel", 5, 12.99, "Soothing and moisturizing aloe vera gel", null));
        healthProducts.add(new HealthProduct(null, "Vitamins", "Vitamin C", 0, 9.99, "Immune system support with vitamin C", null));
        healthProducts.add(new HealthProduct(null, "Supplements", "Protein Powder", 120, 29.99, "Whey protein powder for muscle recovery", null));
        return healthProducts;
    }

    private static List<Storage> createStorages(List<HealthProduct> healthProducts) {
        List<Storage> storages = new ArrayList<>();

        // Create storage objects and assign health products to them
        for (int i = 0; i < 2; i++) {
            Storage storage = new Storage();
            storage.setUpdatedTimeStamp(LocalTime.now());
            storage.setTotalAmount(10);
            storage.setShelfNumber(i + 1);

            for (int j = 0; j < 2; j++) {
                storage.getHealthProduct().add(healthProducts.get(i * 2 + j));
            }
            storages.add(storage);
        }
        return storages;
    }
}


