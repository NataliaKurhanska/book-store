package bookstore.service;

import bookstore.dto.order.CreateOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.UpdateOrderStatusRequestDto;
import bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(User user, CreateOrderRequestDto orderRequestDto);

    List<OrderDto> getOrdersHistory(User user, Pageable pageable);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);

    List<OrderItemDto> getOrderItemsByOrder(Long orderId, Pageable pageable);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
