package africa.semicolon.wollet.services;

import africa.semicolon.wollet.dto.request.FindCustomerRequest;
import africa.semicolon.wollet.dto.request.RegisterCustomerRequest;
import africa.semicolon.wollet.dto.request.UpdateCustomerRequest;
import africa.semicolon.wollet.dto.response.FindCustomerResponse;
import africa.semicolon.wollet.dto.response.RegisterCustomerResponse;
import africa.semicolon.wollet.dto.response.UpdateCustomerResponse;
import africa.semicolon.wollet.exception.CustomerNotFoundException;
import africa.semicolon.wollet.exception.InvalidDetailException;
import africa.semicolon.wollet.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.wollet.dto.response.TransactionStatus.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerServiceTest {


    @Autowired
    private CustomerServiceImpl walletCustomerService;
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Test
    public void testThatUserCanRegister() throws InvalidDetailException {
        RegisterCustomerRequest request = buildCustomerRequest();
        RegisterCustomerResponse response = walletCustomerService.registerUser(request);
        assertNotNull(response);
    }

    @Test
    public void testEmailVerification() {
        RegisterCustomerRequest request = testEmailMethod();
        assertThrows(InvalidDetailException.class, () -> walletCustomerService.registerUser(request));
    }

    @Test
    public void testThatCustomerCanUpdateDetails(){
        UpdateCustomerRequest request = updateCustomerDetails();
        UpdateCustomerResponse response = customerServiceImpl.updateCustomer(request);
        assertNotNull(response);
        assertEquals(SUCCESS, response.getStatus());
    }

    @Test
    public void testToFindCustomer() throws CustomerNotFoundException {
        FindCustomerRequest request = findCustomer();
        FindCustomerResponse response = customerServiceImpl.findCustomerBy(request);
        System.out.println(response.toString());
        assertNotNull(response);
    }

    private static FindCustomerRequest findCustomer(){
        FindCustomerRequest request = new FindCustomerRequest();
        request.setId(2L);
        return request;
    }

    private static UpdateCustomerRequest updateCustomerDetails(){
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setId(1L);
        request.setEmail("JaneDoe@gmail.com");
        request.setFirstName("Jane");
        request.setLastName("Doe");
        request.setPassword("password");
        request.setUserName("Jane");
        return request;
    }

    private static RegisterCustomerRequest buildCustomerRequest(){
        RegisterCustomerRequest request = new RegisterCustomerRequest();
        request.setId(3L);
        request.setEmail("JohnDoe@gmail.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password");
        request.setUserName("JDoe");
        return request;
    }

    private static RegisterCustomerRequest testEmailMethod(){
        RegisterCustomerRequest request = new RegisterCustomerRequest();
        request.setId(1L);
        request.setEmail("JohnDoe@@gmail.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password");
        request.setUserName("JDoe");
        return request;
    }

}
