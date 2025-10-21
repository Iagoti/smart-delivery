package br.com.iago.smartdelivery.modules.customers;

import br.com.iago.smartdelivery.ViaCepDTO;
import br.com.iago.smartdelivery.integrations.zipcode.ViaCEPClient;
import br.com.iago.smartdelivery.modules.customers.dto.CreateCustomerRequest;
import br.com.iago.smartdelivery.modules.users.CreateUserCase;
import br.com.iago.smartdelivery.modules.users.Role;
import br.com.iago.smartdelivery.modules.users.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreateCustomerUseCase {

    private CustomerRepository customerRepository;
    private CreateUserCase createUserCase;
    private ViaCEPClient viaCEPClient;

    public CreateCustomerUseCase(
            CustomerRepository customerrepository,
            CreateUserCase createUserCase,
            ViaCEPClient viaCEPClient
    ){
        this.customerRepository = customerrepository;
        this.createUserCase = createUserCase;
        this.viaCEPClient = viaCEPClient;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void execute(CreateCustomerRequest createCustomerRequest) throws Exception{
        ViaCepDTO viaCepDTO = this.viaCEPClient.findZipCode(createCustomerRequest.getZipcode());
        CustomerEntity customerEntity = new CustomerEntity();
        try{
            customerEntity.setAddress(viaCepDTO.getLogradouro());
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao consultar o CEP " + createCustomerRequest.getZipcode());
        }

        UserEntity userEntity = this.createUserCase.execute(createCustomerRequest.getEmail(), createCustomerRequest.getPassword(), Role.CUSTOMER);

        customerEntity.setName(createCustomerRequest.getName());
        customerEntity.setPhone(createCustomerRequest.getPhone());
        customerEntity.setZipcode(createCustomerRequest.getZipcode());
        customerEntity.setEmail(createCustomerRequest.getEmail());
        customerEntity.setUserId(userEntity.getId());

        this.customerRepository.findByEmail(customerEntity.getEmail())
                .ifPresent(item -> {
                    throw new IllegalArgumentException("O email " + customerEntity.getEmail() + " já existe.");
                });

        this.customerRepository.save(customerEntity);
        System.out.println("Entity: " + customerEntity);
    }
}
