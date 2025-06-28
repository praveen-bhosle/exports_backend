package com.example.demo.utils.EmailTemplates;

import com.example.demo.model.EmailTemplate;

public class PasswordResetConfirmationEmailTemplate { 

    static public EmailTemplate getEmailTemplate( String username  , String name ,  String url  ) {   

        String subject = "Your password was successfully updated" ;  

        String nameAssigned = name  != null  ? name : username ; 

        String body =  String.format(  
        """
        <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
              <p>Hi %s,</p>
              <p>We're letting you know that your account password was <strong>successfully changed</strong>.</p>
              <p>If you made this change, no further action is needed.</p>
              <p>If you did <strong>NOT</strong> change your password, please secure your account immediately by resetting your password using the link below: </p>
              <p>🔐 <a href="%s" style="color: #1a73e8;">Reset your password</a></p>
              <p>If you need help or have any questions, feel free to contact our support team.</p>
              <p>Stay safe,<br/><strong>YKDevout Exports Private Limited </strong></p>
            </body>
        </html>"""  ,  nameAssigned ,  url)   ;    

        EmailTemplate emailTemplate = new EmailTemplate() ; 
        emailTemplate.setBody(body); 
        emailTemplate.setSubject(subject);
        return emailTemplate ; 
    }    
}
