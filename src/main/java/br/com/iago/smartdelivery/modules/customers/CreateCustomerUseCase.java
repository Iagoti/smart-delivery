package br.com.iago.smartdelivery.modules.customers;

import br.com.iago.smartdelivery.ViaCepDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreateCustomerUseCase {

    private CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerrepository){
        this.customerRepository = customerrepository;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void execute(CustomerEntity customerEntity) throws Exception{
        String urlViaApi = "http://viacep.com.br/ws/"+customerEntity.getZipcode()+"/json/";
        try{
            ViaCepDTO viaCepDTO = restTemplate.getForObject(urlViaApi, ViaCepDTO.class);
            customerEntity.setAddress(viaCepDTO.getLogradouro());
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao consultar o CEP " + customerEntity.getZipcode());
        }

        this.customerRepository.findByEmail(customerEntity.getEmail())
                .ifPresent(item -> {
                    throw new IllegalArgumentException("O email " + customerEntity.getEmail() + " já existe.");
                });

        this.customerRepository.save(customerEntity);
        System.out.println("Entity: " + customerEntity);
    }
}
