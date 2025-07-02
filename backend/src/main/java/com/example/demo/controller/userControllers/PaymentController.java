package com.example.demo.controller.userControllers ;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.component.PaymentProperties;
import com.example.demo.records.TransactionDTO;
import com.example.demo.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/payment") 
public class PaymentController {

    @Autowired
    private PaymentProperties paymentProperties ; 

    @Autowired 
    private OrderService orderService ; 

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestParam("amount") int amount) throws RazorpayException {
        try { 
        RazorpayClient razorpay = new RazorpayClient(paymentProperties.getId(), paymentProperties.getSecret());
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        Order order = razorpay.orders.create(orderRequest); 
        String order_id   = order.get("id") ; 
        Long amountInLong =  Long.valueOf(amount) ; 
        orderService.createOrder(order_id , amountInLong) ;  
        return  new ResponseEntity<>(  order.toString() ,  HttpStatus.CREATED ) ; }
        catch( Exception  e) {  
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>( "Internal server error. Failed to create order." , HttpStatus.INTERNAL_SERVER_ERROR ) ; 
        } 
    }

    @PostMapping("/payment-callback")
    public ResponseEntity<?>  paymentCallback(@Valid @RequestBody TransactionDTO transactionDTO  ,  @RequestParam String orderId  ) throws RazorpayException {
        try {
            String razorpayOrderId = transactionDTO.razorpay_order_id() ;
            String razorpayPaymentId =transactionDTO.razorpay_payment_id() ;
            String razorpaySignature = transactionDTO.razorpay_signatue() ;
            String signature = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValid = Utils.verifySignature(signature, razorpaySignature, paymentProperties.getSecret()); 
            System.err.println( "validation result : "  + isValid) ; 
            if (isValid) { 
                orderService.ValidateOrder(orderId, transactionDTO);
                return  new ResponseEntity<>( "Payment is successfull.", HttpStatus.OK )  ; 
            } 
            else {
              return new ResponseEntity<>(  "The payment is not legit.Transaction incomplete." , HttpStatus.BAD_REQUEST )   ; 
            }
        } 
        catch (RazorpayException e) {
            System.err.println("Razorpay Exception during callback: " + e.getMessage());
            return new ResponseEntity<>("internal server error" ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
        } 
        catch (Exception e) {
            System.err.println("General Exception during callback: " + e.getMessage());
            return new  ResponseEntity<>("General exception during callback" ,  HttpStatus.INTERNAL_SERVER_ERROR ); 
        }
    }


}
