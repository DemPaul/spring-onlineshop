package onlineshop.spring.dao.impl;

import onlineshop.spring.dao.OrderDao;
import onlineshop.spring.entity.Order;
import onlineshop.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger orderDaoLogger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public void addOrder(Order order) {
        try {
            sessionFactory.getCurrentSession().save(order);
            orderDaoLogger.info("Order " + order + " added to the DataBase");
        } catch (Exception e) {
            orderDaoLogger.error("Problem in working with the DataBase, " +
                    "Order " + order + " isn't added to the DataBase", e);
        }
    }

    @Override
    public Optional<Order> getLatestOrderOfUser(User user) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("FROM Order " +
                    "WHERE address.user = :user AND confirmed = FALSE ORDER BY id DESC");
            query.setParameter("user", user);
            Order order = (Order) query.list().get(0);
            return Optional.of(order);
        } catch (Exception e) {
            orderDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public void confirmOrder(Order order) {
        try {
            order.setConfirmed(true);
            sessionFactory.getCurrentSession().update(order);
            orderDaoLogger.info("Order " + order.toString() + " is confirmed " +
                    "by the user (id=" + order.getBasket().getUser().getId() + ")");
        } catch (Exception e) {
            orderDaoLogger.error("Problem in working with the DataBase, Order "
                    + order + " isn't confirmed", e);
        }
    }
}
