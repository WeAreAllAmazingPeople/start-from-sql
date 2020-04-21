package com.switchfully.startfromsql.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;

@DataJpaTest
@ContextConfiguration(classes = TestRepository.class)
@EnableAutoConfiguration
class FirstTest {
    @Test
    void contextTest() {
    }

    @Test
    void dataSourceTest(@Autowired DataSource dataSource) throws SQLException {
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(dataSource.getConnection()).isNotNull();
        Assertions.assertThat(dataSource.getConnection().getMetaData().getDatabaseProductName())
                .isEqualTo("H2");
        // Question: Is there a better option for the @Autowired, injecting the method parameter?
    }

    @Test
    void repositoryTest(@Autowired TestRepository repository) {
        Assertions.assertThat(repository).isNotNull();
        // Question: Is there a better option for the @Autowired, injecting the method parameter?
    }

}