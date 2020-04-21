package com.switchfully.startfromsql.data;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.datasource.initialization-mode=always"
})
@ContextConfiguration(classes = ZipRepository.class)
@EnableAutoConfiguration
class ZipRepositoryTest {

    @Test
    @Sql("schema-v1.sql")
    void contextTest() {
    }

    @Test
    @Sql("schema-v1.sql")
    void I_can_create_a_zip_code(@Autowired ZipRepository repository) {
        // Given
        Zip expected = new Zip("abc", "def");
        // When
        Zip actual = repository.save(expected);
        Optional<Zip> actual2 = repository.findById("abc");
        // Then
        assertThat(actual).isEqualTo(expected);
        assertThat(actual2.get()).isEqualTo(expected);
    }

    @Test
    @Sql("schema-v1.sql")
    void I_cannot_create_a_zip_code_without_a_city(@Autowired ZipRepository repository) {
        // Given
        Zip expected = new Zip("abc", null);
        // When
        Throwable exception = catchThrowable(() -> repository.save(expected));
        // Then
        assertThat(exception)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageStartingWith("not-null property references a null or transient value");
    }

    @Test
    @Sql(scripts = {"schema-v1.sql", "data-v1.sql"})
    void I_can_query_a_zip_code(@Autowired ZipRepository repository) {
        // Given
        String zipCode = "3000";
        // When
        Optional<Zip> actual = repository.findById(zipCode);
        // Then
        assertThat(actual.isPresent()).isTrue();
    }

    @Test
    @Disabled
    @Sql("schema-v1.sql")
    void I_cannot_create_a_zip_code_longer_then_4(@Autowired ZipRepository repository) {
        // Disable!
        // Expected -> Exception (VALUE_TOO_LONG_2 = 22001)
        // https://www.h2database.com/javadoc/org/h2/api/ErrorCode.html

        // Given
        String impossiblePostalCode = "abcdefmqsklfjqsmldfkjqsmlfjmqsdklfj";
        Zip expected = new Zip(impossiblePostalCode, "city");
        // When
        repository.save(expected);
        Optional<Zip> actual = repository.findById(impossiblePostalCode);
        // Then
        assertThat(actual.get()).isEqualTo(expected);
    }
}