package com.senai.api.service;

import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.TeacherRequestDto;
import com.senai.api.domain.dto.TeacherResponseDto;
import com.senai.api.mapper.TeacherMapper;
import com.senai.api.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    public Optional<Teacher> createTeacher(TeacherRequestDto dto){

        var teacher = TeacherMapper.toEntity(dto);

        repository.save(teacher);

        return Optional.of(teacher);
    }

    public Optional<Teacher> findById(Long id) {
        return repository.findById(id);

    }
    public boolean update(TeacherResponseDto dto) {
        var itemToSave = repository.findById(dto.id());

        if (itemToSave.isPresent()){
            var teacher = itemToSave.get();
            var teacherUpdated = TeacherMapper.toEntity(dto);
            teacherUpdated.setCpf(teacher.getCpf());
            repository.save(teacher);
            return true;
        }
        return false;
    }

    public List<TeacherResponseDto> findAllTeachers(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("name").ascending());
        Page<Teacher> teacherPage = repository.findAll(pageable);
        return  teacherPage.map( s -> new TeacherResponseDto(s.getId(),s.getName(),s.getSurname(),s.getBirth(),s.getSpecialty(),s.getStatus())).toList();
    }
}
