package onlineshop.spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "product_basket",
            joinColumns = {@JoinColumn(name = "basket_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> productBasket = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "locked")
    private boolean locked;

    public Basket() {

    }

    public Basket(Long id, List<Product> productBasket, User user, boolean locked) {
        this.id = id;
        this.productBasket = productBasket;
        this.user = user;
        this.locked = locked;
    }

    public Basket(List<Product> productBasket, User user) {
        this.productBasket = productBasket;
        this.user = user;
        this.locked = false;
    }

    public Basket(User user) {

        this.user = user;
        this.locked = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProductBasket() {
        return productBasket;
    }

    public void setProductBasket(List<Product> productBasket) {
        this.productBasket = productBasket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return locked == basket.locked &&
                Objects.equals(id, basket.id) &&
                Objects.equals(productBasket, basket.productBasket) &&
                Objects.equals(user, basket.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productBasket, user, locked);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "productBasket=" + Arrays.toString(productBasket.toArray()) +
                ", user=" + user +
                ", locked=" + locked +
                '}';
    }
}
