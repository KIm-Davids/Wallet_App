package africa.semicolon.wollet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletTransferRequest {

    private BigDecimal amount;
    @JsonProperty("account_bank")
    private String bank;
    @JsonProperty("account_number")
    private String narration;
    private String currency;
    private String accountNumber;
}
