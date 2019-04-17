### Language & Frameworks
* Java 8
* Spring Boot
* Spring Data Jpa (Hibernate)
* H2

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
* Endpoint: /api/v1
* POST /user/signup
  * Register a new user and return a token
  * Request parameters
    * userId: string
    * password: string
    
* POST /user/signin
  * Login a user and return a token
  * Request parameters
      * userId: string
      * password: string

* PUT /auth/token?op=refresh
  * Refresh a token for an authenticated user
  * Request header
    * Authorization: Bearer \<token\>
    
* GET /institute
  * Get all institutes
  * Request header
    * Authorization: Bearer \<token\>

* POST /supportamount?csv=true
  * Insert support amount data from csv
  * Request parameters
    * file: csv file
  * Request header
    * Authorization: Bearer \<token\>

* GET /summary
  * Get summary of support amount per institute
  * Request header
    * Authorization: Bearer \<token\>
    
* GET /top-year-inst
  * 