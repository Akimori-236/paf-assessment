package nus.iss.tfip.pafassessment.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private int id;
    @NotEmpty(message="from account cannot be empty")
    private String fromAccount;
    @NotEmpty(message="to account cannot be empty")
    private String toAccount;
    @Positive(message = "amount must be a positive number")
    @DecimalMin(value="0.01", message="Minimum amount is 0.01")
    @Digits(integer=50, fraction=2, message="Maximum amount is 50 digits and 2 decimal places")
    private Double amount;
    private String comments;
}
