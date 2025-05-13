package com.senai.api.service;

import com.senai.api.domain.Teacher;
import com.senai.api.domain.dto.*;
import com.senai.api.domain.exception.AppointmentException;
import com.senai.api.mapper.TeacherMapper;
import com.senai.api.repository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository repository;

    @InjectMocks
    private TeacherService service;


     TeacherRequestDto getTeacherRequestDto(){
        return  new TeacherRequestDto("Nome","Sobrenome","Cpf", LocalDate.of(1994,11,12),"teste",true);
    }
    TeacherResponseDto getTeacherResponseDto(){
        return  new  TeacherResponseDto(1L,"Nome","Sobrenome", LocalDate.of(1994,11,12),"teste",true);
    }

    List<Teacher> getTeachers(){
        var teacherA = new Teacher();
        teacherA.setId(1L);
        teacherA.setCpf("12345678911");
        var teacherB = new Teacher();
        teacherB.setId(2L);
        teacherB.setCpf("11987654321");
        return List.of(teacherA,teacherB);
    }

    @Test
    void ShouldReturnTeacherWhenCreate(){

        var dto = getTeacherRequestDto();
        var teacher = TeacherMapper.toEntity(dto);
        var result = service.createTeacher(dto);

        Assertions.assertEquals(teacher,result.get());
    }

    @Test
    void ShouldThrowWhenCreate(){
        TeacherRequestDto dto =mock(TeacherRequestDto.class);
        Teacher teacher = mock(Teacher.class);

        Mockito.when(repository.save(ArgumentMatchers.any()))
                .thenThrow(new AppointmentException(""));

        Assertions.assertThrows(AppointmentException.class,() ->service.createTeacher(dto));
    }


    @Test
    void shouldReturnTeacherWhenFindById() {
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(mockTeacher));

        var result = service.findById(1L);

        Assertions.assertEquals(mockTeacher,result.get());
    }
    @Test
    void shouldReturnEmptyWhenFindById() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.empty());

        var result = service.findById(1L);

        Assertions.assertEquals(Optional.empty(),result);
    }


    @Test
    void shouldReturnFalseWhenUpdate() {
        var dto = mock(TeacherResponseDto.class);

        Mockito.when(repository.findById(0L))
                .thenReturn(Optional.empty());
        var result = service.update(dto);

        Assertions.assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenUpdate() {
        var dto = getTeacherResponseDto();

        Teacher teacher = mock(Teacher.class);
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(teacher));
        var result = service.update(dto);

        Assertions.assertTrue(result);
    }

    @Test
    void ShouldReturnListOfTeacher() {
        List<Teacher> teachers = getTeachers();

        Page<Teacher> teacherPage = new PageImpl<>(teachers);
        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(teacherPage);

        var result = service.findAllTeachers(0,10);

        Assertions.assertEquals(2, result.size());

    }
}