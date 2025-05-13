package com.senai.api.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentRequestDto(String name, String surname, String cpf, LocalDate birth, ResidentAddressRequestDto address, ContactRequestDto contact) {
}
