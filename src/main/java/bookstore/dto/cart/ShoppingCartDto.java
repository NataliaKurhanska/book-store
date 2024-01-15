package bookstore.dto.cart;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long userId;
    private List<CartItemDto> cartItems;
}
