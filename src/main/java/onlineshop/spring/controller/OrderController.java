package onlineshop.spring.controller;

import onlineshop.spring.entity.Address;
import onlineshop.spring.entity.Basket;
import onlineshop.spring.entity.Code;
import onlineshop.spring.entity.Order;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.AddressService;
import onlineshop.spring.service.BasketService;
import onlineshop.spring.service.CodeService;
import onlineshop.spring.service.MailService;
import onlineshop.spring.service.OrderService;
import onlineshop.spring.utils.ConfirmCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = {"/user/order"})
public class OrderController {

    private BasketService basketService;
    private MailService mailService;
    private CodeService codeService;
    private AddressService addressService;
    private OrderService orderService;

    @Autowired
    public OrderController(BasketService basketService, MailService mailService,
                           CodeService codeService, AddressService addressService,
                           OrderService orderService) {
        this.basketService = basketService;
        this.mailService = mailService;
        this.codeService = codeService;
        this.addressService = addressService;
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String orderGet(@SessionAttribute("user") User user, Model model) {
        Optional<Basket> basketOptional = basketService.getLatestBasketOfUser(user);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            if (basket.getProductBasket().size() == 0) {
                model.addAttribute("emptyBasketError",
                        "Your basket is empty! To place an order please select a product");
                return "forward:/user/product/all";
            } else {
                return "order";
            }
        } else {
            model.addAttribute("missingBasketError",
                    "Your basket does not exist, select the products and make a new one!");
            return "forward:/user/product/all";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String orderPost(@SessionAttribute("user") User user,
                            @RequestParam("name") String name,
                            @RequestParam("email") String email,
                            @RequestParam("phoneNumber") String phoneNumber,
                            @RequestParam("country") String country,
                            @RequestParam("city") String city,
                            @RequestParam("street") String street,
                            @RequestParam("houseNumber") String houseNumber,
                            Model model) {
        if (isNullOrEmpty(name) || isNullOrEmpty(email) || isNullOrEmpty(phoneNumber)
                || isNullOrEmpty(country) || isNullOrEmpty(city)
                || isNullOrEmpty(street) || isNullOrEmpty(houseNumber)) {
            model.addAttribute("incompleteFormError",
                    "The form is not fully completed!");
            model.addAttribute("lastEnteredName", name);
            model.addAttribute("lastEnteredEmail", email);
            model.addAttribute("lastEnteredPhoneNumber", phoneNumber);
            model.addAttribute("lastEnteredCountry", country);
            model.addAttribute("lastEnteredCity", city);
            model.addAttribute("lastEnteredStreet", street);
            model.addAttribute("lastEnteredHouseNumber", houseNumber);
            return "order";
        } else {
            Optional<Basket> basketOptional = basketService.getLatestBasketOfUser(user);
            if (basketOptional.isPresent()) {
                Basket basket = basketOptional.get();
                String stringCode = ConfirmCodeGenerator.generateCode();
                mailService.sendConfirmCode(new Code(stringCode, email));
                codeService.addCode(new Code(stringCode, email));
                Address address = new Address(country, city, street, houseNumber, user);
                addressService.addAddress(address);
                Order order = new Order(codeService.getLatestCodeOfEmail(email).get(),
                        basket, name, email, phoneNumber,
                        addressService.getLatestAddressOfUser(user).get());
                basketService.lockBasket(basket);
                orderService.addOrder(order);
                return "code";
            } else {
                model.addAttribute("missingBasketError",
                        "Your basket does not exist, select the products and make a new one!");
                return "forward:/user/product/all";
            }
        }
    }

    private boolean isNullOrEmpty(String requestParameter) {
        return Objects.isNull(requestParameter) || requestParameter.trim().isEmpty();
    }
}
