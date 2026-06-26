package com.company.booking.repository;

import com.company.booking.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<MeetingRoom, Long> {
    @Query("SELECT r FROM MeetingRoom r WHERE r.isAvailable = true")
    List<MeetingRoom> findAllAvailable();

    @Query("SELECT r FROM MeetingRoom r " +
           "WHERE r.isAvailable = true " +
           "AND NOT EXISTS (SELECT b FROM Booking b " +
           "WHERE b.meetingRoom = r " +
           "AND b.status = 'SCHEDULED' " +
           "AND b.startDateTime < :end " +
           "AND b.endDateTime > :start)")
    List<MeetingRoom> findAvailableRooms(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);
}