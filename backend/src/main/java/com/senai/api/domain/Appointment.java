package com.senai.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senai.api.domain.exception.AppointmentException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="class_content")
    private String classContent;

    @Column(name="appointment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime appointmentDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "studantid", referencedColumnName = "id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacherid", referencedColumnName = "id")
    private Teacher teacher;

    public void isBefore24hours(){
        if (appointmentDate!= null) {
            if (LocalDateTime.now().plusHours(24).isBefore(getAppointmentDate()))
                throw new AppointmentException("Appointments require a 24-hour advance notice.");
        }
    }

    public void validateDailyAppointmentLimit(long currentAppointments, long maxAllowed){
        if(currentAppointments >= maxAllowed) {
            String message = String.format("You cannot have more than %d appointments per day.", maxAllowed);
            throw new AppointmentException(message);
        }
    }
}
