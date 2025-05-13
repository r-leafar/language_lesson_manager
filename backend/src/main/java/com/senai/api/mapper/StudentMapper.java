package com.senai.api.mapper;

import com.senai.api.domain.Contact;
import com.senai.api.domain.ResidentialAddress;
import com.senai.api.domain.Student;
import com.senai.api.domain.dto.StudentRequestDto;
import com.senai.api.domain.dto.StudentResponseDto;

public class StudentMapper {

    public static Student toEntity(StudentResponseDto dto) {
        var student = new Student();

        student.setId(dto.id());
        student.setName(dto.name());
        student.setSurname(dto.surname());
        student.setBirth(dto.birth());

        var address = new ResidentialAddress();

        address.setId(dto.address().getId());
        address.setCep(dto.address().getCep());
        address.setCity(dto.address().getCity());
        address.setNumber(dto.address().getNumber());
        address.setState(dto.address().getState());
        address.setStreet(dto.address().getStreet());
        address.setNeighborhood(dto.address().getNeighborhood());

        student.setAddress(address);

        var contact = new Contact();

        contact.setId(dto.contact().getId());
        contact.setContact(dto.contact().getContact());
        contact.setEmail(dto.contact().getEmail());
        contact.setWhatsapp(dto.contact().getWhatsapp());
        student.setContact(contact);
        return student;
    }
    public static Student toEntity(StudentRequestDto dto) {
        var student = new Student();
        student.setName(dto.name());
        student.setSurname(dto.surname());
        student.setCpf(dto.cpf());
        student.setBirth(dto.birth());

        var address = new ResidentialAddress();

        address.setCep(dto.address().cep());
        address.setCity(dto.address().city());
        address.setNumber(dto.address().number());
        address.setState(dto.address().state());
        address.setStreet(dto.address().street());
        address.setNeighborhood(dto.address().neighborhood());

        student.setAddress(address);

        var contact = new Contact();
        contact.setContact(dto.contact().contact());
        contact.setEmail(dto.contact().email());
        contact.setWhatsapp(dto.contact().whatsapp());

        student.setContact(contact);
        return student;
    }
}
