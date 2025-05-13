package com.senai.api.controller;

import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.TeacherRequestDto;
import com.senai.api.domain.dto.TeacherResponseDto;
import com.senai.api.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody TeacherRequestDto dto) {
        var newTeacher = this.service.createTeacher(dto);
        return newTeacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody TeacherResponseDto dto){
            return this.service.update(dto)?
             ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {

        Optional<Teacher> teacher = this.service.findById(id);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<TeacherResponseDto>> getAllTeacher(@PathVariable int page, @PathVariable int size) {
        List<TeacherResponseDto> newListStudent = this.service.findAllTeachers(page,size);
        return ResponseEntity.ok(newListStudent);

    }
}
