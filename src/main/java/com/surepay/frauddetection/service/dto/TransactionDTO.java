package com.surepay.frauddetection.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {

    private Double reference;
    private String accountNumber;
    private BigDecimal startBalance;
    private BigDecimal mutation;
    private String description;
    private BigDecimal endBalance;

    @JsonIgnore
    private boolean stored;
    @JsonIgnore
    private TransactionDTO transactionDTO;

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionReference=" + reference +
                ", accountNumber='" + accountNumber + '\'' +
                ", startBalance=" + startBalance +
                ", mutation=" + mutation +
                ", description='" + description + '\'' +
                ", endBalance=" + endBalance +
                ", stored=" + stored +
                '}';
    }
}
