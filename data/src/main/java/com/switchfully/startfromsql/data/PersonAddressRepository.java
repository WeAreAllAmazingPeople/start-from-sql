package com.switchfully.startfromsql.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonAddressRepository extends CrudRepository<PersonAddress, Long> {

    List<PersonAddress> findByZipCodeId(String zipId);

    List<PersonAddress> findByZipCodeCity(String zipCity);

    List<PersonAddress> findByZipCodeIdOrZipCodeCity(String zipCode_id, String zipCode_city);
}
