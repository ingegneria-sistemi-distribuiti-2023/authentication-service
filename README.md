# AuthenticationService

## Build

The application can be built using the following command:

```
mvn clean package
```

The application can then be started with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/authentication-service-0.0.1-SNAPSHOT.jar
```