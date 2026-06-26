package com.company.booking.service;

import com.company.booking.entity.Booking;
import com.company.booking.entity.BookingStatus;
import com.company.booking.entity.MeetingRoom;
import com.company.booking.entity.User;
import com.company.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserService userService;

    @Transactional
    public Booking createBooking(Booking booking) {
        // Проверяем, что комната существует и доступна
        MeetingRoom room = roomService.getRoomById(booking.getMeetingRoom().getId());
        if (!room.isAvailable()) {
            throw new RuntimeException("Room is not available");
        }

        // Проверяем, что пользователь существует и активен
        User user = userService.getUserById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isEnabled()) {
            throw new RuntimeException("User is disabled");
        }

        // Проверяем валидность времени
        if (!booking.getStartDateTime().isBefore(booking.getEndDateTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        // Проверяем минимальную длительность (15 минут)
        if (booking.getDurationInMinutes() < 15) {
            throw new RuntimeException("Booking must be at least 15 minutes");
        }

        // Проверяем конфликты
        if (bookingRepository.existsConflictingBooking(
                room.getId(),
                booking.getStartDateTime(),
                booking.getEndDateTime())) {
            throw new RuntimeException("Room is already booked for this time slot");
        }

        booking.setStatus(BookingStatus.SCHEDULED);
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Booking> getActiveBookingsByUser(Long userId) {
        return bookingRepository.findActiveBookingsByUser(userId);
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = getBookingById(bookingId);

        // Проверяем права: только владелец или админ
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!booking.getUser().getId().equals(userId) && !user.hasRole("ADMIN")) {
            throw new RuntimeException("You don't have permission to cancel this booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel completed booking");
        }

        booking.cancel();
        bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateBooking(Long id, Booking bookingData) {
        Booking booking = getBookingById(id);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Cannot update cancelled booking");
        }
        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Cannot update completed booking");
        }

        // Проверяем новое время
        if (!bookingData.getStartDateTime().isBefore(bookingData.getEndDateTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        // Проверяем конфликты с другими бронированиями
        if (bookingRepository.existsConflictingBooking(
                booking.getMeetingRoom().getId(),
                bookingData.getStartDateTime(),
                bookingData.getEndDateTime())) {
            throw new RuntimeException("Room is already booked for this time slot");
        }

        booking.setStartDateTime(bookingData.getStartDateTime());
        booking.setEndDateTime(bookingData.getEndDateTime());
        booking.setSubject(bookingData.getSubject());
        booking.setDescription(bookingData.getDescription());

        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId, LocalDateTime start, LocalDateTime end) {
        return !bookingRepository.existsConflictingBooking(roomId, start, end);
    }

    @Transactional(readOnly = true)
    public List<Booking> getConflictingBookings(Long roomId, LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findConflictingBookings(roomId, start, end);
    }

    @Transactional
    public void completeBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking.getStatus() != BookingStatus.SCHEDULED) {
            throw new RuntimeException("Only scheduled bookings can be completed");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
    }
}
