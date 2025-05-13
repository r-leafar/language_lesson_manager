package com.senai.api.mapper;

import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.TeacherRequestDto;
import com.senai.api.domain.dto.TeacherResponseDto;

public class TeacherMapper {

    public static Teacher toEntity(TeacherResponseDto dto) {
        var teacher = new Teacher();

        teacher.setId(dto.id());
        teacher.setName(dto.name());
        teacher.setSurname(dto.surname());
        teacher.setBirth(dto.birth());
        teacher.setSpecialty(dto.specialty());
        teacher.setStatus(dto.status());
        return teacher;
    }
    public static Teacher toEntity(TeacherRequestDto dto) {
        var teacher = new Teacher();
        teacher.setName(dto.name());
        teacher.setSurname(dto.surname());
        teacher.setCpf(dto.cpf());
        teacher.setBirth(dto.birth());
        teacher.setSpecialty(dto.specialty());
        teacher.setStatus(dto.status());
        return teacher;
    }
}
