package com.dage.recruit.controller;


import com.dage.recruit.dto.*;
import com.dage.recruit.services.FormService;
import com.dage.recruit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/resume")
public class FormController {

//    @Value("${file.upload-dir}")
//    String uploadDir;
//
//    @Value("${img.upload.path}")
//    String userImg;

    String uploadDir;
    String userImg;

    @Autowired
    UserService userService;
    @Autowired
    FormService formService;

    /* 기본사항 */
    @RequestMapping("/basic-info")
    public String basicInfo(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        String seqParam = request.getParameter("seq");

        long setSeq = 0; // 기본값 설정

        if (seqParam != null && !seqParam.isEmpty()) {
            try {
                setSeq = Long.parseLong(seqParam);
                session.setAttribute("seq", setSeq);
            } catch (NumberFormatException e) {
                // 유효하지 않은 숫자 형식일 경우 예외 처리
                e.printStackTrace();
            }
        }

        long seq = (long) session.getAttribute("seq");

        List<UserDTO> userSession = (List<UserDTO>) session.getAttribute("userSession");

        if (userSession != null) {
            boolean found = false;
            for (UserDTO user : userSession) {
                if (user.getSeq() == seq) {

//                    session.setAttribute("app_no", user.getAppNo());
                    session.setAttribute("remarks", user.getRemarks());

                    model.addAttribute("user", user);

                    session.setAttribute("user", user);
                    model.addAttribute("appJob", user.getAppjobName());

                    System.out.println(user);
                    found = true;
                    break; // 매칭되는 지원서를 찾으면 반복문 탈출
                }
            }
            if (!found) {
                UserDTO appUser = userService.getUser(null, null, null, seq);
                session.setAttribute("remarks", appUser.getRemarks());
                model.addAttribute("user", appUser);
                session.setAttribute("user", appUser);
                model.addAttribute("appJob", appUser.getAppjobName());

                System.out.println(appUser);

                List<UserDTO> updatedUserSession = userService.getUserList(appUser.getEmail());
                session.setAttribute("userSession", updatedUserSession);
            }
        } else {
            UserDTO appUser = userService.getUser(null, null, null, seq);

            session.setAttribute("remarks", appUser.getRemarks());

            model.addAttribute("user", appUser);

            session.setAttribute("user", appUser);
                model.addAttribute("appJob", appUser.getAppjobName());
        }

        /* 지원 정보 */
        InfoBasicFormDTO infoBasicFormDTO = formService.basicInfo(seq);

        model.addAttribute("infoBasicFormDTO", infoBasicFormDTO);
        return "resume/basic_info";
    }

    /* 학력사항 */
    @GetMapping("/education")
    public String education(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");
        String remarks = (String) session.getAttribute("remarks");

        EducationFormDTO educationFormDTO = formService.education(seq);

        model.addAttribute("remarks", remarks);
        model.addAttribute("educationFormDTO", educationFormDTO);

        return "resume/education";
    }

    /* 경력사항 */
    @GetMapping("/experience")
    public String experience(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");
        String remarks = (String) session.getAttribute("remarks");

        ExperienceFormDTO experienceFormDTO = formService.experience(seq);

        model.addAttribute("remarks", remarks);
        model.addAttribute("experienceFormDTO", experienceFormDTO);

        return "resume/experience";
    }

    /* 외국어 */
    @GetMapping("/language")
    public String language(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");
        String remarks = (String) session.getAttribute("remarks");

        LanguageFormDTO languageFormDTO = formService.language(seq);

        model.addAttribute("remarks", remarks);
        model.addAttribute("languageFormDTO", languageFormDTO);

        return "resume/language";
    }

    /* 자격사항 */
    @GetMapping("/certifications")
    public String certifications(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");
        String remarks = (String) session.getAttribute("remarks");

        CertFormDTO certFormDTO = formService.certifications(seq);

        model.addAttribute("remarks", remarks);
        model.addAttribute("certFormDTO", certFormDTO);

        return "resume/certifications";
    }

    /* 자격사항 */
    @GetMapping("/self-introduction")
    public String introSelf(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");
        String remarks = (String) session.getAttribute("remarks");

        IntroSelfFormDTO introSelfFormDTO = formService.introSelf(seq);

        model.addAttribute("remarks", remarks);
        model.addAttribute("introSelfFormDTO", introSelfFormDTO);

        return "resume/self-introduction";
    }

    /* 제출서류 */
    @GetMapping("/upload-file")
    public String upload_file(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");
        UserDTO user = (UserDTO) session.getAttribute("user");

        String remarks = user.getRemarks().replace("/", "-");
        String basePath = System.getProperty("user.dir") + "recu/doc/" + user.getYyyy() + "-" + user.getCnt() + " " + remarks + "/(" + seq + ")" + user.getEmail();


        System.out.println("basePATH : " + basePath);
        File folder = new File(basePath);
        File[] getFiles = folder.listFiles(); // 디렉토리 내의 파일 목록을 가져옴

        List<String> fileList = new ArrayList<>();
        if (getFiles != null) {
            for (File file : getFiles) {
                if (file.isFile()) {
                    fileList.add(file.getName()); // 파일 목록에 파일 이름 추가
                }
            }
        }

        model.addAttribute("remarks", user.getRemarks());
        model.addAttribute("user", user);
        model.addAttribute("fileList", fileList); // 파일 목록을 모델에 설정


        return "resume/upload-file";
    }


    @RequestMapping("/submit-application")
    public String submitApp(HttpServletRequest request, HttpSession session, Model model) {

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        long seq = (long) session.getAttribute("seq");

        UserDTO user = (UserDTO) session.getAttribute("user");
        String remarks = user.getRemarks().replace("/", "-");

        // 사용자 업로드 디렉토리 내에 seq로 명명된 파일 확인
        String basePath = System.getProperty("user.dir") + "recu/doc/" + user.getYyyy() + "-" + user.getCnt() + " " + remarks + "/(" + seq + ")" + user.getEmail();

        System.out.println("basePATH : " + basePath);
        File folder = new File(basePath);
        File[] getFiles = folder.listFiles(); // 디렉토리 내의 파일 목록을 가져옴

        List<String> fileList = new ArrayList<>();
        if (getFiles != null) {
            for (File file : getFiles) {
                if (file.isFile()) {
                    fileList.add(file.getName()); // 파일 목록에 파일 이름 추가
                }
            }
        }

        FormDTO formDTO = formService.formPreview(seq);

        model.addAttribute("formDTO", formDTO);
        model.addAttribute("fileList", fileList);

        return "resume/submit-application";

    }

}

