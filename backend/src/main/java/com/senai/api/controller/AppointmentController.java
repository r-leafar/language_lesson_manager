package com.senai.api.controller;

import com.senai.api.domain.Appointment;
import com.senai.api.domain.Student;
import com.senai.api.domain.dto.AppointmentRequestDto;
import com.senai.api.domain.dto.AppointmentResponseDto;
import com.senai.api.domain.exception.AppointmentException;
import com.senai.api.mapper.AppointmentMapper;
import com.senai.api.service.AppointmentService;
import com.senai.api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService service;
    @Autowired
    private TeacherService serviceTeacher;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(@RequestBody AppointmentRequestDto dto){
        var newAppointment = this.service.createAppointment(dto);

        if(newAppointment.isPresent()){
            var response = AppointmentMapper.toDto(newAppointment.get());
            return   ResponseEntity.ok(response);
        }else{
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentFromTeacher(@PathVariable long id){

       var teacher = serviceTeacher.findById(id);

        if (!teacher.isPresent())
            throw new AppointmentException("The teacher id doesn't exists.");

        var optList = service.findAppointmentForTeacher(teacher.get());

        if(optList.isPresent()){

            var response = optList.get().stream().map(AppointmentMapper::toDto).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.noContent().build();
    }

}
