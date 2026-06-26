package com.company.booking.config;

import com.company.booking.entity.*;
import com.company.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // Проверяем, есть ли уже данные
        if (userRepository.count() > 0) {
            return;
        }

        System.out.println("=== ЗАГРУЗКА ТЕСТОВЫХ ДАННЫХ ===");

        // 1. Создаём администратора
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Администратор");
        admin.setEmail("admin@company.com");
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        userRepository.save(admin);

        // 2. Создаём обычного пользователя
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setFullName("Иван Петров");
        user.setEmail("user@company.com");
        user.setRole(Role.USER);
        user.setEnabled(true);
        userRepository.save(user);

        // 3. Создаём переговорные комнаты
        MeetingRoom room1 = new MeetingRoom();
        room1.setName("Комната 1");
        room1.setCapacity(6);
        room1.setEquipment("Проектор, экран");
        room1.setLocation("3 этаж");
        room1.setAvailable(true);
        roomRepository.save(room1);

        MeetingRoom room2 = new MeetingRoom();
        room2.setName("Комната 2");
        room2.setCapacity(10);
        room2.setEquipment("Проектор, экран, колонки");
        room2.setLocation("3 этаж");
        room2.setAvailable(true);
        roomRepository.save(room2);

        MeetingRoom room3 = new MeetingRoom();
        room3.setName("Комната 3");
        room3.setCapacity(4);
        room3.setEquipment("Телевизор, конференц-телефон");
        room3.setLocation("4 этаж");
        room3.setAvailable(true);
        roomRepository.save(room3);

        // 4. Создаём тестовое бронирование
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMeetingRoom(room1);
        booking.setStartDateTime(LocalDateTime.now().plusHours(1));
        booking.setEndDateTime(LocalDateTime.now().plusHours(2));
        booking.setSubject("Тестовая встреча");
        booking.setStatus(BookingStatus.SCHEDULED);
        bookingRepository.save(booking);

        System.out.println("=== ДАННЫЕ ЗАГРУЖЕНЫ ===");
        System.out.println("Пользователи: " + userRepository.count());
        System.out.println("Комнаты: " + roomRepository.count());
        System.out.println("Бронирования: " + bookingRepository.count());
    }
}