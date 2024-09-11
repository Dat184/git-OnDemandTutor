package org.example.ondemandtutor.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.ondemandtutor.dto.request.BookingRequest;
import org.example.ondemandtutor.dto.request.VnPayRequest;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.CallBackVnPay;
import org.example.ondemandtutor.dto.response.VnPayResponse;
import org.example.ondemandtutor.service.BookingService;
import org.example.ondemandtutor.service.VnPayService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/vnpay")
@RequiredArgsConstructor
public class VnPayController {

    private final VnPayService vnPayService;
    private final BookingService bookingService;

    // Endpoint to create VNPay payment
    @PostMapping("/payment")
    public ApiResponse<VnPayResponse> createPayment(HttpServletRequest request, @RequestBody VnPayRequest vnPayRequest) {
        return vnPayService.createVnPayPayment(request, vnPayRequest);
    }

    // Endpoint to handle VNPay return callback
    @GetMapping("/callback")
    public void handlePaymentReturn(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        // Xử lý callback và lấy kết quả
        ApiResponse<CallBackVnPay> callBackResponse = vnPayService.handleVnPayReturn(params);
        if (callBackResponse.getResult().getResponseCode().equals("00")) {
            response.sendRedirect("/html/booking.html");
        } else {
            response.sendRedirect("/paymentFailed.html");
        }
    }
}
