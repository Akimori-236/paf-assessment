package nus.iss.tfip.pafassessment.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafassessment.model.Account;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class AccountsRepository {

    @Autowired
    JdbcTemplate template;

    private String SQLGetAllAccounts = "SELECT * FROM accounts";
    private String SQLisAccountExist = "SELECT EXISTS(SELECT * FROM accounts WHERE account_id=?)";
    private String SQLGetBalanceById = "SELECT balance FROM accounts WHERE account_id=?";
    private String SQLWithdrawById = "UPDATE accounts SET balance = balance - ? WHERE account_id=?";
    private String SQLDepositById = "UPDATE accounts SET balance = balance + ? WHERE account_id=?";
    private String SQLGetNameById = "SELECT name FROM accounts WHERE account_id=?";

    public List<Account> getAllAccounts() {
        List<Account> accList = new LinkedList<>();
        accList = template.query(SQLGetAllAccounts, BeanPropertyRowMapper.newInstance(Account.class));
        return accList;
    }

    public Boolean isAccountExist(String accountId) {
        return template.queryForObject(SQLisAccountExist, Boolean.class, accountId);
    }

    public Double getBalanceById(String accountId) {
        return template.queryForObject(SQLGetBalanceById, Double.class, accountId);
    }

    public Boolean withdrawFunds(String accountId, Double amount) {
        int rows = template.update(SQLWithdrawById, amount, accountId);
        return rows > 0;
    }

    public Boolean depositFunds(String accountId, Double amount) {
        int rows = template.update(SQLDepositById, amount, accountId);
        return rows > 0;
    }

    public String getNameById(String accountId) {
        return template.queryForObject(SQLGetNameById, String.class, accountId);
    }
}
