package africa.semicolon.wollet.service;

import africa.semicolon.wollet.dto.request.RegisterCustomerRequest;
import africa.semicolon.wollet.dto.response.RegisterCustomerResponse;
import africa.semicolon.wollet.exception.InvalidDetailException;

public interface CustomerService {
    RegisterCustomerResponse registerUser(RegisterCustomerRequest request) throws InvalidDetailException;

}
