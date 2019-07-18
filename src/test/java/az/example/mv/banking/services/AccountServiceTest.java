package az.example.mv.banking.services;

import az.example.mv.banking.exceptions.ServiceException;
import az.example.mv.banking.models.Account;
import az.example.mv.banking.repository.AccountRepository;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.google.common.collect.Iterables;

import static az.example.mv.banking.types.Currency.AZN;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;
    @Captor
    private ArgumentCaptor<Iterable<Account>> accountsCaptor;

    @Before
    public void init() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
        MockitoAnnotations.initMocks(this);

        when(accountRepository.saveAll(anyIterable())).thenReturn(Lists.emptyList());
    }

    @Test
    public void openAccount() {
        //given
        List<Account> accountsToOpen = generateAccounts();

        //when
        accountService.openAccount(accountsToOpen);

        //then
        verify(accountRepository).saveAll(accountsCaptor.capture());
        final Iterable<Account> accounts = accountsCaptor.getValue();
        assertThat(Iterables.size(accounts)).isEqualTo(accountsToOpen.size());
        accounts.forEach(acc -> assertThat(acc.getId()).isNotBlank());
    }

    @Test
    public void openAccount_missingMandatoryFields() {
        //when
        Throwable thrown = catchThrowable(() -> accountService.openAccount(newArrayList(new Account())));

        //then
        assertThat(thrown).isInstanceOf(ServiceException.class);
        ServiceException se = (ServiceException) thrown;
        assertThat(se.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private List<Account> generateAccounts() {
        Account acc = new Account();
        acc.setCustomerId(1L);
        acc.setCurrency(AZN);
        return newArrayList(acc);
    }

}
