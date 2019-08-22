package onlineshop.spring.dao;

import onlineshop.spring.entity.Address;
import onlineshop.spring.entity.User;

import java.util.Optional;

public interface AddressDao {

    void addAddress(Address address);

    Optional<Address> getLatestAddressOfUser(User user);

}
