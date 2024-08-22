package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.pojo.StatusBook;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void createBooking(Booking booking){
        booking.setTotalPrice(getTotalPrice(booking.getId()));
        bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBookingStatus(Long id, StatusBook status) {
        Booking booking = getBookingById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatusBook(status);
        return bookingRepository.save(booking);
    }

    public int getTotalPrice(Long id){
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        TutorService tutorService = booking.getTutorService();
        int sessionsOfWeek = tutorService.getSessionOfWeek();
        int priceOfSession = tutorService.getPriceOfSession();
        return sessionsOfWeek * priceOfSession;
    }

    public List<Booking> findByStudentId(Long studentId) {
        return bookingRepository.findByStudentId(studentId);

    }

    public Booking deteleBooking(Long id){
        Booking booking = getBookingById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
        return booking;
    }
}
