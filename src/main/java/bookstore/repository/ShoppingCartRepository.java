package bookstore.repository;

import bookstore.model.ShoppingCart;
import bookstore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);
}
