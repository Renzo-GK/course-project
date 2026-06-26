package com.company.booking.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private Integer capacity;
    private String equipment;
    private String location;
    private boolean available;
}