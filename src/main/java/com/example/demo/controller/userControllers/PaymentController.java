package com.example.demo.controller.userControllers ;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@RestController
@RequestMapping("/api/user/payment") 
public class PaymentController {

    private static final String KEY_ID = "rzp_test_thbnstH0Bq80hy"; // Replace with your Key ID
    private static final String KEY_SECRET = "oc86adrgm685ECs3Wzdk0nOb"; // Replace with your Key Secret

    @PostMapping("/create-order")
    public String createOrder(@RequestParam("amount") int amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        Order order = razorpay.orders.create(orderRequest);
        return order.toString();
    }

    @PostMapping("/payment-callback")
    public RedirectView paymentCallback(
            @RequestParam("razorpay_order_id") String razorpayOrderId,
            @RequestParam("razorpay_payment_id") String razorpayPaymentId,
            @RequestParam("razorpay_signature") String razorpaySignature) throws RazorpayException {
        try {
            // Verify the payment signature here
            String signature = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValid = Utils.verifySignature(signature, razorpaySignature, KEY_SECRET);

            if (isValid) {
                // Payment successful
                RedirectView redirectView = new RedirectView("/success.html?orderId=" + razorpayOrderId);
                return redirectView;
            } else {
                // Payment failed
                return new RedirectView("/failure.html"); // Create failure.html if needed
            }
        } catch (RazorpayException e) {
            System.err.println("Razorpay Exception during callback: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General Exception during callback: " + e.getMessage());
            throw new RazorpayException("General exception during callback");
        }
    }

    @PostMapping("/get-key")
    public String getKey() {
        return KEY_ID;
    }
}
