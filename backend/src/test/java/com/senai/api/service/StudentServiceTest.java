package com.senai.api.service;

import com.senai.api.domain.Contact;
import com.senai.api.domain.ResidentialAddress;
import com.senai.api.domain.Student;
import com.senai.api.domain.dto.ContactRequestDto;
import com.senai.api.domain.dto.ResidentAddressRequestDto;
import com.senai.api.domain.dto.StudentRequestDto;
import com.senai.api.domain.dto.StudentResponseDto;
import com.senai.api.domain.exception.AppointmentException;
import com.senai.api.mapper.StudentMapper;
import com.senai.api.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;


    StudentRequestDto getStudentRequestDto(){
        var address = new ResidentAddressRequestDto("cep","rua","bairro","111","SP","ubatuba");
        var contact = new ContactRequestDto(null,"12991953333",null);
       return  new StudentRequestDto("Nome","Sobrenome","Cpf", LocalDate.of(1994,11,12),address,contact);
    }
    StudentResponseDto getStudentResponseDto(){
        var address = new ResidentialAddress(1L,"cep","rua","bairro","111","SP","ubatuba");
        var contact = new Contact(1L,null,"12991953333",null);
        return  new StudentResponseDto(1L,"Nome","Sobrenome", LocalDate.of(1994,11,12),address,contact);
    }
    List<Student> getStudants(){
        var studantA = new Student();
        studantA.setId(1L);
        studantA.setCpf("12345678911");
        var studantB = new Student();
        studantB.setId(2L);
        studantB.setCpf("11987654321");
        return List.of(studantA,studantB);
    }

    @Test
    void ShouldReturnStudentWhenCreate(){

        var dto = getStudentRequestDto();
        var student = StudentMapper.toEntity(dto);
        var result = service.createStudent(dto);

        Assertions.assertEquals(student,result.get());
    }

    @Test
    void ShouldThrowWhenCreate(){
        StudentRequestDto dto =  getStudentRequestDto();
        Mockito.when(repository.save(ArgumentMatchers.any()))
                .thenThrow(new AppointmentException(""));

        Assertions.assertThrows(AppointmentException.class,() -> service.createStudent(dto));
    }


    @Test
    void shouldReturnStudentWhenFindById() {
        Student mockStudent = new Student();
        mockStudent.setId(1L);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(mockStudent));

        var result = service.findById(1L);

        Assertions.assertEquals(mockStudent,result.get());
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
        StudentResponseDto dto = mock(StudentResponseDto.class);

        Mockito.when(repository.findById(0L))
                .thenReturn(Optional.empty());
        var result = service.update(dto);

        Assertions.assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenUpdate() {
        StudentResponseDto dto = getStudentResponseDto();

        Student student = mock(Student.class);
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(student));
        var result = service.update(dto);

        Assertions.assertTrue(result);
    }

    @Test
    void ShouldReturnListOfStudent() {
        List<Student> students = getStudants();

        Page<Student> studentPage = new PageImpl<>(students);
        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(studentPage);

        var result = service.findAllStudents(0,10);

        Assertions.assertEquals(2, result.size());

    }
}