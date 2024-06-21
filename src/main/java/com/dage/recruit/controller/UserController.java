package com.dage.recruit.controller;

import com.dage.recruit.dto.UserDTO;
import com.dage.recruit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/applicant_login")
    public String login_form() {

        return "user/user_login";
    }

    @GetMapping("/email_test_page")
    public String em() {

        return "user/email";
    }

    /* 비밀번호 변경 */
    @PostMapping("/pw_change")
    public ResponseEntity<String> pwChange(@RequestParam String req_email,
                                           @RequestParam String sel_remarks,
                                           @RequestParam String get_password,
                                           @RequestParam String new_password) {
        if (userService.changePassword(sel_remarks, req_email, get_password, new_password)) {
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다."); // 변경 성공 응답
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 올바르지 않습니다."); // 변경 실패 응답
        }
    }

    /* 비밀번호 초기화 */
    @PostMapping("/pw_reset")
    public ResponseEntity<String> pwReset(@RequestParam String req_email,
                                           @RequestParam String sel_remarks,
                                           @RequestParam String get_name,
                                           @RequestParam String get_birth) throws MessagingException {
        if (userService.resetPassword(sel_remarks, req_email, get_name, get_birth.replace("-", ""))) {
            return ResponseEntity.ok("해당 이메일로 초기화된 비밀번호가 발송되었습니다."); // 변경 성공 응답
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("입력하신 정보가 올바르지 않습니다. 다시 확인해주세요."); // 변경 실패 응답
        }

    }


    // 로그인 처리를 담당하는 핸들러
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String user_email, @RequestParam String user_password,
                                        HttpSession session) {
        if (userService.getUserLogin(user_email, user_password)) {
            List<UserDTO> userSession = userService.getUserList(user_email); // 사용자 정보를 가져옴
            session.setAttribute("userSession", userSession);
            session.setAttribute("user_email", user_email);

            return ResponseEntity.ok("로그인 성공"); // 로그인 성공 응답
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패\n이메일과 비밀번호를 확인해 주세요."); // 로그인 실패 응답
        }
    }

    // 로그아웃 핸들러
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute("userSession");
        session.removeAttribute("seq");

        return "redirect:/job-posting/list"; // 로그인 페이지로 리다이렉트
    }

    @GetMapping("/apply_list")
    public String app_list(HttpSession session, Model model) {
        List<UserDTO> userSession = (List<UserDTO>) session.getAttribute("userSession");

        if (userSession == null) {
            model.addAttribute("sessionExpired", true);
        } else {
            model.addAttribute("appList", userSession);
        }

        model.addAttribute("appList", userSession);

        return "user/apply_list"; // 로그인 페이지로 리다이렉트
    }


}
