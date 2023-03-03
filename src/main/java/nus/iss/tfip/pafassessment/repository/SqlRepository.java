package nus.iss.tfip.pafassessment.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafassessment.model.Account;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class SqlRepository {

    @Autowired
    JdbcTemplate template;

    private String SQLGetAllAccounts = "SELECT * FROM accounts";

    public List<Account> getAllAccounts() {
        List<Account> accList = new LinkedList<>();
        accList = template.query(SQLGetAllAccounts, BeanPropertyRowMapper.newInstance(Account.class));
        return accList;
    }
}
