package healthStore.DOA;

import healthStore.DTO.HealthProductDTO;
import healthStore.exceptions.ApiException;
import healthStore.model.HealthProduct;
import healthStore.model.Storage;

import java.util.List;
import java.util.Set;

public class StorageDAO extends DAO<Storage,Integer> {

    private static StorageDAO instance;
    public StorageDAO(Class<Storage> storageClass, boolean isTest) {
        super(storageClass, isTest);
    }
    public static StorageDAO getInstance(boolean isTest){
        if(instance == null){
            instance = new StorageDAO(Storage.class,isTest);
        }
        return instance;
    }

    public void addProductToStorage(int storageID,int productId){

    }
    public Set<HealthProduct> getProductsByStorageShelt(int storageId){
        return null;
    }

    @Override
    public List<HealthProductDTO> getByCategory(String category) throws ApiException {
        return List.of();
    }
}
