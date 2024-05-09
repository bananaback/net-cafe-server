package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.services.emailservices.EmailService;

@RestController
@RequestMapping("api/email")
public class PromotionEmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-promotion-email")
    public String sendPromotionEmail(@RequestParam String userEmail) {
        try {
            String subject = "Special Promotion for Our NetCafe Customers!";
            String text = "Dear valued customer,\n\n" +
                    "We are excited to offer you a special promotion at our NetCafe!\n" +
                    "Visit us today and enjoy exclusive discounts on our services.\n" +
                    "Thank you for choosing our NetCafe.\n\n" +
                    "Best regards,\n" +
                    "The NetCafe Team";

            emailService.sendSimpleMessage(userEmail, subject, text);
            return "Promotional email sent successfully.";
        } catch (Exception e) {
            return "Failed to send promotional email: " + e.getMessage();
        }
    }
}
