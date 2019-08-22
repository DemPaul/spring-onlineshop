package onlineshop.spring.service;

import onlineshop.spring.entity.Code;

import java.util.Optional;

public interface CodeService {

    void addCode(Code code);

    Optional<Code> getLatestCodeOfEmail(String email);

}
