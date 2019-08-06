package onlineshop.spring.service;

import onlineshop.spring.entity.Order;
import onlineshop.spring.entity.User;

import java.util.Optional;

public interface OrderService {

    void addOrder(Order order);

    Optional<Order> getLatestOrderOfUser(User user);

    void confirmOrder(Order order);

}
