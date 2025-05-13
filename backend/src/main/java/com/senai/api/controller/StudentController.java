package com.senai.api.controller;

import com.senai.api.domain.Student;
import com.senai.api.domain.dto.StudentRequestDto;
import com.senai.api.domain.dto.StudentResponseDto;
import com.senai.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody StudentRequestDto dto){
        var newStudent = this.service.createStudent(dto);
        return newStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody StudentResponseDto dto){
        var result = service.update(dto);

        return result
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){

        Optional<Student> student = this.service.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<StudentResponseDto>> getAllStudent(@PathVariable int page, @PathVariable int size){
        List<StudentResponseDto> newListStudent = this.service.findAllStudents(page,size);
        return ResponseEntity.ok(newListStudent);
    }
}
