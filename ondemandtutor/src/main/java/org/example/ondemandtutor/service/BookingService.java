package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.pojo.StatusBook;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.BookingRepository;

import org.example.ondemandtutor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorServiceService tutorServiceService;
    public void createBooking(BookingRequest bookingRequest) {
        Student student = studentRepository.findById(bookingRequest.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        TutorService tutorService = tutorServiceService.getTutorServiceById(bookingRequest.getTutorServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Tutor service not found"));
        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setTutorService(tutorService);
        booking.setTotalPrice(getTotalPrice(booking.getId()));
        bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void  updateBookingStatus(Long id, StatusBook status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.setStatusBook(status);
        bookingRepository.save(booking);
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

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }
}
