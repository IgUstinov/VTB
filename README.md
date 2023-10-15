# VTB

ЗАПУСК
Запуск приложения может быть в двух вариантах: локально и на виртуальной машине.

В случае локального запуска необходимо начать со 2-го пункта и в качестве домена использовать localhost, например:
https://localhost:8081

В случае запуска на виртуальной машине в качестве домена используется его открытый ip:
https://ip.address:8081

1. Запуск Docker 
В корневом каталоге приложения необходимо создать Dockerfile:
FROM openjdk:17
ADD target/VTB-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

А также docker-compose.yml файл:
version: '2'
services:
  app:
    image: 'docker-spring-boot-postgres:latest'
    build:
      context: .
    container_name: app
    ports:
      - "8081:8081"
    depends_on:
      - db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/vtb
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
  db:
    image: 'postgres:13-alpine'
    container_name: db
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=vtb

Для запуска приложения необходимо выполнить команду из консоли, находясь в корневой папке каталога приложения: docker-compose up
Приложение будет собрано и запущено запущено



  ENDPOINT для пользователя
    Вывод отделений вокруг координаты: POST-запрос ip.address:8081/department
      В зависимости только от радиуса поиска:
        request body:  {
          "longitude": "46.0086",      -долгота пользователя 
          "latitude": "51.5406",       -широта пользователя
          "radius": "50",              -радиус поиска
          "service": [0],              -вывод всех отделов вне зависимости от предоставляемых в них услуг
          "accountWorkload": "false"   -вывод всех отделов вне зависимости от их минимальной загруженности
        } 
        response body:  [
          {
              "department": "Советская улица, 51",
              "longitude": 46.018195,        -долгота отдела
              "latitude": 51.529646,         -широта отдела
              "service": [1],                -услуги отдела
              "workload": 2.0,               -среднее арифметическое загруженности отдела
              "radius": 50,                  -радиус поиска от пользователя
              "accountWorkload": false       -искать ли по минимальной загруженности?
          }
      ]
      В зависимости от радиуса поиска и выбранной/ых услуг
        request body: {
          "longitude": "46.0086", 
          "latitude": "51.5406",
          "radius": "50",
          "service": [1],              - может быть как одна цифра, так и несколько. Например, [1, 2], [1, 3], [1, 2, 3] 
          "accountWorkload": "false"
        }
        response body:  {
          "department": "Советская улица, 51",
          "longitude": 46.018195,
          "latitude": 51.529646,
          "service": [1],                -выбранные услуги
          "workload": 2.0,               -среднее арифметическая загруженность у отдела именно среди данных услуг
          "radius": 50,
          "accountWorkload": false
        }
      В зависимости от радиуса поиска, выбранной/ых услуг и минимальной загруженности
        request body: {
            "longitude": "46.0086", 
            "latitude": "51.5406",
            "radius": "50",
            "service": [1],
            "accountWorkload": "true"
        }
        response body: [
          {
              "department": "Московская улица, 101",
              "longitude": 46.025246,
              "latitude": 51.535736,
              "service": [1,3,2],
              "workload": 1.0,
              "radius": 50,
              "accountWorkload": true
          }
        ]
       В зависимости от радиуса поиска и минимальной загруженности
         request body: {
            "longitude": "46.0086", 
            "latitude": "51.5406",
            "radius": "50",
            "service": [0],
            "accountWorkload": "true"
        }
        response body: [
          {
              "department": "ул. имени М.Ю. Лермонтова, 28А",
              "longitude": 46.05322,
              "latitude": 51.526962,
              "service": [2],
              "workload": 1.0,
              "radius": 50,
              "accountWorkload": true
          }
      ]
    ENDPOINT для создания, удаления, чтения, изменения 
    POST :8081/department/create {
        "longitude": "51.529646",
        "latitude": "46.018195",
        "department": "Советская улица, 51"
    }
    PATCH :8081/department/update/1 {
        "longitude": "46.025246",
        "latitude": "51.535736",
        "department": "Московская улица, 101"
    }
    DELETE :8081/department/delete/10
    POST :8081/service/create
    {
      "service": "Оформить карту"
    }
    PATCH :8081/service/update/2 
    {
      "service": "Оформить карту"
    }
    DELETE :8081/service/delete/4
    POST :8081/department_service/create {
        "departmentId": 3,
        "serviceId": 2,
        "workload": 2.0
    }
    DELETE :8081/department_service/delete/8
    

    
      
