package com.senai.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.TeacherRequestDto;
import com.senai.api.domain.dto.TeacherResponseDto;
import com.senai.api.service.TeacherService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {
    @Autowired
    private MockMvc controller;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TeacherService service;

    TeacherRequestDto getTeacherRequestDto(){
        return  new TeacherRequestDto("Nome","Sobrenome","Cpf", LocalDate.of(1994,11,12),"teste",true);
    }

    TeacherResponseDto getTeacherResponseDto(){
        return  new  TeacherResponseDto(1L,"Nome","Sobrenome", LocalDate.of(1994,11,12),"teste",true);
    }

    @Test
    void shouldReturn404WhenIdDoesNotExist() throws Exception {

        controller.perform(get("/api/v1/teacher/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenIdExist() throws Exception {
        var teacher = new Teacher();
        teacher.setId(1L);

        given(service.findById(1L)).willReturn(Optional.of(teacher));

        controller.perform(get("/api/v1/teacher/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenUrlNotExists() throws Exception {

        controller.perform(get("/api/v1/teacher/"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldReturn204WhenSucessUpdate() throws Exception {
        var dto = getTeacherResponseDto();
        when(service.update(ArgumentMatchers.any())).thenReturn(true);

        var result = controller.perform(put("/api/v1/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void shouldReturn404WhenErrorUpdate() throws Exception {
        var dto = getTeacherResponseDto();
        when(service.update(ArgumentMatchers.any())).thenReturn(false);

        var result = controller.perform(put("/api/v1/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}