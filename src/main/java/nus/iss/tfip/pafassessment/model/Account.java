package nus.iss.tfip.pafassessment.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
public class Account {
    private int id;
    private String account_id;
    private String name;
    @DecimalMin(value="0.01", message="Minimum quantity is 1")
    @Digits(integer=50, fraction=2, message="Maximum quantity is 50 digits and 2 decimal places")
    private Double balance;
}
