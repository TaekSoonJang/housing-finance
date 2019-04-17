### Language & Frameworks
* Java 8
* Spring Boot
* Spring Data Jpa (Hibernate)
* H2 (Database)

### How to build and run

* Run tests
    ```
    $ ./mvnw clean test
    ```

* Run
    ```
    $ ./mvnw clean spring-boot:run
    ```

### APIs
Please refer to [Swagger UI](http://localhost:8080/swagger-ui.html) after run.

### Remarks
* Init data
  * Institutes are populated after the application is up and running.
  * Please refer to the init data [here](src/main/resources/import.sql).
* Reading CSV
  * Column values for institutes should be matched by its unique name with entries in the database.
    * '(억원)' will be automatically removed before looking up institutes in the database.
  * Columns that contain an unprocessable input will be ignored. 
  * If a column has multiple institutes split by '/', (ex> 'a/b(억원)'), 'a' and 'b' will be registered separately.