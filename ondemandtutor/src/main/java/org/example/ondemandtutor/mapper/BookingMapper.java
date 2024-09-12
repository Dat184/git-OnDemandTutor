package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.request.VnPayRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.pojo.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "tutorService.id", source = "tutorServiceId")
    Booking toBooking(BookingRequest bookingRequest);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "tutorServiceId", source = "tutorService.id")
    @Mapping(target = "tutorName", source = "tutorService.tutor.name")
    @Mapping(target = "subjectName", source = "tutorService.subject.name")
    BookingResponse toBookingResponse(Booking booking);

    List<BookingResponse> toBookingResponseList(List<Booking> bookings);
}
