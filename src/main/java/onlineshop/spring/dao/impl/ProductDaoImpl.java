package onlineshop.spring.dao.impl;

import onlineshop.spring.dao.ProductDao;
import onlineshop.spring.entity.Product;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger productDaoLogger = Logger.getLogger(ProductDaoImpl.class);

    @Override
    public void addProduct(Product product) {
        try {
            sessionFactory.getCurrentSession().save(product);
            productDaoLogger.info("Product " + product + " added to the DataBase");
        } catch (Exception e) {
            productDaoLogger.error("Problem in working with the DataBase, " +
                    "Product " + product + " isn't added to the DataBase", e);
        }
    }

    @Override
    public List<Product> getAll() {
        try {
            @SuppressWarnings("unchecked")
            TypedQuery<Product> query = sessionFactory.getCurrentSession()
                    .createQuery("FROM Product WHERE available = TRUE");
            return query.getResultList();
        } catch (Exception e) {
            productDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Product> getProductById(long id) {
        try {
            Product product = sessionFactory.getCurrentSession().get(Product.class, id);
            return Optional.of(product);
        } catch (Exception e) {
            productDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public void changeProduct(Product oldProduct, Product newProduct) {
        try {
            oldProduct.setName(newProduct.getName());
            oldProduct.setDescription(newProduct.getDescription());
            oldProduct.setPrice(newProduct.getPrice());
            sessionFactory.getCurrentSession().update(oldProduct);
            productDaoLogger.info("Product " + oldProduct + " changed in DataBase to " + newProduct);
        } catch (Exception e) {
            productDaoLogger.error("Problem in working with the DataBase", e);
        }
    }

    @Override
    public void removeProduct(Product product) {
        try {
            product.setAvailable(false);
            sessionFactory.getCurrentSession().update(product);
            productDaoLogger.info("Product " + product + " become unavailable");
        } catch (Exception e) {
            productDaoLogger.error("Problem in working with the DataBase, Product "
                    + product + " isn't become unavailable", e);
        }
    }
}
