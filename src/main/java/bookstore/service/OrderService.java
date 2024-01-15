package bookstore.service;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.model.Order;
import bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(User user, String shippingAdress);

    List<OrderDto> getOrdersHistory(User user, Pageable pageable);

    OrderDto updateOrderStatus(Long orderId, Order.Status status);

    List<OrderItemDto> getOrderItemsByOrder(Long orderId);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
