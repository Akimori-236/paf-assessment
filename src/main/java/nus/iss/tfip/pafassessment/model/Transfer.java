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
    private String id;
    @NotEmpty(message="From account cannot be empty")
    private String fromAccount;
    @NotEmpty(message="To account cannot be empty")
    private String toAccount;
    // C3
    @NotNull(message="Amount cannot be empty")
    @Positive(message = "Amount must be a positive number")
    // C4
    @DecimalMin(value="10.00", message="Minimum amount is $10.00")
    @Digits(integer=50, fraction=2, message="Maximum amount is 50 digits and 2 decimal places")
    private Double amount;
    private String comments;

    // private generateID() {
    //     uuid
    // }
}
