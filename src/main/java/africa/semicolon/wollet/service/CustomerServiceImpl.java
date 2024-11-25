package africa.semicolon.wollet.service;

import africa.semicolon.wollet.dto.request.FindCustomerRequest;
import africa.semicolon.wollet.dto.request.RegisterCustomerRequest;
import africa.semicolon.wollet.dto.request.UpdateCustomerRequest;
import africa.semicolon.wollet.dto.response.FindCustomerResponse;
import africa.semicolon.wollet.dto.response.RegisterCustomerResponse;
import africa.semicolon.wollet.dto.response.TransactionStatus;
import africa.semicolon.wollet.dto.response.UpdateCustomerResponse;
import africa.semicolon.wollet.exception.CustomerNotFoundException;
import africa.semicolon.wollet.exception.InvalidDetailException;
import africa.semicolon.wollet.models.Customer;
import africa.semicolon.wollet.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    private void verifyCustomerEmail(RegisterCustomerRequest request) throws InvalidDetailException {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request.getEmail());
        if(!matcher.matches()){
            throw new InvalidDetailException("Invalid Details");
        }
    }

    @Override
    public RegisterCustomerResponse registerUser(RegisterCustomerRequest request) throws InvalidDetailException {
        RegisterCustomerResponse response = new RegisterCustomerResponse();
        verifyCustomerEmail(request);
        request.setWalletId(request.getWalletId());
        Customer customer = modelMapper.map(request, Customer.class);
        customerRepository.save(customer);
        response.setMessage("Registered User Successfully");
        response.setStatus(TransactionStatus.SUCCESS);
        return response;
    }

    public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest request){
        UpdateCustomerResponse response = new UpdateCustomerResponse();
        Customer customer = customerRepository.findCustomersById(request.getId());
        Customer newCustomer =  updateCustomerUsing(customer, request);
        customerRepository.save(newCustomer);
        response.setMessage("Updated User Successfully");
        response.setStatus(TransactionStatus.SUCCESS);
        return response;
    }

    public FindCustomerResponse findCustomerBy(FindCustomerRequest request) throws CustomerNotFoundException {
        FindCustomerResponse response = new FindCustomerResponse();
        Customer foundCustomer = customerRepository.findCustomersById(request.getId());
        if(foundCustomer == null){
            throw new CustomerNotFoundException("Customer not found\nPlease try again");
        }
        response.setEmail(foundCustomer.getEmail());
        response.setFirstName(foundCustomer.getFirstName());
        response.setUserName(foundCustomer.getUserName());
        response.setLastName(foundCustomer.getLastName());
        return response;
    }

    private static Customer updateCustomerUsing(Customer customer, UpdateCustomerRequest request){
        customer.setId(request.getId());
        customer.setFirstName(request.getFirstName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setLastName(request.getLastName());
        customer.setUserName(request.getUserName());
        return customer;
    }


}
