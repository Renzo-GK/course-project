package com.company.booking.controller;

import com.company.booking.dto.CreateBookingRequest;
import com.company.booking.dto.RoomDto;
import com.company.booking.dto.BookingResponseDto;
import com.company.booking.entity.Booking;
import com.company.booking.entity.MeetingRoom;
import com.company.booking.entity.User;
import com.company.booking.service.BookingService;
import com.company.booking.service.RoomService;
import com.company.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final RoomService roomService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/rooms")
    public List<RoomDto> getRooms() {
        return roomService.getAllRooms().stream()
                .map(this::convertToRoomDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
        try {
            MeetingRoom room = roomService.getRoomById(request.getRoomId());
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Booking booking = new Booking();
            booking.setMeetingRoom(room);
            booking.setUser(user);
            booking.setStartDateTime(request.getStartDateTime());
            booking.setEndDateTime(request.getEndDateTime());
            booking.setSubject(request.getSubject());
            booking.setDescription(request.getDescription());

            Booking created = bookingService.createBooking(booking);
            return ResponseEntity.ok(convertToBookingResponseDto(created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bookings/user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByUser(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        List<BookingResponseDto> dtos = bookings.stream()
                .map(this::convertToBookingResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id, @RequestParam Long userId) {
        try {
            bookingService.cancelBooking(id, userId);
            return ResponseEntity.ok("Booking cancelled");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private RoomDto convertToRoomDto(MeetingRoom room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setEquipment(room.getEquipment());
        dto.setLocation(room.getLocation());
        dto.setAvailable(room.isAvailable());
        return dto;
    }

    private BookingResponseDto convertToBookingResponseDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
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