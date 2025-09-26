package br.com.iago.smartdelivery.modules.customers.dto;

import jakarta.persistence.Column;

public class CreateCustomerRequest {

    private String name;
    private String phone;
    private String email;
    private String zipcode;
    private String password;

    public CreateCustomerRequest(String name, String phone, String email, String zipcode, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.zipcode = zipcode;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
