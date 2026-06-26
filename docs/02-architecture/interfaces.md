# Спецификация интерфейсов (PCMEF)

## 1. Интерфейсы слоя Mediator (IService)

### IUserService
\\\java
public interface IUserService {
    User getUserById(Long id);
    User createUser(User user);
    User authenticate(String email, String password);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
\\\

### IRoomService
\\\java
public interface IRoomService {
    MeetingRoom getRoomById(Long id);
    MeetingRoom createRoom(MeetingRoom room);
    List<MeetingRoom> getAllRooms();
    MeetingRoom updateRoom(Long id, MeetingRoom room);
    void deleteRoom(Long id);
    List<MeetingRoom> findAvailableRooms(LocalDateTime start, LocalDateTime end);
}
\\\

### IBookingService
\\\java
public interface IBookingService {
    Booking getBookingById(Long id);
    Booking createBooking(Booking booking);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUser(Long userId);
    Booking updateBooking(Long id, Booking booking);
    void cancelBooking(Long id);
    boolean isRoomAvailable(Long roomId, LocalDateTime start, LocalDateTime end);
}
\\\

---

## 2. Интерфейсы слоя Foundation (IRepository)

### IUserRepository
\\\java
public interface IUserRepository {
    User findById(Long id);
    User save(User user);
    void delete(Long id);
    User findByEmail(String email);
    List<User> findAll();
    boolean existsByEmail(String email);
}
\\\

### IRoomRepository
\\\java
public interface IRoomRepository {
    MeetingRoom findById(Long id);
    MeetingRoom save(MeetingRoom room);
    void delete(Long id);
    List<MeetingRoom> findAll();
    List<MeetingRoom> findAvailable(LocalDateTime start, LocalDateTime end);
}
\\\

### IBookingRepository
\\\java
public interface IBookingRepository {
    Booking findById(Long id);
    Booking save(Booking booking);
    void delete(Long id);
    List<Booking> findAll();
    List<Booking> findByUser(Long userId);
    List<Booking> findByRoom(Long roomId);
    List<Booking> findByDateRange(LocalDateTime start, LocalDateTime end);
    boolean existsByRoomAndTime(Long roomId, LocalDateTime start, LocalDateTime end);
}
\\\

---

## 3. Правила зависимостей

| От | К | Тип |
|----|---|-----|
| Presentation | Control | Зависимость (вызов методов) |
| Control | Mediator | Зависимость (вызов через интерфейс) |
| Mediator | Entity | Зависимость (использование объектов) |
| Mediator | Foundation | Зависимость (вызов через интерфейс) |

**Важно:** Зависимости направлены строго сверху вниз. Нижние слои НЕ знают о верхних.
