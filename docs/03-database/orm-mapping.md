# Описание стратегии ORM (Object-Relational Mapping)

## 1. Стратегия маппинга

Используется **JPA (Java Persistence API)** с реализацией **Hibernate**.

### Основные аннотации:
- @Entity — класс является сущностью
- @Table — имя таблицы
- @Id — первичный ключ
- @GeneratedValue — автоинкремент
- @Column — маппинг на колонку
- @ManyToOne / @OneToMany — связи
- @Enumerated — маппинг enum
- @CreationTimestamp — автоматическая установка времени

---

## 2. Маппинг сущностей

### User → users
| Java-поле | Колонка | Тип | Особенности |
|-----------|---------|-----|-------------|
| id | id | Long | @Id, @GeneratedValue |
| username | username | String | @Column(unique=true) |
| password | password | String | @Column(nullable=false) |
| fullName | full_name | String | @Column(nullable=false) |
| email | email | String | @Column(unique=true) |
| role | role | Role | @Enumerated(EnumType.STRING) |
| enabled | enabled | Boolean | default = true |
| createdAt | created_at | LocalDateTime | @CreationTimestamp |

### MeetingRoom → meeting_rooms
| Java-поле | Колонка | Тип | Особенности |
|-----------|---------|-----|-------------|
| id | id | Long | @Id, @GeneratedValue |
| name | name | String | @Column(nullable=false) |
| capacity | capacity | Integer | @Column(nullable=false) |
| equipment | equipment | String | @Column(columnDefinition = "TEXT") |
| location | location | String | @Column(nullable=false) |
| isAvailable | is_available | Boolean | default = true |
| createdAt | created_at | LocalDateTime | @CreationTimestamp |

### Booking → bookings
| Java-поле | Колонка | Тип | Особенности |
|-----------|---------|-----|-------------|
| id | id | Long | @Id, @GeneratedValue |
| meetingRoom | room_id | MeetingRoom | @ManyToOne(fetch=FetchType.LAZY) |
| user | user_id | User | @ManyToOne(fetch=FetchType.LAZY) |
| startDateTime | start_date_time | LocalDateTime | @Column(nullable=false) |
| endDateTime | end_date_time | LocalDateTime | @Column(nullable=false) |
| status | status | BookingStatus | @Enumerated(EnumType.STRING) |
| subject | subject | String | @Column(length=200) |
| description | description | String | @Column(columnDefinition = "TEXT") |
| createdAt | created_at | LocalDateTime | @CreationTimestamp |

---

## 3. Пример кода сущности

### User.java
\\\java
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;
    
    @Column(nullable = false)
    private boolean enabled = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
}
\\\

### Booking.java
\\\java
@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom meetingRoom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;
    
    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.SCHEDULED;
    
    @Column(length = 200)
    private String subject;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
\\\

---

## 4. Ключевые особенности

| Особенность | Описание |
|-------------|----------|
| **Lazy Loading** | Связи ManyToOne загружаются лениво для оптимизации |
| **Каскадные операции** | При удалении пользователя удаляются его бронирования |
| **Валидация** | Проверка на уровне БД (CHECK constraints) |
| **Индексы** | Созданы для часто запрашиваемых полей |
| **Уникальность** | Запрет двойного бронирования через уникальный индекс |
