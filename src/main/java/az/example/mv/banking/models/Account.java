package az.example.mv.banking.models;

import az.example.mv.banking.types.Currency;
import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class Account {
    @Id
    private String id;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance = BigDecimal.ZERO;
}
