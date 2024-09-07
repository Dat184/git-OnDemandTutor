package org.example.ondemandtutor.config;

import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.util.VnPayUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
@ComponentScan("org.example.ondemandtutor")
@Configuration
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class VnPayConfig {
    @Getter
    String vnp_PayUrl="http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    String vnp_ReturnUrl="http://localhost:8080/v1/vnpay/callback";
    String vnp_TmnCode ="V45O0WA3";
    @Getter
    String HashSecret="N13S9RBKLTZ49UWP8QLYN15BQXPZ56KW";
    String vnp_Version="2.1.0";
    String vnp_Command="pay";
    String orderType="other";

    public Map<String, String> getVNPayConfig() {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  VnPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan booking.html:" +  VnPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}
