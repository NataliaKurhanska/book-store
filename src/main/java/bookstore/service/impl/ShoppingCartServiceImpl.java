package bookstore.service.impl;

import bookstore.dto.cart.CartItemDto;
import bookstore.dto.cart.CreateCartItemRequestDto;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartItemMapper;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.CartItemRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void addItemToCart(User user, CreateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        cartItem.setShoppingCart(shoppingCart);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(savedCartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getByUser(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public CartItemDto updateCartItemsQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + cartItemId));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void deleteItemFromCart(User user, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + cartItemId));
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.deleteById(cartItemId);
    }
}
