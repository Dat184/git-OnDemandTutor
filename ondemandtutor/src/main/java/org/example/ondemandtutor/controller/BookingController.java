package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.StatusBook;
import org.example.ondemandtutor.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok().body(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBookingById(@PathVariable long id) {
        try {
            BookingResponse bookingResponse = bookingService.getBookingById(id);
            ResponseObject response = new ResponseObject("success", "Booking found", bookingResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<BookingResponse>> findByStudentId(@PathVariable long studentId) {
        List<BookingResponse> bookings = bookingService.findByStudentId(studentId);
        return ResponseEntity.ok().body(bookings);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            if (bookingRequest.getStudentId() == null || bookingRequest.getTutorServiceId() == null) {
                throw new IllegalArgumentException("Student ID and Tutor Service ID must not be null");
            }
            BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
            ResponseObject response = new ResponseObject("success", "Booking created successfully", bookingResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Booking not created: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseObject> updateBookingStatus(@PathVariable long id, @RequestBody StatusBook status) {
//        try {
//            BookingResponse bookingResponse = bookingService.updateBookingStatus(id, status);
//            ResponseObject response = new ResponseObject("success", "Booking status updated successfully", bookingResponse);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (IllegalArgumentException e) {
//            ResponseObject response = new ResponseObject("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        } catch (Exception e) {
//            ResponseObject response = new ResponseObject("error", "Booking status not updated");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable Long id) {
//        try {
//            bookingService.deleteBooking(id);
//            ResponseObject response = new ResponseObject("success", "Booking deleted successfully");
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            ResponseObject response = new ResponseObject("error", "Booking not deleted");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
}
