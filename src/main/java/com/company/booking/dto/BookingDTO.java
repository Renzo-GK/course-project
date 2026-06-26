package com.company.booking.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private Long roomId;
    private String roomName;
    private Long userId;
    private String userFullName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String status;
    private String subject;
    private String description;
}
