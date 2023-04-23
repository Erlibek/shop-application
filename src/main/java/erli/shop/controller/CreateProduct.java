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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class CreateProduct {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ValueRepository valueRepository;

  @Autowired
  private OptionRepository optionRepository;

  @GetMapping(path = "/create_product")
  public String createProductPage(@RequestParam(required = false) Long categoryId,
          Model model) {
    if (categoryId != null) {
      Category category = categoryRepository.findById(categoryId).orElseThrow();
      model.addAttribute("category", category);
    }else {
      model.addAttribute("categories", categoryRepository.findAll());
    }
    model.addAttribute("categoryId", categoryId);
    return "create_product_page";
  }


  @PostMapping(path = "/create_product")
  public String createProductAction(
      @RequestParam Long categoryId,
      @RequestParam String name,
      @RequestParam int price,
      @RequestParam(name = "option") List<Long> optionIds,
      @RequestParam(name = "value") List<String> values
  ) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    Category category = categoryRepository.findById(categoryId).orElseThrow();
    List<Option> options = optionRepository.findAllByCategoryOrderById(category);
    product.setCategory(category);
    productRepository.save(product);

    for (int i = 0;i<options.size();i++) {
      Value value = new Value();
      value.setValue(values.get(i));
      value.setOption(options.get(i));
      value.setProduct(product);
      valueRepository.save(value);

    }
    return "redirect:/create_product";
  }
}
