package com.example.demo.controller.userControllers ;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.records.TransactionDTO;
import com.example.demo.service.TransactionService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/public/payment") 
public class PaymentController {

    private static final String KEY_ID = "rzp_test_ehxRg1TmkA0kFi"; 
    private static final String KEY_SECRET = "PDuHjMBQE4iMBMdcVKbScw2J"; 

    @Autowired 
    private TransactionService transactionService ; 

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
    public String  paymentCallback(@Valid @RequestBody TransactionDTO transactionDTO) throws RazorpayException {
        try {
            
            String razorpayOrderId = transactionDTO.razorpay_order_id() ;
            String razorpayPaymentId =transactionDTO.razorpay_payment_id() ;
            String razorpaySignature = transactionDTO.razorpay_signatue() ;
             
            String signature = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValid = Utils.verifySignature(signature, razorpaySignature, KEY_SECRET);

            if (isValid) {
                transactionService.createTransaction(transactionDTO) ; 
                return "Payment is successfull." ; 
            } else {
                
              return  "The payment is not legit. Transaction incomplete."  ; 
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
