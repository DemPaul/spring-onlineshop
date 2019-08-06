package onlineshop.spring.controller;

import onlineshop.spring.entity.Product;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.ProductService;
import onlineshop.spring.service.UserService;
import onlineshop.spring.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Objects;
import java.util.Optional;

@SessionAttributes("user")
@Controller
public class InitController {

    private static boolean initialized;

    private UserService userService;
    private ProductService productService;

    @Autowired
    public InitController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }

    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String init(Model model) {
        if (!initialized) {
            User admin = new User("admin@admin", "admin", "admin");
            User user = new User("1@1", "1111", "user");
            userService.addUser(admin);
            userService.addUser(user);

            Product product1 = new Product("Bread", "Black bread", 8.99);
            Product product2 = new Product("Butter", "Classic butter", 10.99);
            Product product3 = new Product("Knife", "Very sharp", 15.99);
            productService.addProduct(product1);
            productService.addProduct(product2);
            productService.addProduct(product3);
            initialized = true;
        }
        return "index";
    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.GET)
    public String loginGet(Model model) {
        return "index";
    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("user") User user, Model model) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (isNullOrEmpty(email) || isNullOrEmpty(password)) {
            model.addAttribute("incompleteFormError", "The form is not fully completed!");
            model.addAttribute("lastEnteredEmail", email);
            model.addAttribute("lastEnteredPassword", password);
            return "index";
        } else {
            Optional<User> userOptional = userService.getUserByEmail(email);
            if (userOptional.isPresent()) {
                User userFromDb = userOptional.get();
                String securePassword = HashUtil.getSha256SecurePassword(password, userFromDb.getSalt());
                if (securePassword.equals(userFromDb.getPassword())) {
                    user.setId(userFromDb.getId());
                    user.setRole(userFromDb.getRole());
                    user.setSalt(userFromDb.getSalt());
                    return "redirect:/admin/user/all";
                } else {
                    model.addAttribute("LoginError",
                            "Wrong email or password! Check them and try again.");
                    model.addAttribute("lastEnteredEmail", email);
                    model.addAttribute("lastEnteredPassword", password);
                    return "index";
                }
            } else {
                model.addAttribute("LoginError",
                        "Wrong email or password! Check them and try again.");
                model.addAttribute("lastEnteredEmail", email);
                model.addAttribute("lastEnteredPassword", password);
                return "index";
            }
        }
    }

    @RequestMapping(path = {"/register"}, method = RequestMethod.GET)
    public String registrationGet(Model model) {
        return "register";
    }

    @RequestMapping(path = {"/register"}, method = RequestMethod.POST)
    public String registrationPost(@RequestBody MultiValueMap<String, String> formData, Model model) {
        String email = formData.getFirst("email");
        String password = formData.getFirst("password");
        String repeatPassword = formData.getFirst("repeatPassword");
        if (isNullOrEmpty(email) || isNullOrEmpty(password) || isNullOrEmpty(repeatPassword)) {
            model.addAttribute("incompleteFormError", "The form is not fully completed!");
            model.addAttribute("lastEnteredEmail", email);
            model.addAttribute("lastEnteredPassword", password);
            model.addAttribute("lastEnteredRepeatPassword", repeatPassword);
            return "register";
        } else {
            if (password.equals(repeatPassword)) {
                User user = new User(email, password, "user");
                if (!userService.isPresent(email)) {
                    userService.addUser(user);
                    return "redirect:/login";
                } else {
                    model.addAttribute("userIsAlreadyPresentError", "The user with such email already exists!");
                    model.addAttribute("lastEnteredEmail", email);
                    model.addAttribute("lastEnteredPassword", password);
                    model.addAttribute("lastEnteredRepeatPassword", repeatPassword);
                    return "register";
                }
            } else {
                model.addAttribute("passwordEquivalenceError", "The entered passwords are not identical!");
                model.addAttribute("lastEnteredEmail", email);
                model.addAttribute("lastEnteredPassword", password);
                return "register";
            }
        }
    }

    @RequestMapping(path = {"/exit"}, method = RequestMethod.GET)
    public String exit(SessionStatus status) {
        status.setComplete();
        return "redirect:/login";
    }

    private boolean isNullOrEmpty(String requestParameter) {
        return Objects.isNull(requestParameter) || requestParameter.trim().isEmpty();
    }
}
