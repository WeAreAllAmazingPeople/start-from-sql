# start-from-sql

* Multi-module Spring Boot project
* Implemented from the DB schema
* Using a feature branch + PR workflow

## Git workflow --> feature branch + Pull Request (PR)
> git branch next-feature
> git checkout next-feature
> git add ...
> git commit -m "add next feature"
> git push
> git checkout master
> (on github -> merge PR)
> git pull

## First Test! (Without a @SpringBootApplication)
```java
@DataJpaTest
class StartTest {
    @Test
    void contextTest() { }
}
```
> java.lang.IllegalStateException: Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test

```java
@DataJpaTest
@ContextConfiguration(classes = TestRepository.class)
@EnableAutoConfiguration
class FirstTest {
    @Test
    void contextTest() { }
}
```