package com.senai.api.domain.dto;

import java.time.LocalDate;

public record TeacherRequestDto(String name, String surname, String cpf, LocalDate birth,String specialty,Boolean status) {
}
