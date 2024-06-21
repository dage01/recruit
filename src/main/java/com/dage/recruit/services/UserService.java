package com.dage.recruit.services;

import com.dage.recruit.dao.UserDAO;
import com.dage.recruit.dto.UserDTO;
import com.dage.recruit.dto.SelectCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    /* 계정(이메일) 중복 체크 */
    public boolean checkDuplicateEmail(String yyyy, String degrees, String user_email, long seq) {
        UserDTO existingUser = userDAO.appUser(yyyy, degrees, user_email, seq);
        return existingUser != null;
    }

    /* 회원 정보 */
    public UserDTO getUser(String yyyy, String degrees, String user_email,long seq) {
        return userDAO.appUser(yyyy, degrees, user_email,seq);
    }

    /* 지원자 리스트 */
    public List<UserDTO> getUserList(String user_email) {
        return userDAO.getUserList(user_email);
    }

    /* 지원한 공고 리스트 */
    public List<SelectCodeDTO> selectRemarksList(String email) {
        return userDAO.selectRemarksList(email);
    }


    /* 로그인 */
    public boolean getUserLogin(String user_email, String user_password) {
        List<UserDTO> userList = userDAO.getUserList(user_email);

        for (UserDTO user : userList) {
            if (user != null && user.getPin().equals(user_password)) {
                return true; // 인증 성공
            }
        }
        return false; // 인증 실패
    }

    /* 비밀번호 변경 */
    public boolean changePassword(String sel_remarks, String req_email, String get_password, String new_password) {
        // 현재 비밀번호가 맞는지 확인
        int count = userDAO.verifyPassword(sel_remarks, req_email, get_password, null,null);
        if (count != 0) {
            // 비밀번호 변경
            userDAO.changePassword(sel_remarks, req_email, new_password);
            return true; // 변경 성공
        } else {
            return false; // 변경 실패 (비밀번호 불일치)
        }
    }

    /* 비밀번호 초기화 및 이메일 전송 */
    public boolean resetPassword(String sel_remarks, String req_email, String get_name, String get_birth) throws MessagingException {

        int count = userDAO.verifyPassword(sel_remarks, req_email, null, get_name,  get_birth);
        if (count != 0) {
            // 랜덤한 비밀번호 생성
            String new_password = generateRandomPassword();

            // 비밀번호 변경
            userDAO.changePassword(sel_remarks, req_email, new_password);

            // 이메일로 변경된 비밀번호 전송
            sendPasswordResetEmail(req_email, new_password);
            return true; // 변경 성공
        } else {
            return false; // 변경 실패 (비밀번호 불일치)
        }

    }

    /* 랜덤 비밀번호 생성 */
    private String generateRandomPassword() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        String OTHER_CHAR = "!@#$%&*()_+-=[]?";
        String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(index));
        }
        return password.toString();
    }

    /* 초기화 비밀번호 이메일 안내 */
    private void sendPasswordResetEmail(String req_email, String new_password) throws MessagingException {

        Context context = new Context();
        context.setVariable("new_password", new_password);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(req_email);
        helper.setFrom("dage01@dage.co.kr");
        helper.setSubject("[동아지질] 비밀번호 초기화 안내");

        String htmlContent = templateEngine.process("email/reset_message", context);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    /* API */
    /* 지원서 작성 */
    public void appRegForm(Map<String, Object> map) {
        userDAO.appRegForm(map);
    }


}
