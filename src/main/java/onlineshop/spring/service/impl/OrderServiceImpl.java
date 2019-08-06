package onlineshop.spring.service.impl;

import onlineshop.spring.dao.OrderDao;
import onlineshop.spring.entity.Order;
import onlineshop.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import onlineshop.spring.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Order> getLatestOrderOfUser(User user) {
        return orderDao.getLatestOrderOfUser(user);
    }

    @Transactional
    @Override
    public void confirmOrder(Order order) {
        orderDao.confirmOrder(order);
    }
}
