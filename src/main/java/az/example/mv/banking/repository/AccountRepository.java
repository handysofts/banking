package az.example.mv.banking.repository;

import az.example.mv.banking.models.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}
