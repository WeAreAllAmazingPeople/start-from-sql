package com.switchfully.startfromsql.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.datasource.initialization-mode=always"
})
@ContextConfiguration(classes = PersonAddressRepository.class)
@EnableAutoConfiguration
class PersonAddressRepositoryTest {
    @Test
    @Sql("schema-v2.sql")
    void contextAndSQLScript() {
        // Is the configuration OK?
        // Is the SQL script OK?
    }

    @Test
    @Sql("schema-v2.sql")
    void I_can_create_a_person_address(@Autowired PersonAddressRepository repository, @Autowired ZipRepository zipRepository) {
        // Requires the ID to be properly generated
        // https://www.objectdb.com/java/jpa/entity/generated
        // Given
        Zip zip = new Zip("zzzz", "Z-City");
        Zip managedZip = zipRepository.save(zip);
        PersonAddress expected = new PersonAddress(managedZip);
        // When
        PersonAddress actual = repository.save(expected);
        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = {"schema-v2.sql", "data-v2.sql"})
    void I_can_query_a_person_address_by_id(@Autowired PersonAddressRepository repository) {
        // Given
        long id = 0;
        // When
        Optional<PersonAddress> actual = repository.findById(id);
        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    @Sql(scripts = {"schema-v2.sql", "data-v2.sql"})
    void I_can_query_a_person_address_by_zip(@Autowired PersonAddressRepository repository) {
        // Given
        String schaarbeekPC = "1030";
        String schaarbeek = "Schaarbeek";
        // When
        List<PersonAddress> actualA = repository.findByZipCodeId(schaarbeekPC);
        List<PersonAddress> actualB = repository.findByZipCodeCity(schaarbeek);
        List<PersonAddress> actualC = repository.findByZipCodeIdOrZipCodeCity(schaarbeekPC, null);
        List<PersonAddress> actualD = repository.findByZipCodeIdOrZipCodeCity(null, schaarbeek);
        // Then
        assertThat(actualA).hasSize(1);
        assertThat(actualB).hasSize(1);
        assertThat(actualC).hasSize(1);
        assertThat(actualD).hasSize(1);
    }
}