package com.company.booking.controller;

import com.company.booking.dto.BookingDTO;
import com.company.booking.dto.RoomDTO;
import com.company.booking.dto.UserDTO;
import com.company.booking.entity.Booking;
import com.company.booking.entity.MeetingRoom;
import com.company.booking.entity.User;
import com.company.booking.service.BookingService;
import com.company.booking.service.RoomService;
import com.company.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;
    private final RoomService roomService;
    private final BookingService bookingService;

    // ===== Аутентификация =====
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        return userService.authenticate(username, password)
                .map(user -> ResponseEntity.ok("Login successful"))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registered = userService.register(user);
            return ResponseEntity.ok(registered);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===== Комнаты =====
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms().stream()
                .map(this::toRoomDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        try {
            MeetingRoom room = roomService.getRoomById(id);
            return ResponseEntity.ok(toRoomDTO(room));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rooms/available")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<RoomDTO> rooms = roomService.getAvailableRooms(start, end).stream()
                .map(this::toRoomDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }

    // ===== Бронирования =====
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking created = bookingService.createBooking(booking);
            return ResponseEntity.ok(toBookingDTO(created));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bookings/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(@PathVariable Long userId) {
        List<BookingDTO> bookings = bookingService.getBookingsByUser(userId).stream()
                .map(this::toBookingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id, @RequestParam Long userId) {
        try {
            bookingService.cancelBooking(id, userId);
            return ResponseEntity.ok("Booking cancelled");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bookings/check")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long roomId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        boolean available = bookingService.isRoomAvailable(roomId, start, end);
        return ResponseEntity.ok(available);
    }

    // ===== Вспомогательные методы для конвертации =====
    private RoomDTO toRoomDTO(MeetingRoom room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setEquipment(room.getEquipment());
        dto.setLocation(room.getLocation());
        dto.setAvailable(room.isAvailable());
        return dto;
    }

    private BookingDTO toBookingDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setRoomId(booking.getMeetingRoom().getId());
        dto.setRoomName(booking.getMeetingRoom().getName());
        dto.setUserId(booking.getUser().getId());
        dto.setUserFullName(booking.getUser().getFullName());
        dto.setStartDateTime(booking.getStartDateTime());
        dto.setEndDateTime(booking.getEndDateTime());
        dto.setStatus(booking.getStatus().name());
        dto.setSubject(booking.getSubject());
        dto.setDescription(booking.getDescription());
        return dto;
    }
}
