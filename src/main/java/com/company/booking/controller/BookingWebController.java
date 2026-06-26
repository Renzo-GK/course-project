package com.company.booking.controller;

import com.company.booking.entity.User;
import com.company.booking.service.BookingService;
import com.company.booking.service.RoomService;
import com.company.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingWebController {
    private final RoomService roomService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "booking-form";
    }

    @GetMapping("/my")
    public String showMyBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("bookings", bookingService.getBookingsByUser(user.getId()));
        model.addAttribute("userId", user.getId());
        return "my-bookings";
    }

    @PostMapping("/create")
    public String createBooking(@RequestParam Long roomId,
                                @RequestParam String startDateTime,
                                @RequestParam String endDateTime,
                                @RequestParam(required = false) String subject,
                                @RequestParam(required = false) String description) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Преобразование строк в LocalDateTime
        java.time.LocalDateTime start = java.time.LocalDateTime.parse(startDateTime);
        java.time.LocalDateTime end = java.time.LocalDateTime.parse(endDateTime);

        com.company.booking.entity.Booking booking = new com.company.booking.entity.Booking();
        booking.setMeetingRoom(roomService.getRoomById(roomId));
        booking.setUser(user);
        booking.setStartDateTime(start);
        booking.setEndDateTime(end);
        booking.setSubject(subject);
        booking.setDescription(description);

        bookingService.createBooking(booking);
        return "redirect:/bookings/my";
    }
}