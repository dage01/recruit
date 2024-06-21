package com.dage.recruit.services;

import com.dage.recruit.dto.*;
import com.dage.recruit.dao.FormDAO;
import com.dage.recruit.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FormService {

    @Autowired
    FormDAO formDAO;

    @Autowired
    UserDAO userDAO;

    /* 기본사항 */
    public InfoBasicFormDTO basicInfo(long seq) {
        return formDAO.basicInfo(seq);
    }
    public void saveBasicInfo(Map<String, Object> map) {
        formDAO.saveBasicInfo(map);
    }

    /* 학력사항 */
    public EducationFormDTO education(long seq) {
        return formDAO.education(seq);
    }
    public void saveEducation(Map<String, Object> map) {
        formDAO.saveEducation(map);
    }

    /* 경력사항 */
    public ExperienceFormDTO experience(long seq) {
        return formDAO.experience(seq);
    }
    public void saveExperience(Map<String, Object> map) {
        formDAO.saveExperience(map);
    }

    /* 외국어 */
    public LanguageFormDTO language(long seq) {
        return formDAO.language(seq);
    }
    public void saveLanguage(Map<String, Object> map) {
        formDAO.saveLanguage(map);
    }

    /* 자격사항 */
    public CertFormDTO certifications(long seq) {
        return formDAO.certifications(seq);
    }
    public void saveCertifications(Map<String, Object> map) {
        formDAO.saveCertifications(map);
    }

    /* 자기소개 */
    public IntroSelfFormDTO introSelf(long seq) {
        return formDAO.introSelf(seq);
    }
    public void saveIntroSelf(Map<String, Object> map) {
        formDAO.saveIntroSelf(map);
    }


    /* 미리보기 */
    public FormDTO formPreview(long seq) {
        return userDAO.formPreview(seq);
    }


    /* 지원서 삭제 */
    public void deleteForm(long seq) {
        formDAO.deleteForm(seq);
    }
}
