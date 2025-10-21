package br.com.iago.smartdelivery.integrations.zipcode;

import br.com.iago.smartdelivery.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep-service", url = "${feign.client.config.viacep-service.url}")

public interface ViaCEPClient {

    @GetMapping("/{zipCode}/json")
    ViaCepDTO findZipCode(@PathVariable("zipCode") String zipCode );

}
