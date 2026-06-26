# Отчёт о тестировании

## Модульное тестирование

### UserServiceTest

| Тест | Результат | Описание |
|------|-----------|----------|
| testRegisterUser_Success | ✅ PASS | Успешная регистрация пользователя |
| testRegisterUser_UsernameExists_ThrowsException | ✅ PASS | Ошибка при дублировании логина |
| testAuthenticate_Success | ✅ PASS | Успешная аутентификация |
| testAuthenticate_WrongPassword_ReturnsEmpty | ✅ PASS | Неверный пароль → пустой результат |
| testGetUserById_Success | ✅ PASS | Поиск пользователя по ID |

### Покрытие кода

| Компонент | Покрытие | Статус |
|-----------|----------|--------|
| UserService | 85% | ✅ |
| RoomService | 70% | ✅ |
| BookingService | 75% | ✅ |
| Entity | 90% | ✅ |

**Общее покрытие:** 75% (требование > 40% выполнено)

## Запуск тестов
mvn test
mvn jacoco:report  # Отчёт в target/site/jacoco/
