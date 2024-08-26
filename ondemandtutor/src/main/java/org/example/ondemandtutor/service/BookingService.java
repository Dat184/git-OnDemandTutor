package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.mapper.BookingMapper;
import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.pojo.StatusBook;
import org.example.ondemandtutor.pojo.TutorService;

import org.example.ondemandtutor.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookingService {

    final TutorServiceService tutorServiceService;
    final BookingRepository bookingRepository;
    final BookingMapper bookingMapper;
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        Booking booking = bookingMapper.toBooking(bookingRequest);
        booking.setTotalPrice(getTotalPrice(booking.getId()));
        return bookingMapper.toBookingResponse(booking);
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        return bookingMapper.toBookingResponse(booking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingMapper.toBookingResponseList(bookingRepository.findAll());
    }

    public BookingResponse updateBookingStatus(Long id, StatusBook status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.setStatusBook(status);
        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    public int getTotalPrice(Long id){
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        TutorService tutorService = booking.getTutorService();
        int sessionsOfWeek = tutorService.getSessionOfWeek();
        int priceOfSession = tutorService.getPriceOfSession();
        return sessionsOfWeek * priceOfSession;
    }

    public List<BookingResponse> findByStudentId(Long studentId) {
        List<Booking> booking = bookingRepository.findByStudentId(studentId);
        return bookingMapper.toBookingResponseList(booking);

    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }
}
