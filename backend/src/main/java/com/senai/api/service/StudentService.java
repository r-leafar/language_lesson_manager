package com.senai.api.service;

import com.senai.api.domain.Student;
import com.senai.api.domain.dto.StudentRequestDto;
import com.senai.api.domain.dto.StudentResponseDto;
import com.senai.api.mapper.StudentMapper;
import com.senai.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Optional<Student> createStudent(StudentRequestDto dto){

            var studant = StudentMapper.toEntity(dto);

            repository.save(studant);
            return Optional.of(studant);

    }

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }
    public boolean update(StudentResponseDto dto) {
       var itemToSave = repository.findById(dto.id());

       if (itemToSave.isPresent()){
           var student = itemToSave.get();

           var studentUpdated = StudentMapper.toEntity(dto);

           studentUpdated.setCpf(student.getCpf());

           repository.save(studentUpdated);
           return true;
       }
        return false;

    }
    public List<StudentResponseDto> findAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("name").ascending());
        Page<Student> studentPage = repository.findAll(pageable);
        return  studentPage.map( s -> new StudentResponseDto(s.getId(),s.getName(),s.getSurname(),s.getBirth(),s.getAddress(),s.getContact())).toList();
    }
}
