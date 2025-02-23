package com.lucas.pedido_ms.domains.address;


import jakarta.persistence.*;

@Table(name = "addresses")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String number;

    private String state;

    private String zip;

    @Column(name = "user_id")
    private String userId;

    public Address(){

    }

    public Address(String street, String number, String state, String zip, String userId) {
        this.street = street;
        this.number = number;
        this.state = state;
        this.zip = zip;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getUser() {
        return userId;
    }

    public void setUser(String user) {
        this.userId = user;
    }
}

