# api-people
People

Database (EMBEDDED H2):
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:mydb
USER:sa
PASS:

Steps to build and execute:
- git clone https://github.com/estebanbri/api-people
- mvn clean install spring-boot:run

# PEOPLE RESOURCE

### Create a new person in people resource
```
curl -X POST "http://localhost:8080/people" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"dni\": 123, \"dniType\": \"DNI\", \"country\": \"ARGENTINA\", \"gender\": \"MALE\", \"firstName\": \"Ignacio\", \"lastName\": \"Briceno\", \"birthDate\": \"25/02/1989\", \"contactData\": { \"phoneNumber\": \"123411\", \"email\": \"test@test\" }}"
```

### Retrieve an existing resource
```
curl -X GET "http://localhost:8080/people?country=ARGENTINA&dni=123&dniType=DNI&gender=MALE" -H "accept: */*"
```

### Update an existing person in people resource
```
curl -X PUT "http://localhost:8080/people?country=ARGENTINA&dni=123&dniType=DNI&gender=MALE" -H "accept: */*" -H "Content-Type: application/json" -d "{\"firstName\": \"Esteban\",\"lastName\": \"Briceo\",\"birthDate\": \"25/02/1989\",\"contactData\": {\"phoneNumber\":\"1111\",\"email\":\"esteban_89@b\"}}"
```

### Delete an existing person in people resource
```
curl -X DELETE "http://localhost:8080/people?country=ARGENTINA&dni=123&dniType=DNI&gender=MALE" -H "accept: */*"
```

# RELATIONSHIP RESOURCE
### Create a parent relationship
```
curl -X POST "http://localhost:8080/people/1/parent/2" -H "accept: */*"
```

### Query relationship
```
curl -X GET "http://localhost:8080/relationship/1/2" -H "accept: */*"
```

# STATISTICS
### See statistics
```
http://localhost:8080/actuator/statistics
```
{
  "femaleQuantityTotal": 0,
  "maleQuantityTotal": 0,
  "argentinianPercent": 0
}
