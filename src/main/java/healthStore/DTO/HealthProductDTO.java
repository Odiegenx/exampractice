package healthStore.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthProductDTO {
    Integer id;
    String category;
    String name;
    double price;
    double calories;
    String description;

    public HealthProductDTO(String category, String name, double price, double calories, String description) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.description = description;
    }
}
