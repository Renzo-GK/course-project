package com.company.booking.controller;

import com.company.booking.service.RoomService;
import com.company.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final RoomService roomService;
    private final BookingService bookingService;

    @GetMapping("/")
    public String home() {
        return "redirect:/rooms";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms";
    }

    @GetMapping("/rooms/{id}")
    public String roomDetails(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "room-details";
    }

    @GetMapping("/bookings/create")
    public String createBookingForm(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "booking-form";
    }

    @GetMapping("/bookings/my")
    public String myBookings(Model model) {
        // Временно показываем все бронирования (позже будет привязано к пользователю)
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "my-bookings";
    }
}
