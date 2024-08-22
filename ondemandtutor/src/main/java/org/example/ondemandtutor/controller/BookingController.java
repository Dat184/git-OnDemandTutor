package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok().body(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Booking>> getBookingById(@PathVariable long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return ResponseEntity.ok().body(booking);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Booking>> findByStudentId(@PathVariable long studentId) {
        List<Booking> bookings = bookingService.findByStudentId(studentId);
        return ResponseEntity.ok().body(bookings);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBooking(@RequestBody Booking booking) {
        try {
            bookingService.createBooking(booking);
            ResponseObject response = new ResponseObject("success", "Booking created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Booking not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable long id, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, booking.getStatusBook());
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable long id) {
        Booking deletedBooking = bookingService.deteleBooking(id);
        return ResponseEntity.ok(deletedBooking);
    }
}
