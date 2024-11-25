package africa.semicolon.wollet.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindCustomerResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;

}
