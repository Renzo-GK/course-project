# Этап 5: Реализация ядра

## Выполненные артефакты

| № | Артефакт | Статус |
|---|----------|--------|
| 1 | Структура проекта (Maven) | ✅ Готов |
| 2 | Сущности (Entity) | ✅ Готов |
| 3 | Репозитории (Repository) | ✅ Готов |
| 4 | Сервисы (Service) | ✅ Готов |
| 5 | Конфигурация (Security) | ✅ Готов |
| 6 | Модульные тесты | ✅ Готов |

## Структура проекта

\\\
src/
├── main/
│   ├── java/
│   │   └── com/company/booking/
│   │       ├── entity/
│   │       │   ├── User.java
│   │       │   ├── MeetingRoom.java
│   │       │   ├── Booking.java
│   │       │   ├── Role.java
│   │       │   └── BookingStatus.java
│   │       ├── repository/
│   │       │   ├── UserRepository.java
│   │       │   ├── RoomRepository.java
│   │       │   └── BookingRepository.java
│   │       ├── service/
│   │       │   ├── UserService.java
│   │       │   ├── RoomService.java
│   │       │   └── BookingService.java
│   │       ├── config/
│   │       │   ├── SecurityConfig.java
│   │       │   └── PasswordEncoderConfig.java
│   │       └── BookingApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/company/booking/service/
            └── UserServiceTest.java
\\\

## Технологии
- Java 17
- Spring Boot 2.7.0
- Spring Data JPA (Hibernate)
- Spring Security
- H2 Database (development)
- JUnit 5, Mockito
- Maven

## Запуск проекта
\\\ash
mvn clean install
mvn spring-boot:run
\\\
"@ | Out-File -FilePath "docs\05-implementation\README.md" -Encoding UTF8

Write-Host "Документация Этапа 5 создана!" -ForegroundColor Green
@"
# Этап 5: Реализация ядра

## Выполненные артефакты

| № | Артефакт | Статус |
|---|----------|--------|
| 1 | Структура проекта (Maven) | ✅ Готов |
| 2 | Сущности (Entity) | ✅ Готов |
| 3 | Репозитории (Repository) | ✅ Готов |
| 4 | Сервисы (Service) | ✅ Готов |
| 5 | Конфигурация (Security) | ✅ Готов |
| 6 | Модульные тесты | ✅ Готов |

## Структура проекта

src/
├── main/
│   ├── java/
│   │   └── com/company/booking/
│   │       ├── entity/
│   │       │   ├── User.java
│   │       │   ├── MeetingRoom.java
│   │       │   ├── Booking.java
│   │       │   ├── Role.java
│   │       │   └── BookingStatus.java
│   │       ├── repository/
│   │       │   ├── UserRepository.java
│   │       │   ├── RoomRepository.java
│   │       │   └── BookingRepository.java
│   │       ├── service/
│   │       │   ├── UserService.java
│   │       │   ├── RoomService.java
│   │       │   └── BookingService.java
│   │       ├── config/
│   │       │   ├── SecurityConfig.java
│   │       │   └── PasswordEncoderConfig.java
│   │       └── BookingApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/company/booking/service/
            └── UserServiceTest.java

## Технологии
- Java 17
- Spring Boot 2.7.0
- Spring Data JPA (Hibernate)
- Spring Security
- H2 Database (development)
- JUnit 5, Mockito
- Maven

## Запуск проекта
mvn clean install
mvn spring-boot:run
