package com.senai.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AppointmentRequestDto(@JsonProperty("class_content") String classContent, @JsonFormat(pattern = "yyyy-MM-dd HH:mm")@JsonProperty("appointment_date") LocalDateTime appointmentDate, Long student, Long teacher) {
}
