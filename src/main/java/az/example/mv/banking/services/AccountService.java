package az.example.mv.banking.services;

import az.example.mv.banking.exceptions.ServiceException;
import az.example.mv.banking.models.Account;
import az.example.mv.banking.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
@Log4j2
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Creates accounts after local validation. In case of any account is wrong exception thrown
     *
     * @param accounts
     */
    @Transactional
    public void openAccount(List<Account> accounts) {
        List<Account> ineligibleAccounts = validateMandatoryFields(accounts);
        if (!ineligibleAccounts.isEmpty())
            throw new ServiceException("Some of given accounts are incorrect!", HttpStatus.BAD_REQUEST);

        populateWithIds(accounts);//FIXME: it can be done on DB using trigger. To keep it simple I am generating in java

        Iterable<Account> savedAccounts = accountRepository.saveAll(accounts);
        log.info("Saved accounts are: {}", savedAccounts);
    }


    private void populateWithIds(List<Account> accounts) {
        accounts.forEach(acc -> acc.setId(UUID.randomUUID().toString()));
    }

    private List<Account> validateMandatoryFields(List<Account> accounts) {
        //TODO: it should be clarified with PO if we need to store ineligible/wrong accounts in a different table
        List<Account> ineligibleAccounts = accounts.stream()
                .filter(acc -> isNull(acc.getCustomerId()) || isNull(acc.getCurrency()))
                .collect(Collectors.toList());

        accounts.removeAll(ineligibleAccounts);
        return ineligibleAccounts;
    }
}
