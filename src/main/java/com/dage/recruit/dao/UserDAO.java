package com.dage.recruit.dao;

import com.dage.recruit.dto.FormDTO;
import com.dage.recruit.dto.UserDTO;
import com.dage.recruit.dto.SelectCodeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDAO {

    /* 지원자 정보 */
    UserDTO appUser(String yyyy, String degrees, String user_email, long seq);

    /* 지원자 리스트 */
    List<UserDTO> getUserList(String user_email);

    /* 미리보기 */
    FormDTO formPreview(long seq);

    /* 지원 공고 목록 */
    List<SelectCodeDTO> selectRemarksList(String email);

    /* 비밀번호 검증 */
    Integer verifyPassword(String sel_remarks, String req_email, String get_password, String get_name, String get_birth);

    /* 비밀번호 변경 */
    void changePassword(String sel_remarks, String req_email, String new_password);



    /* API */
    /* 지원서 작성 */
    void appRegForm(Map<String, Object> map);

}
