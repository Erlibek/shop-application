package erli.shop.controller;


import erli.shop.entity.Category;
import erli.shop.entity.Option;
import erli.shop.entity.Product;
import erli.shop.entity.Value;
import erli.shop.repository.CategoryRepository;
import erli.shop.repository.OptionRepository;
import erli.shop.repository.ProductRepository;
import erli.shop.repository.ValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UpdateProduct {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValueRepository valueRepository;

    @Autowired
    private OptionRepository optionRepository;

    @GetMapping(path = "/update_product")
    public String updateProductPage(
            @RequestParam(required = false) Long productId,
            Model model
    ) {
        Product product = productRepository.findById(productId).orElseThrow();
        model.addAttribute("product",product);
        model.addAttribute("productId",productId);
        return "update_product_page";
    }

    @PostMapping(path = "/update_product")
    public String updateProductPage(
            @RequestParam(required = false) Long productId,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) int price,
            @RequestParam (required = false, name = "option") List<Long> optionIds,
            @RequestParam( required = false, name = "value") List<String> values,
            Model model
    ) {
        Product product = productRepository.findById(productId).orElseThrow();
        if (name != null) product.setName(name);
        if (price != 0) product.setPrice(price);
        productRepository.save(product);

        List<Option> options = optionRepository.findAllByCategoryOrderById(product.getCategory());
        for (int i = 0; i < options.size(); i++) {
            Value value = valueRepository.findByProductAndOption(product, options.get(i));
            if (value == null) {
                value = new Value();
                value.setProduct(product);
                value.setOption(options.get(i));
                value.setValue(values.get(i));
            }
            valueRepository.save(value);
        }

        return "redirect:/categories";
    }
}
