package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.pojo.StatusBook;
import org.example.ondemandtutor.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ResponseObject> getBookingById(@PathVariable long id) {
        try {
            Booking booking = bookingService.getBookingById(id);
            ResponseObject response = new ResponseObject("success", "Booking found", booking);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Booking>> findByStudentId(@PathVariable long studentId) {
        List<Booking> bookings = bookingService.findByStudentId(studentId);
        return ResponseEntity.ok().body(bookings);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBooking(@RequestBody BookingRequest booking) {
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
    public ResponseEntity<ResponseObject> updateBookingStatus(@PathVariable long id, @RequestBody StatusBook status) {
        try {
            bookingService.updateBookingStatus(id, status);
            ResponseObject response = new ResponseObject("success", "Booking status updated successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Booking status not updated");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable long id) {
        try {
            bookingService.deleteBooking(id);
            ResponseObject response = new ResponseObject("success", "Booking deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Booking not deleted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
