package com.senai.api.domain.dto;

import com.senai.api.domain.Contact;
import com.senai.api.domain.ResidentialAddress;

import java.time.LocalDate;

public record StudentResponseDto(Long id, String name, String surname, LocalDate birth, ResidentialAddress address, Contact contact) {
}
