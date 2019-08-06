package onlineshop.spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Code code;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Basket basket;

    @Column(name = "contactName")
    private String contactName;

    @Column(name = "contactEmail")
    private String contactEmail;

    @Column(name = "contactNumber")
    private String contactNumber;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "confirmed")
    private boolean confirmed;

    public Order() {

    }

    public Order(Long id, Code code, Basket basket, String contactName,
                 String contactEmail, String contactNumber, Address address, boolean confirmed) {
        this.id = id;
        this.code = code;
        this.basket = basket;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactNumber = contactNumber;
        this.address = address;
        this.confirmed = confirmed;
    }

    public Order(Code code, Basket basket, String contactName,
                 String contactEmail, String contactNumber, Address address) {
        this.code = code;
        this.basket = basket;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactNumber = contactNumber;
        this.address = address;
        this.confirmed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return confirmed == order.confirmed &&
                Objects.equals(id, order.id) &&
                Objects.equals(code, order.code) &&
                Objects.equals(basket, order.basket) &&
                Objects.equals(contactName, order.contactName) &&
                Objects.equals(contactEmail, order.contactEmail) &&
                Objects.equals(contactNumber, order.contactNumber) &&
                Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, basket, contactName,
                contactEmail, contactNumber, address, confirmed);
    }

    @Override
    public String toString() {
        return "Order{" +
                "code=" + code.toString() +
                ", basket=" + basket +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address=" + address.toString() +
                ", confirmed=" + confirmed +
                '}';
    }
}
