package com.company.booking.service;

import com.company.booking.entity.MeetingRoom;
import com.company.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public MeetingRoom createRoom(MeetingRoom room) {
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public List<MeetingRoom> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MeetingRoom> getAvailableRooms() {
        return roomRepository.findAllAvailable();
    }

    @Transactional(readOnly = true)
    public List<MeetingRoom> getAvailableRooms(LocalDateTime start, LocalDateTime end) {
        return roomRepository.findAvailableRooms(start, end);
    }

    @Transactional(readOnly = true)
    public MeetingRoom getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Transactional
    public MeetingRoom updateRoom(Long id, MeetingRoom roomData) {
        MeetingRoom room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setName(roomData.getName());
        room.setCapacity(roomData.getCapacity());
        room.setEquipment(roomData.getEquipment());
        room.setLocation(roomData.getLocation());
        room.setAvailable(roomData.isAvailable());
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}