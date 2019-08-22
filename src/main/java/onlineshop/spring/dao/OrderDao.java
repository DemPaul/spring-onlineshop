package onlineshop.spring.dao;

import onlineshop.spring.entity.Order;
import onlineshop.spring.entity.User;

import java.util.Optional;

public interface OrderDao {

    void addOrder(Order order);

    Optional<Order> getLatestOrderOfUser(User user);

    void confirmOrder(Order order);

}
