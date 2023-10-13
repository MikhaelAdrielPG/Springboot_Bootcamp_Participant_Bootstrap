package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantResponse {
    private String name;
    private String address;
    private String phone;
    @JsonProperty("actived")
    private Boolean active;

    public ParticipantResponse(String name, String address, String phone, Boolean active) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
