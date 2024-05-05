package healthStore.DOA;

import healthStore.DTO.HealthProductDTO;
import healthStore.exceptions.ApiException;
import healthStore.persistance.User;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityNotFoundException;
import updater.Updater;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HealthProductDAOMock implements iDAO<HealthProductDTO,Integer>{
    static List<HealthProductDTO> productDTOList = new ArrayList<>();

    private static HealthProductDAOMock instance;

    public static HealthProductDAOMock getInstance(){
        if(instance == null){
            instance = new HealthProductDAOMock();
        }
        return instance;
    }
    public List<HealthProductDTO> getAll() throws ApiException {
        if (!productDTOList.isEmpty()) {
            return productDTOList;
        }else {
            throw new ApiException(HttpStatus.forStatus(500),"No information found, try again later");
        }
    }
    public HealthProductDTO getById(Integer id) throws ApiException {
        HealthProductDTO toFind = null;
        for(int i = 0; i < productDTOList.size();i++){
            if(productDTOList.get(i).getId() == id){
                toFind = productDTOList.get(i);
            }
        }
        if(toFind != null) {
            return toFind;
        }else {
            throw new ApiException(HttpStatus.forStatus(404),"No productFound with that id");
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
            throw new ApiException(HttpStatus.forStatus(404),"No Items found the the requested category");
        }
    }

    public HealthProductDTO create(HealthProductDTO healthProduct) throws ApiException {
        // TODO fix this
        if(!productDTOList.contains(healthProduct)){
            healthProduct.setId(productDTOList.size()+1);
            productDTOList.add(healthProduct);
            return healthProduct;
        }else {
            throw new ApiException(HttpStatus.forStatus(400),"Product Already exists");
        }
    }

    public HealthProductDTO update(HealthProductDTO healthProduct,Integer id) throws ApiException {
        int productIndex = id-1;
        if(productIndex <= productDTOList.size()) {
            HealthProductDTO found = productDTOList.get(productIndex);
            HealthProductDTO foundUpdated = Updater.updateFromDTO(found, healthProduct);
            productDTOList.set(productIndex, foundUpdated);
            return foundUpdated;
        }else {
            throw new ApiException(HttpStatus.forStatus(400),"No item with that id");
        }
    }
     public HealthProductDTO delete(Integer id) throws ApiException {
        HealthProductDTO toRemove = null;
        for(int i = 0; i < productDTOList.size();i++){
            if(productDTOList.get(i).getId() == id){
                toRemove = productDTOList.remove(i);
            }
        }
        if(toRemove != null) {
            return toRemove;
        }else {
            throw new ApiException(HttpStatus.forStatus(404),"No productFound with that id");
        }
    }
    List<HealthProductDTO> getTwoWeeksToExpire(){
        return null;
    } // returns all health products  that expire within two weeks

    public static List<HealthProductDTO> getProductListByCalorueCount(int count){
        return productDTOList.stream().filter(x -> x.getCalories() < 50).toList();
    }
    public static List<String> getProductNames(){
        return productDTOList.stream().map(x -> x.getName()).toList();
    }
    public static void getTotalPriceByCategory() {
        // Group health products by category
        Map<String, Double> totalPriceByCategory = productDTOList.stream()
                .collect(Collectors.groupingBy(HealthProductDTO::getCategory,
                        Collectors.summingDouble(HealthProductDTO::getPrice)));
        totalPriceByCategory.forEach((category, totalPrice) ->
                System.out.println("Category: " + category + ", Total Price: " + totalPrice));
    }
}
