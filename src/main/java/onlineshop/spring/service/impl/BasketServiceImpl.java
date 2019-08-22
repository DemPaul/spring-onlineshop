package onlineshop.spring.service.impl;

import onlineshop.spring.dao.BasketDao;
import onlineshop.spring.entity.Basket;
import onlineshop.spring.entity.Product;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketDao basketDao;

    @Transactional
    @Override
    public void addBasket(Basket basket) {
        basketDao.addBasket(basket);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Basket> getLatestBasketOfUser(User user) {
        return basketDao.getLatestBasketOfUser(user);
    }

    @Transactional
    @Override
    public void addProductToBasket(Product product, Basket basket) {
        basketDao.addProductToBasket(product, basket);
    }

    @Transactional
    @Override
    public void lockBasket(Basket basket) {
        basketDao.lockBasket(basket);
    }
}
