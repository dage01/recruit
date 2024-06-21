package com.dage.recruit.dao;

import com.dage.recruit.dto.JobPostingDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingDAO {

    // 전체 공고 목록
    List<JobPostingDTO> jobPostingList();

    // 게시여부 자동 변경
    void ChangeOpenTag(String yyyy, String degrees, String remarks);

    // 공고 선택
    JobPostingDTO selectJobPosting(String yyyy, String degrees);

}
