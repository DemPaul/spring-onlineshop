package onlineshop.spring.dao.impl;

import onlineshop.spring.dao.AddressDao;
import onlineshop.spring.entity.Address;
import onlineshop.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger addressDaoLogger = Logger.getLogger(AddressDaoImpl.class);

    @Override
    public void addAddress(Address address) {
        try {
            sessionFactory.getCurrentSession().save(address);
            addressDaoLogger.info("Address " + address + " added to the DataBase");
        } catch (Exception e) {
            addressDaoLogger.error("Problem in working with the DataBase, " +
                    "Address " + address + " isn't added to the DataBase", e);
        }
    }

    @Override
    public Optional<Address> getLatestAddressOfUser(User user) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM Address WHERE user = :user ORDER BY id DESC");
            query.setParameter("user", user);
            Address address = (Address) query.list().get(0);
            return Optional.of(address);
        } catch (Exception e) {
            addressDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }
}
