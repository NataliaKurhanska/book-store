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
import bookstore.repository.BookRepository;
import bookstore.repository.CartItemRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.repository.UserRepository;
import bookstore.service.ShoppingCartService;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public void registerNewShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userRepository.getReferenceById(userId));
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void addItemToCart(User user, CreateCartItemRequestDto requestDto) {
        if (bookRepository.findBookById(requestDto.getBookId()).isEmpty()) {
            throw new EntityNotFoundException("Can't find book with id " + requestDto.getBookId());
        }
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        ShoppingCart shoppingCart = getShoppingCart(user);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
    }

    @Override
    public ShoppingCartDto getByUser(User user) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public CartItemDto updateCartItemsQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + cartItemId));
        cartItem.setQuantity(quantity);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteItemFromCart(User user, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + cartItemId));
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.deleteById(cartItemId);
    }

    private ShoppingCart getShoppingCart(User user) {
        if (shoppingCartRepository.findByUser(user).isEmpty()) {
            registerNewShoppingCart(user.getId());
        }
        return shoppingCartRepository.findByUser(user).get();
    }
}
