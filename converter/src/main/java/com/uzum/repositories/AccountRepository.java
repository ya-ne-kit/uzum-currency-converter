package com.uzum.repositories;

import com.uzum.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAccountByCurrencyName(String name);

}
