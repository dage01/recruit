package com.dage.recruit.controller;


import com.dage.recruit.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailService emailService;

    private Map<String, String> tokenStorage = new ConcurrentHashMap<>();
    private Set<String> confirmedTokens = ConcurrentHashMap.newKeySet();

    @RequestMapping("/authEmail")
    public ResponseEntity<String> sendEmail(@RequestParam("email") String email) {
        try {
            Context context = new Context();

            String token = UUID.randomUUID().toString();
            tokenStorage.put(token, email);

            // 인증 이메일 전송
//            String confirmationUrl = "http://210.206.179.41:8080/email/confirm?token=" + token;
            String confirmationUrl = "http://localhost:8080/email/confirm?token=" + token;

            context.setVariable("confirmationUrl", confirmationUrl);

            emailService.auth_email(email, "email/message", context);
            return ResponseEntity.ok().body("Email sent successfully."); // 이메일 성공적으로 보내기
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage()); // 이메일 보내기 실패
        }
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam String token, HttpSession session) {
        if (tokenStorage.containsKey(token) && !confirmedTokens.contains(token)) {
            String email = tokenStorage.get(token);
            confirmedTokens.add(token);
            session.setAttribute("authenticatedEmail", email); // 인증된 이메일을 세션에 저장

            return "redirect:/email/authenticatedEmail";
        } else {
            // 오류 처리 로직
            return "redirect:/email/error"; // 에러 페이지로 리다이렉션
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<String> checkEmailAuth(HttpSession session) {
        String authenticatedEmail = (String) session.getAttribute("authenticatedEmail");
        if (authenticatedEmail != null) {
            return ResponseEntity.ok("authenticated");
        } else {
            return ResponseEntity.ok("not authenticated");
        }
    }

    @GetMapping("/authenticatedEmail")
    public String authenticatedEmail() {

        return "email/email_suc";
    }

    @GetMapping("/error")
    public String email_error() {

        return "email/email_error";
    }
}
