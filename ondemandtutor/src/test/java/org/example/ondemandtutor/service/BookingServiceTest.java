package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.mapper.BookingMapper;
import org.example.ondemandtutor.pojo.Booking;

import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.BookingRepository;
import org.example.ondemandtutor.repository.TutorServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private TutorServiceRepository tutorServiceRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;
    private TutorService tutorService;

//    @BeforeEach
//    void setUp() {
//        booking = new Booking();
//        bookingRequest = new BookingRequest(1L, 1L);
//        bookingResponse = new BookingResponse(1L, 1L, 1L, "CONFIRMED", 200);
//        tutorService = new TutorService();
//        tutorService.setSessionOfWeek(2);
//        tutorService.setPriceOfSession(100);
//    }

    @Test
    void createBooking() {
        when(bookingMapper.toBooking(any(BookingRequest.class))).thenReturn(booking);
        when(tutorServiceRepository.findById(anyLong())).thenReturn(Optional.of(tutorService));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toBookingResponse(any(Booking.class))).thenReturn(bookingResponse);

        BookingResponse response = bookingService.createBooking(bookingRequest);

        assertEquals(200, response.getTotalPrice());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void getBookingById() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingMapper.toBookingResponse(any(Booking.class))).thenReturn(bookingResponse);

        BookingResponse response = bookingService.getBookingById(1L);

        assertNotNull(response);
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));
        when(bookingMapper.toBookingResponseList(anyList())).thenReturn(Arrays.asList(bookingResponse));

        List<BookingResponse> responses = bookingService.getAllBookings();

        assertFalse(responses.isEmpty());
        verify(bookingRepository, times(1)).findAll();
    }


    @Test
    void findByStudentId() {
        when(bookingRepository.findByStudentId(anyLong())).thenReturn(Arrays.asList(booking));
        when(bookingMapper.toBookingResponseList(anyList())).thenReturn(Arrays.asList(bookingResponse));

        List<BookingResponse> responses = bookingService.findByStudentId(1L);

        assertFalse(responses.isEmpty());
        verify(bookingRepository, times(1)).findByStudentId(anyLong());
    }

    @Test
    void deleteBooking() {
        bookingService.deleteBooking(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }
}
