package healthStore.DOA;

import healthStore.DTO.HealthProductDTO;
import healthStore.exceptions.ApiException;
import healthStore.model.HealthProduct;
import healthStore.persistance.User;

import java.util.List;

public class HealthProductDAO extends DAO<HealthProduct,Integer> {

    private static HealthProductDAO instance;
    public HealthProductDAO(boolean isTesting) {
        super(HealthProduct.class,isTesting);
    }
    public static HealthProductDAO getInstance(Boolean isTesting){
        if(instance == null){
            instance = new HealthProductDAO(isTesting);
        }
        return instance;
    }

    @Override
    public List<HealthProductDTO> getByCategory(String category) throws ApiException {
        return List.of();
    }
}
