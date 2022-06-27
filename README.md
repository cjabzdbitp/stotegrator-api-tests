# API testing

in Project used:

- RestAssured_
- JUnit 5_
- Hamcrest_
- Faker_ for generation test data
- OWNER_ for Credentials configuration with file.properties  based.


### example of commands:

```
gradle :test --tests "tests.ApiTests"

:test --tests "tests.ApiTests.test1" // or test2 etc.


```