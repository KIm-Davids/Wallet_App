package africa.semicolon.wollet.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class CreateTransactionResponse {
    private Long id;
    private String recipientAccount;
    private String senderAccount;
    private BigDecimal amount;
    private String status;
    private LocalDateTime date;
    private String description;
}
