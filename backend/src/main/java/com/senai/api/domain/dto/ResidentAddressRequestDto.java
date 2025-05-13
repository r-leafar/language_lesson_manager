package com.senai.api.domain.dto;

public record ResidentAddressRequestDto(String cep,String street,String neighborhood,String number,String state,String city) {
}
