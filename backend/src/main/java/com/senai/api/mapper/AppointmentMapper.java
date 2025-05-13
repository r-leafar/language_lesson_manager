package com.senai.api.mapper;

import com.senai.api.domain.Appointment;
import com.senai.api.domain.Student;
import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.AppointmentRequestDto;
import com.senai.api.domain.dto.AppointmentResponseDto;
import com.senai.api.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AppointmentMapper {

       public static Appointment toEntity(AppointmentRequestDto dto,Student studant, Teacher teacher) {
        var appointment = new Appointment();

        appointment.setStudent(studant);
        appointment.setTeacher(teacher);

        appointment.setClassContent(dto.classContent());
        appointment.setAppointmentDate(dto.appointmentDate());
        return appointment;
    }
    public static AppointmentResponseDto toDto(Appointment appointment) {

           return new  AppointmentResponseDto(appointment
                   .getId(),appointment.getClassContent(),
                   appointment.getAppointmentDate(),
                   appointment.getStudent().getId(),appointment.getTeacher().getId());
    }
}
