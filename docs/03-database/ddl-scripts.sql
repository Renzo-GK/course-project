-- ============================================================
-- DDL-скрипты для базы данных бронирования переговорных комнат
-- ============================================================

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы переговорных комнат
CREATE TABLE IF NOT EXISTS meeting_rooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    equipment TEXT,
    location VARCHAR(100) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы бронирований
CREATE TABLE IF NOT EXISTS bookings (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    start_date_time TIMESTAMP NOT NULL,
    end_date_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    subject VARCHAR(200),
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES meeting_rooms(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_booking_time CHECK (start_date_time < end_date_time),
    CONSTRAINT chk_booking_status CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED'))
);

-- Создание индексов для оптимизации
CREATE INDEX IF NOT EXISTS idx_bookings_room_time ON bookings(room_id, start_date_time);
CREATE INDEX IF NOT EXISTS idx_bookings_user ON bookings(user_id);
CREATE INDEX IF NOT EXISTS idx_bookings_status ON bookings(status);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- Создание уникального ограничения для предотвращения двойного бронирования
-- (одна комната не может быть забронирована на одно и то же время)
CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_booking 
ON bookings(room_id, start_date_time) 
WHERE status != 'CANCELLED';

-- ============================================================
-- Вставка тестовых данных
-- ============================================================

-- Добавление администратора (пароль: admin123)
INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES (
    'admin',
    '.kh9J9QxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQx',
    'Администратор Системы',
    'admin@company.com',
    'ADMIN',
    true
) ON CONFLICT (username) DO NOTHING;

-- Добавление тестового пользователя (пароль: user123)
INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES (
    'user',
    '.kh9J9QxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQxXQx',
    'Иван Петров',
    'user@company.com',
    'USER',
    true
) ON CONFLICT (username) DO NOTHING;

-- Добавление переговорных комнат
INSERT INTO meeting_rooms (name, capacity, equipment, location, is_available)
VALUES 
    ('Комната 1', 6, 'Проектор, экран, конференц-телефон', '3 этаж', true),
    ('Комната 2', 10, 'Проектор, экран, колонки, доска', '3 этаж', true),
    ('Комната 3', 4, 'Телевизор, конференц-телефон', '4 этаж', true),
    ('Комната 4', 15, 'Проектор, экран, колонки, микрофоны', '4 этаж', true)
ON CONFLICT DO NOTHING;
