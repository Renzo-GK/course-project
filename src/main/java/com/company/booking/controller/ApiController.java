package com.company.booking.controller;

import com.company.booking.entity.MeetingRoom;
import com.company.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final RoomService roomService;

    @GetMapping("/rooms")
    public List<MeetingRoom> getRooms() {
        return roomService.getAllRooms();
    }
}