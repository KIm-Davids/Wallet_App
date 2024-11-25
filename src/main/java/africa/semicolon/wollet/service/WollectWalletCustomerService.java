package africa.semicolon.wollet.service;

import africa.semicolon.wollet.dto.request.CreateTransactionRequest;
import africa.semicolon.wollet.dto.request.RegisterCustomerRequest;
import africa.semicolon.wollet.dto.request.WalletDepositRequest;
import africa.semicolon.wollet.dto.response.*;
import africa.semicolon.wollet.dto.request.CustomerDepositRequest;
import africa.semicolon.wollet.exception.InvalidDetailException;
import africa.semicolon.wollet.exception.USerNotFoundException;
import africa.semicolon.wollet.exception.WalletNotFoundException;
import africa.semicolon.wollet.models.Customer;
import africa.semicolon.wollet.models.Wallet;
import africa.semicolon.wollet.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class WollectWalletCustomerService implements WalletCustomerService {

    private final WalletService walletService;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final TransactionService transactionService;


    @Override
    public DepositResponse deposit(CustomerDepositRequest request) throws USerNotFoundException, WalletNotFoundException {
        Customer customer = customerRepository.findById(request.getCustomerId())
                                              .orElseThrow(()-> new USerNotFoundException(
                                              String.format("Customer with id %d not found", request.getCustomerId())));
        Wallet customerWallet = customer.getWallet();
        WalletDepositRequest walletDepositRequest = createWalletDepositRequestFrom(request, customerWallet);
        WalletDepositResponse response = walletService.deposit(walletDepositRequest);

        CreateTransactionRequest transactionRequest = new CreateTransactionRequest();

        transactionMapping(request, transactionRequest, customerWallet, response);
        CreateTransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        var depositResponse =  modelMapper.map(response, DepositResponse.class);
        depositResponse.setTransactionId(transactionResponse.getId());
        depositResponse.setMessage("SUCCESSFUL");
        return depositResponse;
    }


    private static void transactionMapping(CustomerDepositRequest request, CreateTransactionRequest transactionRequest, Wallet customerWallet, WalletDepositResponse response) {
        transactionRequest.setRecipientAccount(customerWallet.getAccountNumber());
        transactionRequest.setAmount(request.getAmount());
        transactionRequest.setStatus(TransactionStatus.valueOf(response.getStatus()));
        transactionRequest.setDescription(request.getDescription());
        transactionRequest.setSenderAccount("loading");
    }

    private static WalletDepositRequest createWalletDepositRequestFrom(CustomerDepositRequest request, Wallet customerWallet) {
        WalletDepositRequest walletDepositRequest = new WalletDepositRequest();
        walletDepositRequest.setWalletId(customerWallet.getId());
        walletDepositRequest.setAmount(request.getAmount());
        return walletDepositRequest;
    }
}
