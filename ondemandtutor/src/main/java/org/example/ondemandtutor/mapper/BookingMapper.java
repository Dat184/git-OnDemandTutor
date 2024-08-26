package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.response.BookingResponse;
import org.example.ondemandtutor.pojo.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "tutorService.id", source = "tutorServiceId")
    Booking toBooking(BookingRequest bookingRequest);

    BookingResponse toBookingResponse(Booking booking);

    void updateBooking(@MappingTarget Booking booking, BookingRequest bookingRequest);

    List<BookingResponse> toBookingResponseList(List<Booking> bookings);
}
