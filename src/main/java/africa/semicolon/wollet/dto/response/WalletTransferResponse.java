package africa.semicolon.wollet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletTransferResponse {

    private String status;
    private String message;
    @JsonProperty("data")
    private FlutterwaveTransferData data;
}
