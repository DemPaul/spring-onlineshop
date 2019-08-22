package onlineshop.spring.service.impl;

import onlineshop.spring.dao.CodeDao;
import onlineshop.spring.entity.Code;
import org.springframework.beans.factory.annotation.Autowired;
import onlineshop.spring.service.CodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeDao codeDao;

    @Transactional
    @Override
    public void addCode(Code code) {
        codeDao.addCode(code);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Code> getLatestCodeOfEmail(String email) {
        return codeDao.getLatestCodeOfEmail(email);
    }
}
