package com.dage.recruit.dao;

import com.dage.recruit.dto.SelectCodeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommCodeDAO {

    // 지원직무코드 목록
    List<SelectCodeDTO> selectJobCodeList();


    /* 전문대학교 목록 */
    List<SelectCodeDTO> selectCollegeList();


    /* 전문대학교 목록 */
    List<SelectCodeDTO> selectUnivList();
}
