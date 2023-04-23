package erli.shop.controller;

import erli.shop.entity.Category;
import erli.shop.entity.Product;
import erli.shop.repository.CategoryRepository;
import erli.shop.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductListController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping(path = "/products")
    public String products(
            Model model
    ) {
        Sort sort = Sort.by(
                Sort.Order.desc("price"),
                Sort.Order.asc("name")
        );
        List<Product> product = productRepository.findAll(sort);
        model.addAttribute("products", product);
        TypedQuery<Integer> sumProduct = entityManager.createQuery(
          "select sum(p.price) from  Product p ", Integer.class
        );
        model.addAttribute("price", sumProduct.getSingleResult());
        return "product_list_page";
    }

    @GetMapping(path = "/categories")
    public String categories(
             @RequestParam long categoryId,
            Model model
    ) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        model.addAttribute("categories", category);
        TypedQuery<Integer> sumProduct = entityManager.createQuery(
                "select sum(p.price) from  Product p where p.category.id=?1", Integer.class
        );
        sumProduct.setParameter(1, categoryId);
        model.addAttribute("price", sumProduct.getSingleResult());
        return "category_page";
    }
}
