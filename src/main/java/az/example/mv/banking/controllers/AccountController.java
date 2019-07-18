package az.example.mv.banking.controllers;

import az.example.mv.banking.models.Account;
import az.example.mv.banking.models.api.Res;
import az.example.mv.banking.services.AccountService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController(value = "/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Res> openAccount(@RequestBody List<Account> accounts){
        accountService.openAccount(accounts);

        //TODO: messages should be localized using localization service. I don't have a time to provide that solution
        return new ResponseEntity<>(new Res("All requested accounts opened successfully!"), CREATED);
    }

}
