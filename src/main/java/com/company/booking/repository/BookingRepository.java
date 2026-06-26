package com.company.booking.repository;

import com.company.booking.entity.Booking;
import com.company.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByMeetingRoomId(Long roomId);

    @Query("SELECT b FROM Booking b " +
           "WHERE b.meetingRoom.id = :roomId " +
           "AND b.status = 'SCHEDULED' " +
           "AND b.startDateTime < :end " +
           "AND b.endDateTime > :start")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
           "WHERE b.meetingRoom.id = :roomId " +
           "AND b.status = 'SCHEDULED' " +
           "AND b.startDateTime < :end " +
           "AND b.endDateTime > :start")
    boolean existsConflictingBooking(@Param("roomId") Long roomId,
                                     @Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    List<Booking> findByStatus(BookingStatus status);

    @Query("SELECT b FROM Booking b " +
           "WHERE b.user.id = :userId " +
           "AND b.status = 'SCHEDULED' " +
           "ORDER BY b.startDateTime ASC")
    List<Booking> findActiveBookingsByUser(@Param("userId") Long userId);
}
