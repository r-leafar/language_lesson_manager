package com.senai.api.domain.dto;

import java.time.LocalDate;

public record TeacherResponseDto(Long id,String name, String surname, LocalDate birth, String specialty, Boolean status) {
}
