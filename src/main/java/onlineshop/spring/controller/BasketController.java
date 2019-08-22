package onlineshop.spring.controller;

import onlineshop.spring.entity.Basket;
import onlineshop.spring.entity.Product;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = {"/user/basket"})
public class BasketController {

    private BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String checkBasket(@AuthenticationPrincipal User user, Model model) {
        Optional<Basket> basketOptional = basketService.getLatestBasketOfUser(user);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            List<Product> allProductsInBasket = basket.getProductBasket();
            if (allProductsInBasket.size() == 0) {
                model.addAttribute("emptyBasketError",
                        "Your basket is empty! To place an order please select a product");
                return "forward:/user/product/all";
            } else {
                Double totalPrice = allProductsInBasket.stream()
                        .mapToDouble(Product::getPrice).sum();
                model.addAttribute("totalPrice", String.format("%.2f", totalPrice));
                model.addAttribute("allProductsInBasket", allProductsInBasket);
                return "basket";
            }
        } else {
            model.addAttribute("missingBasketError",
                    "Your basket does not exist, select the products and make a new one!");
            return "forward:/user/product/all";
        }
    }

    @RequestMapping(path = {"/clear"}, method = RequestMethod.GET)
    public String clearBasket(@AuthenticationPrincipal User user, Model model) {
        Optional<Basket> basketOptional = basketService.getLatestBasketOfUser(user);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            if (basket.getProductBasket().size() == 0) {
                model.addAttribute("emptyBasketError",
                        "Your basket is empty!");
                return "forward:/user/product/all/";
            } else {
                basketService.lockBasket(basket);
                model.addAttribute("clearedBasket",
                        "Your basket has been cleared!");
                return "forward:/user/product/all";
            }
        } else {
            model.addAttribute("missingBasketError",
                    "Your basket does not exist, select the products and make a new one!");
            return "forward:/user/product/all";
        }
    }
}
