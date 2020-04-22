package com.switchfully.startfromsql.data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person_address")
@SequenceGenerator(name = "person_address_seq", initialValue = 1, allocationSize = 100)
public class PersonAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_address_seq")
    @Column(name = "person_address_id")
    private Long id;

    @Column(name = "person_street")
    private String street;

    @Column(name = "person_nr")
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "POSTAL_CODE", nullable = false)
    private Zip zipCode;

    public PersonAddress() {
    }

    public PersonAddress(Zip zip) {
        this.zipCode = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonAddress address = (PersonAddress) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(street, address.street) &&
                Objects.equals(number, address.number) &&
                Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, number, zipCode);
    }
}
