package onlineshop.spring.controller;

import onlineshop.spring.entity.Basket;
import onlineshop.spring.entity.Product;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.BasketService;
import onlineshop.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProductController {

    private ProductService productService;
    private BasketService basketService;

    @Autowired
    public ProductController(ProductService productService, BasketService basketService) {
        this.productService = productService;
        this.basketService = basketService;
    }

    @RequestMapping(path = {"admin/product/all"}, method = RequestMethod.GET)
    public String allProducts(Model model) {
        List<Product> allProducts = productService.getAll();
        model.addAttribute("allProducts", allProducts);
        return "products";
    }

    @RequestMapping(path = {"admin/product/add"}, method = RequestMethod.GET)
    public String addProductGet() {
        return "product-add";
    }

    @RequestMapping(path = {"admin/product/add"}, method = RequestMethod.POST)
    public String addProductPost(@RequestBody MultiValueMap<String, String> formData, Model model) {
        String name = formData.getFirst("name");
        String description = formData.getFirst("description");
        String stringPrice = formData.getFirst("price");
        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(stringPrice)) {
            model.addAttribute("incompleteFormError",
                    "The form is not fully completed!");
            model.addAttribute("lastEnteredName", name);
            model.addAttribute("lastEnteredDescription", description);
            model.addAttribute("lastEnteredPrice", stringPrice);
            return "product-add";
        } else {
            try {
                Double price = Double.valueOf(stringPrice);
                if (price < 0) {
                    throw new NumberFormatException();
                }
                Product product = new Product(name, description, price);
                productService.addProduct(product);
                return "redirect:/admin/product/all";
            } catch (NumberFormatException nfe) {
                model.addAttribute("illegalPriceError",
                        "Invalid price entered!");
                model.addAttribute("lastEnteredName", name);
                model.addAttribute("lastEnteredDescription", description);
                return "product-add";
            }
        }
    }

    @RequestMapping(path = {"admin/product/edit"}, method = RequestMethod.GET)
    public String editProductGet(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product productToChange = productOptional.get();
            model.addAttribute("lastEnteredName", productToChange.getName());
            model.addAttribute("lastEnteredDescription", productToChange.getDescription());
            model.addAttribute("lastEnteredPrice", productToChange.getPrice());
            return "product-edit";
        } else {
            return "redirect:/admin/product/all";
        }
    }

    @RequestMapping(path = {"admin/product/edit"}, method = RequestMethod.POST)
    public String editProductPost(@RequestParam("id") Long id,
                                  @RequestBody MultiValueMap<String, String> formData, Model model) {
        String name = formData.getFirst("name");
        String description = formData.getFirst("description");
        String stringPrice = formData.getFirst("price");
        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(stringPrice)) {
            model.addAttribute("id", id);
            model.addAttribute("incompleteFormError",
                    "The form is not fully completed!");
            model.addAttribute("lastEnteredName", name);
            model.addAttribute("lastEnteredDescription", description);
            model.addAttribute("lastEnteredPrice", stringPrice);
            return "product-edit";
        } else {
            try {
                Double price = Double.valueOf(stringPrice);
                if (price < 0) {
                    throw new NumberFormatException();
                }
                Product newProduct = new Product(name, description, price);
                Optional<Product> productOptional = productService.getProductById(id);
                productOptional.ifPresent(prod -> productService.changeProduct(prod, newProduct));
                return "redirect:/admin/product/all";
            } catch (NumberFormatException nfe) {
                model.addAttribute("id", id);
                model.addAttribute("illegalPriceError", "Invalid price entered!");
                model.addAttribute("lastEnteredName", name);
                model.addAttribute("lastEnteredDescription", description);
                return "product-edit";
            }
        }
    }

    @RequestMapping(path = {"admin/product/delete"}, method = RequestMethod.GET)
    public String deleteProductGet(@RequestParam("id") Long id) {
        Optional<Product> productOptional = productService.getProductById(id);
        productOptional.ifPresent(productService::removeProduct);
        return "redirect:/admin/product/all";
    }

    @RequestMapping(path = {"user/product/all"}, method = RequestMethod.GET)
    public String allProductsToBuyGet(@AuthenticationPrincipal User user, Model model) {
        List<Product> allProductsToBuy = productService.getAll();
        Optional<Basket> basketOptional = basketService.getLatestBasketOfUser(user);
        if (basketOptional.isPresent()) {
            int basketSize = basketOptional.get().getProductBasket().size();
            if (basketSize > 0) {
                model.addAttribute("productsInBasket", ("There are " +
                        basketSize + " products in Basket"));
            }
        }
        model.addAttribute("allProductsToBuy", allProductsToBuy);
        return "products-to-buy";
    }

    @RequestMapping(path = {"user/product/all"}, method = RequestMethod.POST)
    public String allProductsToBuyPost(Model model) {
        List<Product> allProductsToBuy = productService.getAll();
        model.addAttribute("allProductsToBuy", allProductsToBuy);
        return "products-to-buy";
    }


    @RequestMapping(path = {"user/product/buy"}, method = RequestMethod.GET)
    public String buyProductGet(@AuthenticationPrincipal User user,
                                @RequestParam("id") Long id, Model model) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Optional<Basket> basketOptional = basketService.getLatestBasketOfUser(user);
            while (!basketOptional.isPresent()) {
                basketService.addBasket(new Basket(user));
                basketOptional = basketService.getLatestBasketOfUser(user);
            }
            Basket basket = basketOptional.get();
            basketService.addProductToBasket(product, basket);
            return "redirect:/user/product/all";
        } else {
            model.addAttribute("missingProductError",
                    "You choose a product that does not exist!");
            return "products-to-buy";
        }
    }

    private boolean isNullOrEmpty(String requestParameter) {
        return Objects.isNull(requestParameter) || requestParameter.trim().isEmpty();
    }
}
