package africa.semicolon.wollet.service;

import africa.semicolon.wollet.dto.request.FlutterwaveTransferRequest;
import africa.semicolon.wollet.dto.request.WalletDepositRequest;
import africa.semicolon.wollet.dto.response.FlutterwaveTransferData;
import africa.semicolon.wollet.dto.response.FlutterwaveTransferResponse;
import africa.semicolon.wollet.dto.response.WalletDepositResponse;
import africa.semicolon.wollet.exception.USerNotFoundException;
import africa.semicolon.wollet.exception.WalletNotFoundException;
import africa.semicolon.wollet.models.Wallet;
import africa.semicolon.wollet.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

import static africa.semicolon.wollet.dto.response.TransactionStatus.SUCCESS;
import static org.springframework.http.HttpMethod.POST;

@Service
@AllArgsConstructor
public class AppWalletService implements WalletService {

    private final WalletRepository repository;

    @Override
    public WalletDepositResponse deposit(WalletDepositRequest deposit) throws WalletNotFoundException {
        Wallet wallet = repository.findById(deposit.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(
                        String.format("wallet with id %d not found", deposit.getWalletId())));

        BigDecimal walletCurrentBalance = wallet.getBalance();
        BigDecimal walletBalanceAfterDeposit = walletCurrentBalance.add(deposit.getAmount());
        wallet.setBalance(wallet.getBalance().add(deposit.getAmount()));
        repository.save(wallet);
        WalletDepositResponse response = new WalletDepositResponse();
        response.setStatus(SUCCESS.toString());
        response.setAmount(deposit.getAmount().toEngineeringString());
        return response;
    }
}
//
//    @Override
//    public FlutterwaveTransferResponse transfer(FlutterwaveTransferRequest transferRequest){
//        String authorization = "";
//        RestTemplate restTemplate = new RestTemplate();
//        RequestEntity<FlutterwaveTransferRequest> request = new  RequestEntity<>(transferRequest, POST, URI.create("https://api.Flutterwave.com/v3/trsnsfers");
//        var response = restTemplate.postForEntity("https://apiflutterwave.com/v3/trnasfers", request, FlutterwaveTransferResponse.class);
//        return response.getBody();
//    }
//
//
//}
