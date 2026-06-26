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
        MeetingRoom room = roomService.getRoomById(booking.getMeetingRoom().getId());
        if (!room.isAvailable()) {
            throw new RuntimeException("Room is not available");
        }

        User user = userService.getUserById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isEnabled()) {
            throw new RuntimeException("User is disabled");
        }

        if (!booking.getStartDateTime().isBefore(booking.getEndDateTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        if (booking.getDurationInMinutes() < 15) {
            throw new RuntimeException("Booking must be at least 15 minutes");
        }

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

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = getBookingById(bookingId);
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

    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId, LocalDateTime start, LocalDateTime end) {
        return !bookingRepository.existsConflictingBooking(roomId, start, end);
    }
}