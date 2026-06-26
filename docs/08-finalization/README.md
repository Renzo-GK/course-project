# Руководство администратора

## 1. Требования к окружению

| Компонент | Версия |
|-----------|--------|
| Java | 17+ |
| Maven | 3.8+ |
| PostgreSQL | 14+ |
| Git | 2.0+ |

## 2. Установка и запуск

### 2.1. Клонирование репозитория
\\\ash
git clone https://github.com/Renzo-GK/course-project.git
cd course-project
\\\

### 2.2. Настройка базы данных
1. Установите PostgreSQL
2. Создайте базу данных: ookingdb
3. Настройте подключение в pplication.properties

### 2.3. Сборка и запуск
\\\ash
mvn clean install
mvn spring-boot:run
\\\

### 2.4. Доступ к приложению
- URL: http://localhost:8080
- Администратор: admin / admin123
- Пользователь: user / user123

## 3. Управление пользователями
1. Войдите как администратор
2. Перейдите в админ-панель
3. Добавьте, отредактируйте или удалите пользователя

## 4. Управление комнатами
1. Войдите как администратор
2. Перейдите в админ-панель
3. Добавьте, отредактируйте или удалите комнату

## 5. Резервное копирование
\\\ash
pg_dump -U postgres bookingdb > backup.sql
\\\

## 6. Восстановление
\\\ash
psql -U postgres bookingdb < backup.sql
\\\
"@ | Out-File -FilePath "docs\08-finalization\admin-guide.md" -Encoding UTF8

Write-Host "Руководство администратора создано!" -ForegroundColor Green
@"
# Этап 8: Завершение

## Выполненные артефакты

| № | Артефакт | Статус | Файл |
|---|----------|--------|------|
| 1 | WBS | ✅ | [wbs.md](wbs.md) |
| 2 | Диаграмма Ганта | ✅ | [gantt-chart.md](gantt-chart.md) |
| 3 | Оценка COCOMO | ✅ | [cocomo.md](cocomo.md) |
| 4 | Техническое задание | ✅ | [technical-specification.md](technical-specification.md) |
| 5 | Руководство пользователя | ✅ | [user-guide.md](user-guide.md) |
| 6 | Руководство администратора | ✅ | [admin-guide.md](admin-guide.md) |
| 7 | Пояснительная записка | ✅ | [explanatory-note.md](explanatory-note.md) |
| 8 | Презентация | ✅ | [presentation.md](presentation.md) |

## Итог проекта

| Параметр | Значение |
|----------|----------|
| Название | Веб-сервис для бронирования переговорных комнат |
| Траектория | Б. Веб-ориентированная |
| Технологии | Java 17, Spring Boot, Thymeleaf, PostgreSQL |
| Объем кода | ~2000 строк |
| Покрытие тестами | > 40% |
| Длительность | 18 недель |
