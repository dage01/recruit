package com.dage.recruit.services;

import com.dage.recruit.dto.JobPostingDTO;
import com.dage.recruit.dao.JobPostingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JobPostingService {

    @Autowired
    JobPostingDAO jobPostingDAO;

    /* 전체 공고 목록 조회*/
    public List<JobPostingDTO> jobPostingList() {

        List<JobPostingDTO> jobPostingList = jobPostingDAO.jobPostingList();

        for (JobPostingDTO dto : jobPostingList) {
            String getEndDate = dto.getEndDate();
            if (getEndDate != null) {
                try {
                    // 문자열을 Date 객체로 변환
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                    Date endDate = dateFormat.parse(getEndDate);

                    // Date 객체를 LocalDateTime으로 변환
                    LocalDateTime endLocalDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    // 현재 일시
                    LocalDateTime currentDateTime = LocalDateTime.now();

                    // 현재 시간 테스트
//                    LocalDateTime currentDateTime = LocalDateTime.of(2023, 8, 31, 20, 01);

                    // 남은 일수 계산
                    long dayRemaining = ChronoUnit.DAYS.between(currentDateTime, endLocalDateTime);

                    // 현재 시간과 분을 초과했는지 여부 확인
                    boolean isPast = currentDateTime.isAfter(endLocalDateTime);

                    // 현재 시간과 분을 지났다면
                    if (isPast) {
//                        jobPostingDAO.ChangeOpenTag(dto.getYyyy(), dto.getDegrees(), dto.getRemarks());
                    }

                    dto.setClosingDay(dayRemaining);

//                    System.out.println("currentDateTime: " + currentDateTime);
//                    System.out.println("Date: " + getEndDate);
//                    System.out.println("D-Day: " + dayRemaining);
//                    System.out.println("************");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                dto.setClosingDay(0);
            }
        }


        return jobPostingList;
    }

    /* 선택 공고 지원서 작성 화면 */
    public JobPostingDTO selectJobPosting(String yyyy, String degrees) {

        return jobPostingDAO.selectJobPosting(yyyy, degrees);
    }

}
