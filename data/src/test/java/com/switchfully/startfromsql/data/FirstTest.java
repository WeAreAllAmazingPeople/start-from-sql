package com.switchfully.startfromsql.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = ZipRepository.class)
@EnableAutoConfiguration
class FirstTest {
    @Test
    void contextTest() {
    }

    @Test
    void dataSourceTest(@Autowired DataSource dataSource) throws SQLException {
        assertThat(dataSource).isNotNull();
        assertThat(dataSource.getConnection()).isNotNull();
        assertThat(dataSource.getConnection().getMetaData().getDatabaseProductName())
                .isEqualTo("H2");
        // Question: Is there a better option for the @Autowired, injecting the method parameter?
    }

    @Test
    void repositoryTest(@Autowired ZipRepository repository) {
        assertThat(repository).isNotNull();
        // Question: Is there a better option for the @Autowired, injecting the method parameter?
    }

    @Test
    void entityManagerTest(@Autowired EntityManager entityManager) {
        assertThat(entityManager).isNotNull();
    }
}