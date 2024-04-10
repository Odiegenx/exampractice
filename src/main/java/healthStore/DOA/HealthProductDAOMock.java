package healthStore.DOA;

import healthStore.DTO.HealthProductDTO;
import healthStore.exceptions.ApiException;
import healthStore.persistance.User;
import jakarta.persistence.EntityNotFoundException;
import updater.Updater;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HealthProductDAOMock {
    static List<HealthProductDTO> productDTOList = new ArrayList<>();

    private static HealthProductDAOMock instance;
    public static HealthProductDAOMock getInstance(){
        if(instance == null){
            instance = new HealthProductDAOMock();
        }
        return instance;
    }
    public List<HealthProductDTO> getAll(){
        return  productDTOList;
    }
    public HealthProductDTO getById(int id) throws ApiException {
        HealthProductDTO toFind = null;
        for(int i = 0; i < productDTOList.size();i++){
            if(productDTOList.get(i).getId() == id){
                toFind = productDTOList.get(i);
            }
        }
        if(toFind != null) {
            return toFind;
        }else {
            throw new ApiException(404,"No productFound with that id");
        }
    }
    public List<HealthProductDTO> getByCategory(String category) throws ApiException {
        List<HealthProductDTO> foundProducts = new ArrayList<>();
        for (HealthProductDTO h: productDTOList) {
            if(h.getCategory().equals(category.toUpperCase())){
                foundProducts.add(h);
            }
        }
        if(!foundProducts.isEmpty()){
            return foundProducts;
        }else {
            throw new ApiException(404,"No Items found the the requested category");
        }
    }
    public HealthProductDTO create(HealthProductDTO healthProduct) throws ApiException {
        // TODO fix this
        if(!productDTOList.contains(healthProduct)){
            healthProduct.setId(productDTOList.size()+1);
            productDTOList.add(healthProduct);
            return healthProduct;
        }else {
            throw new ApiException(500,"Product Already exists");
        }
    }
    public HealthProductDTO update(HealthProductDTO healthProduct,int id){
        int productIndex = id-1;
        HealthProductDTO found = productDTOList.get(productIndex);
        HealthProductDTO foundUpdated = Updater.updateFromDTO(found,healthProduct);
        productDTOList.set(productIndex,foundUpdated);
        return foundUpdated;
    }
     HealthProductDTO delete(int id) throws ApiException {
        HealthProductDTO toRemove = null;
        for(int i = 0; i < productDTOList.size();i++){
            if(productDTOList.get(i).getId() == id){
                toRemove = productDTOList.remove(i);
            }
        }
        if(toRemove != null) {
            return toRemove;
        }else {
            throw new ApiException(404,"No productFound with that id");
        }
    }
    List<HealthProductDTO> getTwoWeeksToExpire(){
        return null;
    } // returns all health products  that expire within two weeks
}
