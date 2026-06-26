# Спецификация методов (ключевые методы)

## 1. Слой Control (Контроллеры)

### BookingController
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| createBooking | BookingDTO bookingDTO | Booking | Создание бронирования |
| cancelBooking | Long bookingId | oid | Отмена бронирования |
| getBookingById | Long id | Booking | Получение по ID |
| getBookingsByUser | Long userId | List<Booking> | Все бронирования пользователя |

### UserController
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| uthenticate | String username, String password | User | Аутентификация |
| egister | User user | User | Регистрация |
| getUserById | Long id | User | Получение по ID |

### RoomController
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| createRoom | MeetingRoom room | MeetingRoom | Создание комнаты |
| getAllRooms | - | List<MeetingRoom> | Список всех комнат |
| deleteRoom | Long id | oid | Удаление комнаты |

---

## 2. Слой Mediator (Сервисы)

### BookingService
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| createBooking | Booking booking | Booking | Создание бронирования с проверкой |
| cancelBooking | Long bookingId | oid | Отмена с проверкой прав |
| getBookingById | Long id | Booking | Получение с проверкой существования |
| isRoomAvailable | Long roomId, LocalDateTime start, LocalDateTime end | oolean | Проверка доступности |

### UserService
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| uthenticate | String username, String password | User | Проверка пароля (BCrypt) |
| egister | User user | User | Создание пользователя с проверкой |
| getUserById | Long id | User | Получение с проверкой |
| getAllUsers | - | List<User> | Список всех пользователей |

### RoomService
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| createRoom | MeetingRoom room | MeetingRoom | Создание комнаты |
| getAllRooms | - | List<MeetingRoom> | Список всех комнат |
| deleteRoom | Long id | oid | Удаление комнаты |
| getAvailableRooms | LocalDateTime start, LocalDateTime end | List<MeetingRoom> | Поиск свободных комнат |

---

## 3. Слой Foundation (Репозитории)

### BookingRepository
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| indById | Long id | Booking | Поиск по ID |
| save | Booking booking | Booking | Сохранение (INSERT/UPDATE) |
| delete | Long id | oid | Удаление |
| indAll | - | List<Booking> | Все записи |
| indByUser | Long userId | List<Booking> | Поиск по пользователю |
| indByRoomAndTime | Long roomId, LocalDateTime start, LocalDateTime end | List<Booking> | Проверка пересечений |
| existsByRoomAndTime | Long roomId, LocalDateTime start, LocalDateTime end | oolean | Проверка существования |

### UserRepository
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| indById | Long id | User | Поиск по ID |
| save | User user | User | Сохранение |
| delete | Long id | oid | Удаление |
| indAll | - | List<User> | Все пользователи |
| indByUsername | String username | User | Поиск по логину |
| indByEmail | String email | User | Поиск по email |

### RoomRepository
| Метод | Параметры | Возврат | Описание |
|-------|-----------|---------|----------|
| indById | Long id | MeetingRoom | Поиск по ID |
| save | MeetingRoom room | MeetingRoom | Сохранение |
| delete | Long id | oid | Удаление |
| indAll | - | List<MeetingRoom> | Все комнаты |
| indAvailable | LocalDateTime start, LocalDateTime end | List<MeetingRoom> | Свободные комнаты |
