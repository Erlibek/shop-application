package erli.shop.service;

import erli.shop.entity.CartItem;
import erli.shop.entity.Order;
import erli.shop.entity.OrderProduct;
import erli.shop.entity.enumeration.Status;
import erli.shop.repository.CartItemRepository;
import erli.shop.repository.OrderProductRepository;
import erli.shop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final CartItemRepository cartItemRepository;

    private final   UserService userService;

    @Transactional
    public void addOrder(Order order) {
        orderRepository.save(order);

        List<CartItem> cartItemList = cartItemRepository.findAllByUserIdOrderByProductId(userService.getCurrentUser().getId());
        for (CartItem cartItem:cartItemList) {
            OrderProduct  orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setProductCount(cartItem.getProductCount());
            orderProductRepository.save(orderProduct);
        }

        cartItemRepository.deleteByUser(userService.getCurrentUser());
    }

    public List<Order> orderList() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    public void changeStatus(Status status, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order oneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return order;
    }
    public List<Order> oneOrderList(Long orderId) {
        return orderRepository.findAllById(orderId);
    }

    @Transactional
    public void createOrder(String address) {
        Order order = new Order();
        order.setUser(userService.getCurrentUser());
        order.setStatus(Status.INSTOCK);
        order.setAddress(address);
        order.setDate(LocalDate.now());
        addOrder(order);
    }

    public Integer sumOneOrder(Long orderId) {
        int sum = 0;
        List<Order> orders = oneOrderList(orderId);
        for (Order order:orders) {
            for (OrderProduct orderProduct:order.getOrderProducts()) {
                sum += orderProduct.getProduct().getPrice()*orderProduct.getProductCount();
            }
        }
        return sum;
    }

    public List<Order> orderListForCabinet (){
        List<Order> orderList  = orderRepository.findAllByUserId(userService.getCurrentUser().getId());
        return orderList;
    }
}
