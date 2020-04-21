package com.switchfully.startfromsql.data;

import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestData, Long> {
}
