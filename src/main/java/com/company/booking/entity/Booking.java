package com.company.booking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom meetingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.SCHEDULED;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public boolean isActive() {
        return this.status == BookingStatus.SCHEDULED;
    }

    public long getDurationInMinutes() {
        return Duration.between(startDateTime, endDateTime).toMinutes();
    }
}
