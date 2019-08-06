package onlineshop.spring.service.impl;

import onlineshop.spring.dao.ProductDao;
import onlineshop.spring.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import onlineshop.spring.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> getProductById(long id) {
        return productDao.getProductById(id);
    }

    @Transactional
    @Override
    public void changeProduct(Product oldProduct, Product newProduct) {
        productDao.changeProduct(oldProduct, newProduct);
    }

    @Transactional
    @Override
    public void removeProduct(Product product) {
        productDao.removeProduct(product);
    }
}
