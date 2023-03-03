package nus.iss.tfip.pafassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.tfip.pafassessment.model.Account;
import nus.iss.tfip.pafassessment.repository.SqlRepository;

@Service
public class SqlService {

    @Autowired
    private SqlRepository sqlRepo;

    public List<Account> getAllAccounts() {
        return sqlRepo.getAllAccounts();
    }

    public Boolean isAccountExist(String accountId) {
        return sqlRepo.isAccountExist(accountId);
    }

    public Double getBalanceById(String accountId) {
        return sqlRepo.getBalanceById(accountId);
    }

    
}
