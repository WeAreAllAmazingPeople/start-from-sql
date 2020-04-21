package com.switchfully.startfromsql.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Zip {
    @Id
    @Column(name = "POSTAL_CODE")
    private String id;

    @Column(nullable = false)
    private String city;

    public Zip() {
    }

    public Zip(String id, String city) {
        this.id = id;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zip zip = (Zip) o;
        return Objects.equals(id, zip.id) &&
                Objects.equals(city, zip.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city);
    }
}
