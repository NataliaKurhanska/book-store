package bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "shopping_carts")
@Data
public class ShoppingCart {
    @Id
    private Long id;
    @MapsId
    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "shoppingCart", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CartItem> cartItems;
}
