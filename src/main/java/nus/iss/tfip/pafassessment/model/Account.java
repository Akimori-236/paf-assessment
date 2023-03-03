package nus.iss.tfip.pafassessment.model;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String account_id;
    private String name;
    private Double balance;
}
