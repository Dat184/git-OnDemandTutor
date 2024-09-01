package org.example.ondemandtutor.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.config.VnPayConfig;
import org.example.ondemandtutor.dto.request.VnPayRequest;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.CallBackVnPay;
import org.example.ondemandtutor.dto.response.VnPayResponse;
import org.example.ondemandtutor.pojo.Booking;
import org.example.ondemandtutor.util.VnPayUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VnPayService {
    VnPayConfig vnPayConfig;
    BookingService bookingService;
    public ApiResponse<VnPayResponse> createVnPayPayment(HttpServletRequest request, VnPayRequest vnPayRequest) {
        // Tạo URL thanh toán
        long amount = vnPayRequest.getTotalPrice() * 100L;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VnPayUtil.getIpAddress(request));

        // Trả về URL thanh toán
        String queryUrl = VnPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VnPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return ApiResponse.<VnPayResponse>builder()
                .code(200)
                .message("Success")
                .result(VnPayResponse.builder()
                        .code("ok")
                        .message("success")
                        .paymentUrl(paymentUrl)
                        .build())
                .build();
    }

    public ApiResponse<CallBackVnPay> handleVnPayReturn(@RequestParam Map<String, String> params) {
        // Xác thực chữ ký trả về từ VNPay
        String vnpSecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash");
        String hashData = VnPayUtil.getPaymentURL(params, false);
        String calculatedHash = VnPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), hashData);

        // Kiểm tra chữ ký
        if (!vnpSecureHash.equals(calculatedHash)) {
            return ApiResponse.<CallBackVnPay>builder()
                    .code(400)
                    .message("Bad Request")
                    .result(CallBackVnPay.builder()
                            .code("error")
                            .message("Invalid signature")
                            .build())
                    .build();
        }

        // Kiểm tra trạng thái giao dịch
        String responseCode = params.get("vnp_ResponseCode");
        switch (responseCode) {
            case "00":
                // Giao dịch thành công
                String transactionId = params.get("vnp_TransactionNo");
                long amount = Long.parseLong(params.get("vnp_Amount")) / 100L;
                return ApiResponse.<CallBackVnPay>builder()
                        .code(200)
                        .message("Success")
                        .result(CallBackVnPay.builder()
                                .amount(amount)
                                .transactionId(transactionId)
                                .responseCode(responseCode)
                                .message("Payment successful")
                                .code("success")
                                .build())
                        .build();

            case "11":
                // Đã hết hạn chờ thanh toán
                return ApiResponse.<CallBackVnPay>builder()
                        .code(400)
                        .message("Transaction Timeout")
                        .result(CallBackVnPay.builder()
                                .code("error")
                                .message("Transaction timeout. Please try again.")
                                .responseCode(responseCode)
                                .build())
                        .build();

            case "12":
                // Tài khoản của khách hàng bị khóa
                return ApiResponse.<CallBackVnPay>builder()
                        .code(400)
                        .message("Account Locked")
                        .result(CallBackVnPay.builder()
                                .code("error")
                                .message("Your card/account is locked. Please contact your bank for assistance.")
                                .responseCode(responseCode)
                                .build())
                        .build();

            case "13":
                // Nhập sai mật khẩu xác thực giao dịch (OTP)
                return ApiResponse.<CallBackVnPay>builder()
                        .code(400)
                        .message("Incorrect OTP")
                        .result(CallBackVnPay.builder()
                                .code("error")
                                .message("Incorrect OTP. Please try again.")
                                .responseCode(responseCode)
                                .build())
                        .build();

            case "24":
                // Giao dịch bị hủy
                return ApiResponse.<CallBackVnPay>builder()
                        .code(400)
                        .message("Transaction Canceled")
                        .result(CallBackVnPay.builder()
                                .code("error")
                                .message("Transaction was canceled by the user.")
                                .responseCode(responseCode)
                                .build())
                        .build();

            case "51":
                // Tài khoản không đủ số dư
                return ApiResponse.<CallBackVnPay>builder()
                        .code(400)
                        .message("Insufficient Funds")
                        .result(CallBackVnPay.builder()
                                .code("error")
                                .message("Insufficient funds in the account. Please check your balance.")
                                .responseCode(responseCode)
                                .build())
                        .build();
            default:
                // Giao dịch không thành công
                return ApiResponse.<CallBackVnPay>builder()
                        .code(400)
                        .message("Bad Request")
                        .result(CallBackVnPay.builder()
                                .code("error")
                                .message("Payment failed")
                                .responseCode(responseCode)
                                .build())
                        .build();
        }
    }
}
