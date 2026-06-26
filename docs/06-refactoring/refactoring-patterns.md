# Паттерны рефакторинга

## 1. Data Mapper
**Где:** Repository слой (BookingRepository, UserRepository, RoomRepository)

**Суть:** Отделение бизнес-логики от доступа к данным.

\\\java
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // JPA автоматически маппит Booking → таблица bookings
}
\\\

## 2. Identity Map
**Где:** Service слой (UserService, BookingService)

**Суть:** Каждый объект в рамках сессии уникален.

\\\java
// Spring Data JPA автоматически использует Identity Map
// через EntityManager и кэш первого уровня
\\\

## 3. Lazy Load
**Где:** Entity (User.bookings, Booking.meetingRoom, Booking.user)

**Суть:** Связанные объекты загружаются только при обращении.

\\\java
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
private List<Booking> bookings;

@ManyToOne(fetch = FetchType.LAZY)
private MeetingRoom meetingRoom;
\\\

## 4. DTO (Data Transfer Object)
**Где:** Контроллеры (будет на Этапе 7)

**Суть:** Разделение внутренних Entity и внешних DTO.
