package onlineshop.spring.controller;

import onlineshop.spring.entity.User;
import onlineshop.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = {"/admin/user"})
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = {"/all"}, method = RequestMethod.GET)
    public String allUsers(Model model) {
        List<User> allUsers = userService.getAll();
        model.addAttribute("allUsers", allUsers);
        return "users";
    }

    @RequestMapping(path = {"/add"}, method = RequestMethod.GET)
    public String addUserGet(Model model) {
        return "user-add";
    }

    @RequestMapping(path = {"/add"}, method = RequestMethod.POST)
    public String addUserPost(@RequestBody MultiValueMap<String, String> formData, Model model) {
        String email = formData.getFirst("email");
        String password = formData.getFirst("password");
        String repeatPassword = formData.getFirst("repeatPassword");
        String role = formData.getFirst("role");
        if (isNullOrEmpty(email) || isNullOrEmpty(password)
                || isNullOrEmpty(repeatPassword) || isNullOrEmpty(role)) {
            model.addAttribute("incompleteFormError", "The form is not fully completed!");
            model.addAttribute("lastEnteredEmail", email);
            model.addAttribute("lastEnteredPassword", password);
            model.addAttribute("lastEnteredRepeatPassword", repeatPassword);
            model.addAttribute("lastEnteredRole", role);
            return "user-add";
        } else {
            if (password.equals(repeatPassword)) {
                User user = new User(email, password, role);
                if (!userService.isPresent(email)) {
                    userService.addUser(user);
                    return "redirect:/admin/user/all";
                } else {
                    model.addAttribute("userIsAlreadyPresentError", "The user with such email already exists!");
                    model.addAttribute("lastEnteredEmail", email);
                    model.addAttribute("lastEnteredPassword", password);
                    model.addAttribute("lastEnteredRepeatPassword", repeatPassword);
                    model.addAttribute("lastEnteredRole", role);
                    return "user-add";
                }
            } else {
                model.addAttribute("passwordEquivalenceError", "The entered passwords are not identical!");
                model.addAttribute("lastEnteredEmail", email);
                model.addAttribute("lastEnteredPassword", password);
                model.addAttribute("lastEnteredRole", role);
                return "user-add";
            }
        }
    }

    @RequestMapping(path = {"/edit"}, method = RequestMethod.GET)
    public String editUserGet(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User userToEdit = userOptional.get();
            model.addAttribute("lastEnteredEmail", userToEdit.getEmail());
            model.addAttribute("lastEnteredRole", userToEdit.getRole());
            return "user-edit";
        } else {
            return "redirect:/admin/user/all";
        }
    }

    @RequestMapping(path = {"/edit"}, method = RequestMethod.POST)
    public String editUserPost(@RequestParam("id") Long id,
                               @RequestBody MultiValueMap<String, String> formData, Model model) {
        String email = formData.getFirst("email");
        String password = formData.getFirst("password");
        String role = formData.getFirst("role");
        if (isNullOrEmpty(email) || isNullOrEmpty(password) || isNullOrEmpty(role)) {
            model.addAttribute("id", id);
            model.addAttribute("incompleteFormError", "The form is not fully completed!");
            model.addAttribute("lastEnteredEmail", email);
            model.addAttribute("lastEnteredPassword", password);
            model.addAttribute("lastEnteredRole", role);
            return "user-edit";
        } else {
            Optional<User> userOptional = userService.getUserById(id);
            if (userOptional.isPresent()) {
                User userToEdit = userOptional.get();
                if (email.equals(userToEdit.getEmail()) || !userService.isPresent(email)) {
                    User newUser = new User(email, password, role);
                    userService.changeUser(userToEdit, newUser);
                    return "redirect:/admin/user/all";
                } else {
                    model.addAttribute("id", id);
                    model.addAttribute("userIsAlreadyPresentError", "The user with such email already exists!");
                    model.addAttribute("lastEnteredEmail", email);
                    model.addAttribute("lastEnteredPassword", password);
                    model.addAttribute("lastEnteredRole", role);
                    return "user-edit";
                }
            } else {
                return "user-edit";
            }
        }
    }

    @RequestMapping(path = {"/delete"}, method = RequestMethod.GET)
    public String deleteUserGet(@RequestParam("id") Long id, Model model) {
        Optional<User> userOptional = userService.getUserById(id);
        userOptional.ifPresent(userService::removeUser);
        return "redirect:/admin/user/all";
    }

    private boolean isNullOrEmpty(String requestParameter) {
        return Objects.isNull(requestParameter) || requestParameter.trim().isEmpty();
    }
}
