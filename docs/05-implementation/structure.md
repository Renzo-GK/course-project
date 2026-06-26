# Структура проекта

## Maven-проект

src/
├── main/
│   ├── java/
│   │   └── com/company/booking/
│   │       ├── entity/          # Сущности (User, Room, Booking)
│   │       ├── repository/      # JPA-репозитории
│   │       ├── service/         # Бизнес-логика
│   │       ├── config/          # Конфигурация (Security)
│   │       └── BookingApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/company/booking/service/
            └── UserServiceTest.java

## Соответствие PCMEF

| Слой | Папка | Классы |
|------|-------|--------|
| Entity | entity/ | User, MeetingRoom, Booking |
| Foundation | repository/ | UserRepository, RoomRepository, BookingRepository |
| Mediator | service/ | UserService, RoomService, BookingService |
| Control | controller/ | (будет на Этапе 7) |
| Presentation | templates/ | (будет на Этапе 7) |
