package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.BookingMapper;
import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.pojo.Student;

import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.BookingRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {

    BookingRepository bookingRepository;
    BookingMapper bookingMapper;
    UserRepository userRepository;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Student student = (Student) user;
        Booking booking = bookingMapper.toBooking(bookingRequest);
        booking.setStudent(student);
        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        return bookingMapper.toBookingResponse(booking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingMapper.toBookingResponseList(bookingRepository.findAll());
    }

    public List<BookingResponse> findByStudentId(Long studentId) {
        List<Booking> booking = bookingRepository.findByStudentId(studentId);
        return bookingMapper.toBookingResponseList(booking);

    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }
    public Optional<BookingResponse> findBookingByTutorServiceAndStudent(Long tutorServiceId, Long studentId) {
        Optional<Booking> booking = bookingRepository.findBookingByTutorServiceIdAndStudentId(tutorServiceId, studentId);
        return booking.map(bookingMapper::toBookingResponse);
    }
}
