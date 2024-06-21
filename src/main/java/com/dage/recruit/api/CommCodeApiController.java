package com.dage.recruit.api;

import com.dage.recruit.dto.SelectCodeDTO;
import com.dage.recruit.services.CommCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api_comm_code")
public class CommCodeApiController {
    @Autowired
    CommCodeService commCodeService;

    /*지원직무 목록*/
    @RequestMapping("/selectJobCode")
    public List<SelectCodeDTO> selectJobCodeList() {
        List<SelectCodeDTO> list = commCodeService.selectJobCodeList();
        return list;
    }

    /* 전문대학교 목록 */
    @RequestMapping("/selectCollegeCode")
    public List<SelectCodeDTO> selectCollegeList() {
        List<SelectCodeDTO> collegeList = commCodeService.selectCollegeList();
        return collegeList;
    }

    /* 대학교 목록 */
    @RequestMapping("/selectUnivCode")
    public List<SelectCodeDTO> selectUnivList() {
        List<SelectCodeDTO> univList = commCodeService.selectUnivList();
        return univList;
    }

}
