package com.senai.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.api.domain.Contact;
import com.senai.api.domain.ResidentialAddress;
import com.senai.api.domain.Student;
import com.senai.api.domain.dto.StudentResponseDto;
import com.senai.api.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc controller;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService service;

    @Test
    void shouldReturn404WhenIdDoesNotExist() throws Exception {

        controller.perform(get("/api/v1/student/1"))
                .andExpect(status().isNotFound());
    }

    StudentResponseDto getStudentResponseDto(){
        var address = new ResidentialAddress(1L,"cep","rua","bairro","111","SP","ubatuba");
        var contact = new Contact(1L,null,"12991953333",null);
        return  new StudentResponseDto(1L,"Nome","Sobrenome", LocalDate.of(1994,11,12),address,contact);
    }

    @Test
    void shouldReturn200WhenIdExist() throws Exception {
        var student = new Student();
        student.setId(1L);

        given(service.findById(1L)).willReturn(Optional.of(student));

        controller.perform(get("/api/v1/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenUrlNotExists() throws Exception {

        controller.perform(get("/api/v1/student/"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldReturn204WhenSucessUpdate() throws Exception {
        StudentResponseDto dto = getStudentResponseDto();
        when(service.update(ArgumentMatchers.any())).thenReturn(true);

        var result = controller.perform(put("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void shouldReturn404WhenErrorUpdate() throws Exception {
        StudentResponseDto dto = getStudentResponseDto();
        when(service.update(ArgumentMatchers.any())).thenReturn(false);

        var result = controller.perform(put("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}