package com.senai.api.service;

import com.senai.api.domain.Appointment;
import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.AppointmentRequestDto;
import com.senai.api.domain.exception.AppointmentException;
import com.senai.api.mapper.AppointmentMapper;
import com.senai.api.repository.AppointmentRepository;
import com.senai.api.repository.StudentRepository;
import com.senai.api.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;
    @Autowired
    private StudentRepository studantRepository;
    @Autowired
    private TeacherRepository teacherRepository;

     private final int  MAX_QTD_APPOINTMENT=2;

    public Optional<Appointment> createAppointment(AppointmentRequestDto dto) throws AppointmentException {

      var studant = studantRepository.getReferenceById(dto.student());
      var teacher = teacherRepository.getReferenceById(dto.teacher());

        var appointment = AppointmentMapper.toEntity(dto,studant,teacher);

        appointment.isBefore24hours();

        var total = repository.countByAppointmentDateAndTeacher(appointment.getAppointmentDate().toLocalDate(),appointment.getTeacher().getId());

        appointment.validateDailyAppointmentLimit(total,MAX_QTD_APPOINTMENT);

        repository.save(appointment);
        return Optional.of(appointment);

    }

    public Optional<List<Appointment>> findAppointmentForTeacher(Teacher teacher){

        var response = repository.findAppointmentByTeacher(teacher.getId());

        return Optional.of(response);

    }
}
