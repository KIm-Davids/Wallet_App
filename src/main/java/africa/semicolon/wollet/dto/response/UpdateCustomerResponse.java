package africa.semicolon.wollet.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerResponse {

    private String message;
    private TransactionStatus status;

}
