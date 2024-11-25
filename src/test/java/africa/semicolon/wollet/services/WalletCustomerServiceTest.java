package africa.semicolon.wollet.services;
import africa.semicolon.wollet.dto.response.DepositResponse;
import africa.semicolon.wollet.dto.request.CustomerDepositRequest;

import africa.semicolon.wollet.exception.TransactionNotFoundException;
import africa.semicolon.wollet.exception.USerNotFoundException;
import africa.semicolon.wollet.exception.WalletNotFoundException;
import africa.semicolon.wollet.models.Transaction;
import africa.semicolon.wollet.service.WalletCustomerService;
import africa.semicolon.wollet.service.TransactionServiceImp;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static africa.semicolon.wollet.dto.response.TransactionStatus.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Sql(scripts = {"/db/data.sql"})
public class WalletCustomerServiceTest {

    @Autowired
    private WalletCustomerService walletCustomerService;
    @Autowired
    private TransactionServiceImp transactionService;
    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testCustomerCanDeposit() throws WalletNotFoundException, USerNotFoundException {
        Long customerId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String description = "description";
        CustomerDepositRequest request = new CustomerDepositRequest();
        mapper(request, customerId, amount, description);
        DepositResponse response = walletCustomerService.deposit(request);

        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals("SUCCESSFUL", response.getMessage());
    }


    @Test
    public void testTransactionIsCreatedForDeposit() throws WalletNotFoundException, USerNotFoundException, TransactionNotFoundException {
        Long customerId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String description = "description";
        CustomerDepositRequest request = new CustomerDepositRequest();
        mapper(request, customerId, amount, description);
        DepositResponse response = walletCustomerService.deposit(request);
        Transaction transaction  =transactionService.getTransaction(response.getTransactionId());
        assertNotNull(response);
        assertNotNull(transaction);
        assertEquals(SUCCESS,transaction.getStatus());
        System.out.println(transaction);
    }

    private static void mapper(CustomerDepositRequest request, Long customerId, BigDecimal amount, String description) {
        request.setCustomerId(customerId);
        request.setAmount(amount);
        request.setDescription(description);
    }

}