package onlineshop.spring.dao.impl;

import onlineshop.spring.dao.CodeDao;
import onlineshop.spring.entity.Code;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CodeDaoImpl implements CodeDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger codeDaoLogger = Logger.getLogger(CodeDaoImpl.class);

    @Override
    public void addCode(Code code) {
        try {
            sessionFactory.getCurrentSession().save(code);
            codeDaoLogger.info("Code " + code + " added to the DataBase");
        } catch (Exception e) {
            codeDaoLogger.error("Problem in working with the DataBase, " +
                    "Code " + code + " isn't added to the DataBase", e);
        }
    }

    public Optional<Code> getLatestCodeOfEmail(String email) {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM Code WHERE email = :email ORDER BY id DESC");
            query.setParameter("email", email);
            Code code = (Code) query.list().get(0);
            return Optional.of(code);
        } catch (Exception e) {
            codeDaoLogger.error("Problem in working with the DataBase", e);
        }
        return Optional.empty();
    }
}
