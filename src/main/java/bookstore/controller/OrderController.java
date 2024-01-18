package bookstore.controller;

import bookstore.dto.order.CreateOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.UpdateOrderStatusRequestDto;
import bookstore.model.User;
import bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Manage order")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Place an order",
            description = "Place an order and clear the shopping cart")
    public ResponseEntity<OrderDto> createOrder(Authentication authentication,
                                                @RequestBody @Valid
                                                CreateOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.createOrder(user, requestDto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get order's history", description = "Get history of user's orders")
    public List<OrderDto> getOrdersHistory(Authentication authentication,
                                           @PageableDefault(size = 5) Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrdersHistory(user, pageable);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get order items by order's id",
            description = "Get all order items by order's id")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long orderId,
                                                     @PageableDefault(size = 20)
                                                     Pageable pageable) {
        return orderService.getOrderItemsByOrder(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get order item by its id", description = "Get order item by its id")
    public OrderItemDto getOrderItemById(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update order's status", description = "Update order's status by admin")
    public OrderDto updateOrderStatus(@PathVariable Long id,
                                      @RequestBody @Valid UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }
}
