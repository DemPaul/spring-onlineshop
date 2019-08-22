package onlineshop.spring.service;

import onlineshop.spring.entity.Code;

public interface MailService {

    void sendConfirmCode(Code code);

}
