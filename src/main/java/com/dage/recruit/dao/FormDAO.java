package com.dage.recruit.dao;

import com.dage.recruit.dto.*;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FormDAO {

    /* 기본사항 */
    InfoBasicFormDTO basicInfo(long seq);

    /* 학력사항 */
    EducationFormDTO education(long seq);

    /* 경력사항 */
    ExperienceFormDTO experience(long seq);


    /* 외국어 */
    LanguageFormDTO language(long seq);

    /* 자격사항 */
    CertFormDTO certifications(long seq);

    /* 자기소개 */
    IntroSelfFormDTO introSelf(long seq);


    /* API */
    /* 기본사항 */
    void saveBasicInfo(Map<String, Object> map);

    /* 학력사항 */
    void saveEducation(Map<String, Object> map);

    /* 경력사항 */
    void saveExperience(Map<String, Object> map);

    /* 외국어 */
    void saveLanguage(Map<String, Object> map);

    /* 자격사항 */
    void saveCertifications(Map<String, Object> map);

    /* 자기소개 */
    void saveIntroSelf(Map<String, Object> map);

    /* 지원서 삭제 */
    void deleteForm(long seq);


}
