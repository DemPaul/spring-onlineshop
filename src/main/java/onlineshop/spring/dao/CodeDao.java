package onlineshop.spring.dao;

import onlineshop.spring.entity.Code;

import java.util.Optional;

public interface CodeDao {

    void addCode(Code code);

    Optional<Code> getLatestCodeOfEmail(String email);

}
