package com.company.booking.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateBookingRequest {
    private Long roomId;
    private Long userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String subject;
    private String description;
}