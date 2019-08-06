package onlineshop.spring.dao.impl;

import onlineshop.spring.dao.BasketDao;
import onlineshop.spring.entity.Basket;
import onlineshop.spring.entity.Product;
import onlineshop.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BasketDaoImpl implements BasketDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger basketDaoLogger = Logger.getLogger(BasketDaoImpl.class);

    @Override
    public void addBasket(Basket basket) {
        try {
            sessionFactory.getCurrentSession().save(basket);
            basketDaoLogger.info("Basket " + basket + " added to the DataBase");
        } catch (Exception e) {
            basketDaoLogger.error("Problem in working with the DataBase, " +
                    "Basket " + basket + " isn't added to the DataBase", e);
        }
    }

    @Override
    public Optional<Basket> getLatestBasketOfUser(User user) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM Basket WHERE user = :user " +
                            "AND locked = FALSE ORDER BY id DESC");
            query.setParameter("user", user);
            Basket basket = (Basket) query.list().get(0);
            return Optional.of(basket);
        } catch (Exception e) {
            basketDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public void addProductToBasket(Product product, Basket basket) {
        try {
            List<Product> productList = new ArrayList<>(basket.getProductBasket());
            productList.add(product);
            basket.setProductBasket(productList);
            sessionFactory.getCurrentSession().update(basket);
            basketDaoLogger.info("Product (id=" + product.getId() +
                    ") is added to basket (id=" + basket.getId() +
                    ") by the user (id=" + basket.getUser().getId() + ")");
        } catch (Exception e) {
            basketDaoLogger.error("Problem in working with the DataBase, Basket "
                    + basket + " isn't added to the DataBase", e);
        }
    }

    @Override
    public void lockBasket(Basket basket) {
        try {
            basket.setLocked(true);
            sessionFactory.getCurrentSession().update(basket);
            basketDaoLogger.info("Basket " + basket.toString() + " is locked " +
                    "by the user (id=" + basket.getUser().getId() + ")");
        } catch (Exception e) {
            basketDaoLogger.error("Problem in working with the DataBase, Basket "
                    + basket + " isn't locked", e);
        }
    }
}
