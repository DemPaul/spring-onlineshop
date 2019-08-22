package onlineshop.spring.service;

import onlineshop.spring.entity.Address;
import onlineshop.spring.entity.User;

import java.util.Optional;

public interface AddressService {

    void addAddress(Address address);

    Optional<Address> getLatestAddressOfUser(User user);

}
