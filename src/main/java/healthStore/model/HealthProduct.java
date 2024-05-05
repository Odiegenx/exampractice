package healthStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "health_product")
@NoArgsConstructor
@AllArgsConstructor
public class HealthProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    String category;
    String name;
    double calories;
    double price;
    String description;

    @ManyToOne
    Storage storage;
}