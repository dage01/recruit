package com.dage.recruit.services;

import com.dage.recruit.dao.CommCodeDAO;
import com.dage.recruit.dto.SelectCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommCodeService {

    @Autowired
    CommCodeDAO commCodeDAO;

    /* API */
    /* 지원직무코드 목록*/
    public List<SelectCodeDTO> selectJobCodeList() {
        return commCodeDAO.selectJobCodeList();
    }

    /* 전문대학교 목록*/
    public List<SelectCodeDTO> selectCollegeList() {
        return commCodeDAO.selectCollegeList();
    }

    /* 대학교 목록*/
    public List<SelectCodeDTO> selectUnivList() {
        return commCodeDAO.selectUnivList();
    }

}
