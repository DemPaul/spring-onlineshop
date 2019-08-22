package onlineshop.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "code")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "email")
    private String email;

    public Code() {

    }

    public Code(Long id, String value, String email) {
        this.id = id;
        this.value = value;
        this.email = email;
    }

    public Code(String value, String email) {
        this.value = value;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Objects.equals(id, code.id) &&
                Objects.equals(value, code.value) &&
                Objects.equals(email, code.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, email);
    }

    @Override
    public String toString() {
        return "Code{" +
                "value='" + value + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
