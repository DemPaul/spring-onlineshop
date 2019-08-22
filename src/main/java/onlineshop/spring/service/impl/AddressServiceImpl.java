package onlineshop.spring.service.impl;

import onlineshop.spring.dao.AddressDao;
import onlineshop.spring.entity.Address;
import onlineshop.spring.entity.User;
import onlineshop.spring.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Transactional
    @Override
    public void addAddress(Address address) {
        addressDao.addAddress(address);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Address> getLatestAddressOfUser(User user) {
        return addressDao.getLatestAddressOfUser(user);
    }
}
