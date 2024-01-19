package bookstore.service.impl;

import bookstore.dto.order.CreateOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.UpdateOrderStatusRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartItemOrderMapper;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.OrderItemRepository;
import bookstore.repository.OrderRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final CartItemOrderMapper cartItemOrderMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto createOrder(User user, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user).orElseThrow(() ->
                new EntityNotFoundException("Can't find shopping cart by user"));
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(shoppingCart.getUser());
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setOrderItems(getOrderItems(shoppingCart, order));
        order.setTotal(getTotal(order.getOrderItems()));
        orderRepository.save(order);
        shoppingCartRepository.delete(shoppingCart);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrdersHistory(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Can't find order by id: " + orderId));
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrder(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(orderId, pageable).stream()
                    .map(orderItemMapper::toDto)
                    .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        return orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order item by id: "
                        + itemId + " in order by id: " + orderId));
    }

    private Set<OrderItem> getOrderItems(ShoppingCart shoppingCart, Order order) {
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(cartItemOrderMapper::toOrderItem)
                .collect(Collectors.toSet());
        orderItems.forEach(i -> i.setOrder(order));
        return orderItems;
    }

    private BigDecimal getTotal(Set<OrderItem> orderItems) {
        Function<OrderItem, BigDecimal> totalMapper = item -> item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
        return orderItems.stream()
                .map(totalMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
