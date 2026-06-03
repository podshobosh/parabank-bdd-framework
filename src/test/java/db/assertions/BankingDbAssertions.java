package db.assertions;

import db.model.AccountRecord;
import db.model.CustomerRecord;
import db.repository.AccountRepository;
import db.repository.CustomerRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BankingDbAssertions {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public BankingDbAssertions() {
        this(new CustomerRepository(), new AccountRepository());
    }

    public BankingDbAssertions(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public CustomerRecord assertCustomerWasPersisted(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new AssertionError("Expected customer to be persisted in CUSTOMER table. username=" + username));
    }

    public AccountRecord assertAccountBelongsToCustomer(int accountId, int customerId) {
        AccountRecord account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AssertionError("Expected account to be persisted in ACCOUNT table. accountId=" + accountId));
        assertEquals(account.customerId(), customerId, "DB account ownership mismatch");
        return account;
    }

    public void assertCustomerHasAtLeastOneAccount(int customerId) {
        assertTrue(!accountRepository.findAccountsByCustomerId(customerId).isEmpty(),
                "Expected customer to have at least one account. customerId=" + customerId);
    }
}
