package com.senai.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person{


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressid", referencedColumnName = "id")
    private ResidentialAddress address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactid", referencedColumnName = "id")
    private Contact contact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return person.getId() == this.getId() &&  this.getCpf().equals(person.getCpf());
    }
}
