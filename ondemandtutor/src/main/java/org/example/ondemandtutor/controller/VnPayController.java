package org.example.ondemandtutor.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.ondemandtutor.dto.request.VnPayRequest;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.CallBackVnPay;
import org.example.ondemandtutor.dto.response.VnPayResponse;
import org.example.ondemandtutor.service.VnPayService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/v1/vnpay")
@RequiredArgsConstructor
public class VnPayController {

    private final VnPayService vnPayService;

    // Endpoint to create VNPay payment
    @PostMapping("/payment")
    public ApiResponse<VnPayResponse> createPayment(HttpServletRequest request, @RequestBody VnPayRequest vnPayRequest) {
        return vnPayService.createVnPayPayment(request, vnPayRequest);
    }

    // Endpoint to handle VNPay return callback
    @GetMapping("/callback")
    public ApiResponse<CallBackVnPay> handlePaymentReturn(@RequestParam Map<String, String> params) {
        return vnPayService.handleVnPayReturn(params);
    }
}
