package br.com.iago.smartdelivery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreateCustomerUseCase {

    @PersistenceContext
    private EntityManager entityManager;

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

        String jpql = "select count(C) from CustomerEntity C where C.email = :email";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("email", customerEntity.getEmail()).getSingleResult();

        if(count > 0 ){
            throw new IllegalArgumentException("O email " + customerEntity.getEmail() + " já existe.");
        }

        entityManager.persist(customerEntity);
        System.out.println("Entity: " + customerEntity);
    }
}
