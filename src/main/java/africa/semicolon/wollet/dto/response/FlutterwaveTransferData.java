package africa.semicolon.wollet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlutterwaveTransferData {

    private int id;
    @JsonProperty("account_number")
    private String account_number;
    @JsonProperty("bank_code")
    private String bank_code;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("created_at")
    private String createdAt;
    private String currency;
    @JsonProperty("debit_currency")
    private String debitCurrency;
    private int amount;
    private int fee;
    private String status;
    private String reference;
    @JsonProperty("requires_approval")
    private String requiresApproval;
    @JsonProperty("is_approved")
    private int isApproved;
    @JsonProperty("bank_name")
    private String bankName;
}
