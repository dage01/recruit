package com.dage.recruit.api;

import com.dage.recruit.component.CommonUtils;
import com.dage.recruit.dto.SelectCodeDTO;
import com.dage.recruit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api_user")
public class UserApiController {

    @Autowired
    UserService userService;

    @Autowired
    CommonUtils commonUtils;

    @RequestMapping("/checkDuplicateEmail")
    public boolean checkDuplicateEmail(@RequestParam("yyyy") String yyyy, @RequestParam("degrees") String degrees, @RequestParam("user_email") String user_email) {
        try {
            boolean isDuplicate = userService.checkDuplicateEmail(yyyy, degrees, user_email, 0L);
            System.out.println(isDuplicate);
            return isDuplicate;
        } catch (Exception e) {
            // 예외 발생 시 로깅하고 false를 반환합니다.
            return false;
        }
    }

    @RequestMapping("/appRegForm")
    public ResponseEntity<Map<String, Object>> regForm(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, Object> appRegForm = commonUtils.returnMapDataObj(requestMap);

            String userBirth = (String) appRegForm.get("user_birth");
            if (userBirth != null) {
                appRegForm.put("user_birth", userBirth.replace("-", ""));
            }

            userService.appRegForm(appRegForm);
            String seq = String.valueOf(appRegForm.get("seq"));

            response.put("message", "Success");
            response.put("seq", seq);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace(); //예외정보 출력
            response.put("message", "\"부득이한 사정으로 인해 현재 서비스 이용이 제한될 수 있습니다.\n\n불편을 드려 죄송합니다. 관리자에게 문의하여 주십시오.\n\n문의번호 : 051-580-5546\"");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /* 대학교 목록 */
    @RequestMapping("/selectRemarksList")
    public List<SelectCodeDTO> selectUnivList(@RequestParam("email") String email) {
        List<SelectCodeDTO> selectList = userService.selectRemarksList(email);
        return selectList;
    }
}
