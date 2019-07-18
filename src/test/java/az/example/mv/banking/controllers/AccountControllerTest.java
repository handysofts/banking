package az.example.mv.banking.controllers;

import az.example.mv.banking.models.Account;
import az.example.mv.banking.repository.AccountRepository;
import az.example.mv.banking.services.AccountService;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import static az.example.mv.banking.types.Currency.AZN;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({AccountController.class, AccountService.class})
public class AccountControllerTest {
    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @Before
    public void init() {
        when(accountRepository.saveAll(anyIterable())).then(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void openAccount() throws Exception {
        this.mockMvc.perform(
                post("/account")
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .content(prepareAccountRequest()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    public void openAccountWithoutAccountDetailsInBody() throws Exception {
        this.mockMvc.perform(post("/account"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private String prepareAccountRequest() {
        Account acc = new Account();
        acc.setCustomerId(7L);
        acc.setCurrency(AZN);
        List<Account> accounts = Lists.newArrayList(acc);
        return gson.toJson(accounts);
    }
}
