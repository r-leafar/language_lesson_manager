package com.senai.api.repository;

import com.senai.api.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("SELECT count(a) FROM Appointment a WHERE DATE(a.appointmentDate) = :date AND a.teacher.id = :teacherId")
   public Long countByAppointmentDateAndTeacher(@Param("date") LocalDate date, @Param("teacherId") Long teacherId);


    @Query("SELECT a FROM Appointment a WHERE a.teacher.id = :teacherId")
    public List<Appointment> findAppointmentByTeacher(@Param("teacherId")Long teacherId);

}
