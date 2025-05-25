package  com.example.demo.service ;

import org.springframework.stereotype.Service;

import com.example.demo.model.EmailDetails;


@Service
public interface EmailService {
    String sendSimpleMail(EmailDetails details);   
    String sendMailWithAttachment(EmailDetails details);
}