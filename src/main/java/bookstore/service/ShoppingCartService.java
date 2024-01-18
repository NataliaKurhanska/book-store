package bookstore.service;

import bookstore.dto.cart.CartItemDto;
import bookstore.dto.cart.CreateCartItemRequestDto;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.model.User;

public interface ShoppingCartService {
    void registerNewShoppingCart(Long userId);

    ShoppingCartDto getByUser(User user);

    CartItemDto updateCartItemsQuantity(Long cartItemId, int quantity);

    CartItemDto addItemToCart(User user, CreateCartItemRequestDto cartItemDto);

    ShoppingCartDto deleteItemFromCart(User user, Long cartItemId);
}
