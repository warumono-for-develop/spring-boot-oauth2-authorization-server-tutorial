# Spring Boot with Security, OAuth2 and JWT Tutorial
**An tutorial application using Spring Boot as OAuth2 Authorization back-end.**

More details about the codes, please read the online **[Spring Boot](https://projects.spring.io/spring-boot).**

Requirements
------
Running in
+ [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.8 or newer
+ [Spring Boot](https://github.com/spring-projects/spring-boot) 1.5.2.RELEASE or newer
+ [Gradle](https://github.com/gradle/gradle) 3.4.1 or newer

Optional
------
+ YAML

Dependencies
------
+ [org.springframework.boot:spring-boot-starter-security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
+ [org.springframework.boot:spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)

+ [org.springframework.security.oauth:spring-security-oauth2:2.1.0.RELEASE](https://mvnrepository.com/artifact/org.springframework.security.oauth/spring-security-oauth2)
+ [org.springframework.security:spring-security-jwt:1.0.7.RELEASE](https://mvnrepository.com/artifact/org.springframework.security/spring-security-jwt)
+ [org.springframework.boot:spring-boot-devtools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
+ [com.h2database:h2](https://mvnrepository.com/artifact/com.h2database/h2)

Latest Update
------
+ 1.0 (Apr 26, 2017)

How to Run
------
### OAuth2 Authorization Server
+ Clone [this Git repository](https://github.com/warumono-for-develop/spring-boot-oauth2-authorization-server-tutorial)
+ Build the project gradle build
+ Run the application **./gradlew bootRun**
```command
$ ./gradle bootRun
```

### With OAuth2 Resource Server
+ Move [SpringBootOAuth2ResourceServerTutorial](https://github.com/warumono-for-develop/spring-boot-oauth2-resource-server-tutorial)

Test accounts
------
+ Reference file [SpringBootOAuth2AuthorizationServerTutorialApplication.java](https://github.com/warumono-for-develop/spring-boot-oauth2-authorization-server-tutorial/blob/master/src/main/java/com/warumono/SpringBootOAuth2AuthorizationServerTutorialApplication.java)

#### User
```sql
username: admin@me.com
password: admin
-- Has authorities: USER, ADMIN

or

username: user@me.com
password: user
-- Has authorities: USER
```

#### Client
```sql
client id    : oneclient
client secret: onesecret
-- Has scopes: read, write
-- Has grant types: authorization_code, refresh_token, implicit, password, client_credentials

or

client id    : twoclient
client secret: twosecret
-- Has scopes: read
-- Has grant types: authorization_code, client_credentials
```

#### or
You can use it that create a new account.

+ Open file [SpringBootOAuth2AuthorizationServerTutorialApplication.java](https://github.com/warumono-for-develop/spring-boot-oauth2-authorization-server-tutorial/blob/master/src/main/java/com/warumono/SpringBootOAuth2AuthorizationServerTutorialApplication.java)
+ And write to your custom users and clients.

```java
@Bean
public CommandLineRunner commandLineRunner(UserRepository userRepository, ClientRepository clientRepository)
{
  String yourUsername = "your@email.address";
  String yourPassword = new BCryptPasswordEncoder().encode("yourpassword");
 
  String yourClientId = "your_client_id";
  String yourClientSecret = new BCryptPasswordEncoder().encode("your_client_secret");

  return args ->
  {
    userRepository.save(new AppUser(3L, yourUsername, yourPassword, "USER,ADMIN"));
    clientRepository.save(new AppClient(yourClientId, yourClientSecret, "read,write", "authorization_code,refresh_token,implicit,password,client_credentials"));
  };
}
```

Get a access token
------
#### Request command

##### Template command

- clientid		: id in AppClient entity.
- clientsecret	: secret in AppClient entity.
- username		: username in AppUser entity.
- password		: password in AppUser entity.

```cli
$ curl -XPOST "<clientid>:<clientsecret>@localhost:9090/oauth/token" -d "grant_type=password&username=<username>&password=<password>"
```

##### via

```cli
$ curl -XPOST "oneclient:onesecret@localhost:9090/oauth/token" -d "grant_type=password&username=user@me.com&password=user"
```

#### Response

##### Response template

- access_token	: token in order to access resource in ResourceServer
- refresh_token	: token in order to get a new access_token in AuthorizationServer

```json
{"access_token":"<access_token>","token_type":"bearer","refresh_token":"<refresh_token>","expires_in":43199,"scope":"read write","jti":"ed68363e-2ced-4466-8c07-894a04cd3250"}
```

#### via

```json
{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0OTMxNTczNzksInVzZXJfbmFtZSI6InVzZXJAbWUuY29tIiwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiJlZDY4MzYzZS0yY2VkLTQ0NjYtOGMwNy04OTRhMDRjZDMyNTAiLCJjbGllbnRfaWQiOiJvbmVjbGllbnQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.ZFxOMfjVy-z4QkLy20LWvmsClgqpCtIuhlzM9pyw6YUDGgWrIn6QfKFi5OMOmrKFuJvk_IA57aRa27PMAQuHKWKtHryWj71BUqQbWIVt0Cc04ZfBuey5Xy6qIHHvEy-LhaAt4KiX4JnySoLspiuBMgRs0-OCFvAhrO5vEG-Q2svlkivMMEMl3qDgosh4S4IBmmJ-WKckJTOQQ9Zwr3yrSJoNXPDPI_1Nik4jzP2I0rs8jYGuFVG-nst9xd8PRA9JtblAcCjjSwPhV6U72Ue5MdP_vsGXTdSmdlidNeclWqkCYiW3FJQ23LyIo9wT8-ouf9xOXuHn67Tj6C87tV46Ng","token_type":"bearer","refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyQG1lLmNvbSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJlZDY4MzYzZS0yY2VkLTQ0NjYtOGMwNy04OTRhMDRjZDMyNTAiLCJleHAiOjE0OTU3MDYxNzksImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiYWIyZTVkNTYtZjQxYi00Zjc2LThjMDktN2Y2NTE0NTc3ODRkIiwiY2xpZW50X2lkIjoib25lY2xpZW50In0.AOtrqPxVmGe0zSkJcDP3-yrYydHLjEkLaJoR47VtfpH2Qhjhf9VhB5r9oF4pAYh9KnSvep5C1BoAIoQslE53DZELLzM4nkxEKY4arGtZkxAjjQWPvdJT5UC8xMVCD8RSmhnB5t0wap5TLr8G78_7uQRLeAxmzwdTtJVBQRUNz_LLU_iokkWZaTbwOlnDLhbAQcR5ZFArwvsxBNlw2YNYOhWhk1jibzBMZvkfv4IP5L_bZyVEKEeCoucJLad_mZvWI9b-6PNTZlzZ3OLxRdRcB6IsKIKWSwP0m9SuQ2tx2MWLeL3b8wCxUAnzjA7ye1LfColsnW2EqY8m3_lMIEoNuw","expires_in":43199,"scope":"read write","jti":"ed68363e-2ced-4466-8c07-894a04cd3250"}
```

API
------
#### Configuration
By default Spring Boot applications run on port **9090**.
But may vary depending on what ports are in use on your machine (check the terminal after entering the ./gradlew bootRun command).
If you require to change which port the application runs on by default, add the following to:

#### application.yml
```yml
server:
    port: 9090 # --> change other port via. 9999
```

Author
------
**warumono** - <warumono.for.develop@gmail.com>
