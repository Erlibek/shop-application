package erli.shop.service;

import erli.shop.entity.CartItem;
import erli.shop.entity.Product;
import erli.shop.entity.User;
import erli.shop.repository.CartItemRepository;
import erli.shop.repository.ProductRepository;
import erli.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {


    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    public void addProductToCart(Long productId) {
        Long userId = userService.getCurrentUser().getId();
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        if (cartItemRepository.existsCartByUserAndProduct(user, product)) {
            CartItem c1 = cartItemRepository.findByProductIdAndUserIdOrderByProductId(productId,userId);
            cartItemRepository.delete(c1);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(userService.getCurrentUser());
            cartItem.setProduct(productRepository.findById(productId).orElseThrow());
            cartItem.setProductCount(1);
            cartItemRepository.save(cartItem);
        }
    }

    public List<CartItem> cartItemList() {
        List<CartItem> cartItemList = cartItemRepository.findAllByUserIdOrderByProductId(userService.getCurrentUser().getId());
        return cartItemList;
    }

    public void increaseProduct(Long productId) {
        CartItem cartItem = cartItemRepository.findByProductIdAndUserIdOrderByProductId(productId, userService.getCurrentUser().getId());
        cartItem.setProductCount(cartItem.getProductCount()+1);
        cartItemRepository.save(cartItem);
    }

    public void decreaseProduct(Long productId) {
        CartItem cartItem = cartItemRepository.findByProductIdAndUserIdOrderByProductId(productId,userService.getCurrentUser().getId());
        cartItem.setProductCount(cartItem.getProductCount()-1);
        if (cartItem.getProductCount() == 0) {
            cartItemRepository.delete(cartItem);
        }else{
            cartItemRepository.save(cartItem);
        }
    }

    public void deleteProductFromCart(Long productId) {
        CartItem cartItem = cartItemRepository.findByProductIdAndUserIdOrderByProductId(productId,userService.getCurrentUser().getId());
        cartItemRepository.delete(cartItem);
    }
    public int sumAllProductCart() {
        List<CartItem> cartItemList = cartItemRepository.findAllByUserIdOrderByProductId(userService.getCurrentUser().getId());
        int sum = 0;

        for (CartItem cartItem : cartItemList) {
            sum += (cartItem.getProductCount()*cartItem.getProduct().getPrice());
        }
        return sum;
    }
}