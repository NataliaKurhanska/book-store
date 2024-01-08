package bookstore.service;

import bookstore.dto.cart.CartItemDto;
import bookstore.dto.cart.CreateCartItemRequestDto;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.model.User;

public interface ShoppingCartService {
    void registerNewShoppingCart(User user);

    ShoppingCartDto getByUser(User user);

    CartItemDto updateCartItemsQuantity(Long cartItemId, int quantity);

    void addItemToCart(User user, CreateCartItemRequestDto cartItemDto);

    void deleteItemFromCart(User user, Long cartItemId);
}
