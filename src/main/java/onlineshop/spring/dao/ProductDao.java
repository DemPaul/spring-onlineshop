package onlineshop.spring.dao;

import onlineshop.spring.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    void addProduct(Product product);

    List<Product> getAll();

    Optional<Product> getProductById(long id);

    void changeProduct(Product oldProduct, Product newProduct);

    void removeProduct(Product product);

}
