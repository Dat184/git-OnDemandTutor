package org.example.ondemandtutor.controller;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/v1/booking")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookingController {

    BookingService bookingService;

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


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            ResponseObject response = new ResponseObject("success", "Booking deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Booking not deleted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("/getBooking")
    public ResponseEntity<Optional<BookingResponse>> getBookingByTutorAndStudent(@RequestBody BookingRequest bookingRequest) {
        Optional<BookingResponse> booking = bookingService.findBookingByTutorAndStudent(bookingRequest.getTutorId(), bookingRequest.getStudentId());
        return ResponseEntity.ok().body(booking);
    }
    @GetMapping("/TutorService/{tutorServiceId}")
    public ResponseEntity<Optional<BookingResponse>> getBookingByTutorService(@PathVariable Long tutorServiceId) {
        Optional<BookingResponse> booking = bookingService.findBookingByTutorService(tutorServiceId);
        return ResponseEntity.ok().body(booking);
    }

}
