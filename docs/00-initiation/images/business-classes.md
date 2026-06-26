# Модель бизнес-классов

## Диаграмма
![Модель бизнес-классов](images/business-classes.png)

## Классы

### 1. Пользователь (User)
| Атрибут | Тип | Описание |
|---------|-----|----------|
| id | Long | Уникальный ID |
| username | String | Логин |
| password | String | Пароль (BCrypt) |
| fullName | String | Полное имя |
| email | String | Email |
| role | String | USER/ADMIN |
| enabled | boolean | Активен |

### 2. Переговорная комната (MeetingRoom)
| Атрибут | Тип | Описание |
|---------|-----|----------|
| id | Long | Уникальный ID |
| name | String | Название |
| capacity | Integer | Вместимость |
| equipment | String | Оборудование |
| location | String | Местоположение |

### 3. Бронирование (Booking)
| Атрибут | Тип | Описание |
|---------|-----|----------|
| id | Long | Уникальный ID |
| startDateTime | LocalDateTime | Начало |
| endDateTime | LocalDateTime | Конец |
| status | String | Статус |
| subject | String | Тема встречи |
| description | String | Описание |

## Связи
- User (1) → Booking (*)
- MeetingRoom (1) → Booking (*)
