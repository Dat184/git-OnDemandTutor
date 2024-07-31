package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.repository.BookingRepository;
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
    private BookingRepository bookingRepository;

    @GetMapping("")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<Booking> foundBooking = bookingRepository.findById(id);
        return foundBooking.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query booking successfully", foundBooking)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Booking with id = " + id, "")
                );
    }

    @GetMapping("/student/{studentId}")
    public List<Booking> findByStudentId(@PathVariable long studentId) {
        return bookingRepository.findByStudentId(studentId);
    }

    @GetMapping("/tutor_service/{tutorServiceId}")
    public List<Booking> findByTutorServiceId(@PathVariable long tutorServiceId) {
        return bookingRepository.findByTutorServiceId(tutorServiceId);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertBooking(@RequestBody Booking newBooking) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert booking successfully", bookingRepository.save(newBooking))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBooking(@PathVariable long id, @RequestBody Booking newBooking) {
        Booking updatedBooking = bookingRepository.findById(id)
                .map(booking -> {
                    booking.setStudent(newBooking.getStudent());
                    booking.setTutorService(newBooking.getTutorService());
                    booking.setBookingTime(newBooking.getBookingTime());
                    booking.setTotalPrice(newBooking.getTotalPrice());
                    booking.setStatusBook(newBooking.getStatusBook());
                    return bookingRepository.save(booking);
                }).orElseGet(() -> {
                    newBooking.setId(id);
                    return bookingRepository.save(newBooking);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update booking successfully", updatedBooking)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable long id) {
        boolean exists = bookingRepository.existsById(id);
        if (exists) {
            bookingRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete booking successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Booking with id = " + id, "")
            );
        }
    }
}
