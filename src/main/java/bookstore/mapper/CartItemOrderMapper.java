package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.model.CartItem;
import bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemOrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "book.price")
    OrderItem toOrderItem(CartItem cartItem);
}
