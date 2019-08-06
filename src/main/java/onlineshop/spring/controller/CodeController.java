package onlineshop.spring.controller;

import onlineshop.spring.entity.Order;
import onlineshop.spring.entity.User;

import onlineshop.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequestMapping(path = {"/user/code"})
public class CodeController {

    private OrderService orderService;

    @Autowired
    public CodeController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String confirmCodeGet(Model model) {
        return "code";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String confirmCodePost(@SessionAttribute("user") User user,
                                  @RequestParam("code") String receivedCode, Model model) {
        Optional<Order> orderOptional = orderService.getLatestOrderOfUser(user);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            String sentCode = order.getCode().getValue();
            if (sentCode.equals(receivedCode)) {
                orderService.confirmOrder(order);
                model.addAttribute("successfulPurchase",
                        "Your purchase is completed successfully, wait for delivery! " +
                                "You can select other products");
                return "redirect:/user/product/all";
            } else {
                model.addAttribute("lastEnteredCode", receivedCode);
                model.addAttribute("codeEquivalenceError",
                        "You have entered an incorrect code, check it out and try again!");
                return "code";
            }
        } else {
            model.addAttribute("missingOrderError",
                    "Your order does not exist, select the products and make a new one!");
            return "redirect:/user/product/all";
        }
    }
}
