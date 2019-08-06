package onlineshop.spring.dao;

import onlineshop.spring.entity.Basket;
import onlineshop.spring.entity.Product;
import onlineshop.spring.entity.User;

import java.util.Optional;

public interface BasketDao {

    void addBasket(Basket basket);

    Optional<Basket> getLatestBasketOfUser(User user);

    void addProductToBasket(Product product, Basket basket);

    void lockBasket(Basket basket);

}
