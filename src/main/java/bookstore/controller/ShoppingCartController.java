package bookstore.controller;

import bookstore.dto.cart.CartItemDto;
import bookstore.dto.cart.CreateCartItemRequestDto;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.model.User;
import bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart", description = "Manage shopping cart")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "Get a list of cart items in user's shopping cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUser(user);
    }

    @PostMapping
    @Operation(summary = "Add a book to shopping cart",
            description = "Add a book to user's shopping cart")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public CartItemDto addItemtoShoppingCart(Authentication authentication,
                                      @RequestBody @Valid CreateCartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addItemToCart(user, requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update cart item's quantity",
            description = "Update cart item's quantity in shopping cart")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public CartItemDto updateCartItemsQuantity(@PathVariable Long cartItemId,
                                        @RequestParam("quantity")
                                        @Min(value = 1, message = "Quantity can't be less then 1")
                                        int quantity) {
        return shoppingCartService.updateCartItemsQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete book from shopping cart",
            description = "Delete book from user's shopping cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCartDto deleteItemFromCart(Authentication authentication,
                                              @PathVariable Long cartItemId) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.deleteItemFromCart(user, cartItemId);
    }
}
