package com.dage.recruit.api;

import com.dage.recruit.component.FtpComponent;
import com.dage.recruit.component.CommonUtils;
import com.dage.recruit.dto.UserDTO;
import com.dage.recruit.services.FormService;
import com.dage.recruit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

@RestController
@RequestMapping("/api_resume")
@RequiredArgsConstructor
public class FormApiController {

    @Autowired
    FormService formService;

    @Autowired
    UserService userService;

    private final CommonUtils commonUtils;

//    @Autowired
//    FtpService ftpService;

    private final FtpComponent ftpComponent;

    @RequestMapping("/basicInfo")
    public ResponseEntity<String> basicInfoSave(HttpServletRequest request, HttpSession session, @RequestParam(required = false) MultipartFile user_profile) {

        long seq = (long) session.getAttribute("seq");
        UserDTO user = (UserDTO) session.getAttribute("user");

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> map = commonUtils.returnMapDataObj(requestMap);

        map.put("seq", seq);
        commonUtils.setDefaultValues(map, "basicInfo");

        String userCp = (String) map.get("user_cp");
        String userTel = (String) map.get("user_tel");

        commonUtils.processDate(map, "mil_start_date", "mil_end_date", "sdt_y", "sdt_m", "edt_y", "edt_m");

        if (userTel != null && !userTel.equals("")) {
            map.put("user_tel", userTel.replace("-", ""));
        }

        if (userCp != null && !userCp.equals("")) {
            String[] userCpDateParts = userCp.split("-");

            if (userCpDateParts.length == 3) {
                map.put("front", userCpDateParts[0]);
                map.put("middle", userCpDateParts[1]);
                map.put("back", userCpDateParts[2]);
            }
        }

        formService.saveBasicInfo(map);

        if (user_profile != null && !user_profile.isEmpty()) {
            try {
                String basePath = System.getProperty("user.dir") + "/recu/photo/";
                Path directoryPath = Paths.get(basePath);

                String fileName = user.getEmail() + ".jpg";

                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                Path savedFilePath = directoryPath.resolve(fileName);

                if (Files.exists(savedFilePath)) {
                    Files.delete(savedFilePath);
                }

                File destFile = new File(savedFilePath.toUri());
                /* 증명 사진 업로드 */

                user_profile.transferTo(destFile);

                /* FTP 전송(ERP서버) */
                try {
                    ftpComponent.uploadFileFTP("PHOTO/" + fileName, destFile);
                } catch (IOException e) {
                    return new ResponseEntity<>("Error occurred while uploading to FTP: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("File uploaded and saved successfully", HttpStatus.OK);
    }


    @RequestMapping("/education")
    public void educationSave(HttpServletRequest request, HttpSession session) {

        long seq = (long) session.getAttribute("seq");

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> map = commonUtils.returnMapDataObj(requestMap);

        map.put("seq", seq);
        commonUtils.setDefaultValues(map, "education");

        commonUtils.processDate(map, "high_start_date", "high_end_date", "high_sdt_y", "high_sdt_m", "high_edt_y", "high_edt_m");
        commonUtils.processDate(map, "college_start_date", "college_end_date", "col_sdt_y", "col_sdt_m", "col_edt_y", "col_edt_m");
        commonUtils.processDate(map, "univ_start_date", "univ_end_date", "univ_sdt_y", "univ_sdt_m", "univ_edt_y", "univ_edt_m");
        commonUtils.processDate(map, "univ_start_date_2", "univ_end_date_2", "univ_sdt_y_2", "univ_sdt_m_2", "univ_edt_y_2", "univ_edt_m_2");
        commonUtils.processDate(map, "grad_start_date", "grad_end_date", "grad_sdt_y", "grad_sdt_m", "grad_edt_y", "grad_edt_m");
        commonUtils.processDate(map, "grad_start_date_2", "grad_end_date_2", "grad_sdt_y_2", "grad_sdt_m_2", "grad_edt_y_2", "grad_edt_m_2");

        formService.saveEducation(map);
    }

    @RequestMapping("/experience")
    public void experienceSave(HttpServletRequest request, HttpSession session) {

        long seq = (long) session.getAttribute("seq");

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> map = commonUtils.returnMapDataObj(requestMap);

        map.put("seq", seq);
        commonUtils.setDefaultValues(map, "experience");

        for (int i = 1; i <= 5; i++) {
            commonUtils.processDate(map, "co_start_date_" + i, "co_end_date_" + i, "co_sdt_y_" + i, "co_sdt_m_" + i, "co_edt_y_" + i, "co_edt_m_" + i);

            String startDate = (String) map.get("co_start_date_" + i);
            String endDate = (String) map.get("co_end_date_" + i);
            if (startDate != null && !startDate.equals("")) {

                String[] splitSDate = startDate.split("-");
                String[] splitEDate = endDate.split("-");

                int parseSy = Integer.parseInt(splitSDate[0]);
                int parseEy = Integer.parseInt(splitEDate[0]);
                int paresSm = Integer.parseInt(splitSDate[1]);
                int parseEm = Integer.parseInt(splitEDate[1]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate startLocalDate = LocalDate.parse(startDate + "-01", formatter);
                LocalDate endLocalDate = LocalDate.parse(endDate + "-01", formatter);

                Period period = Period.between(startLocalDate, endLocalDate);

                int years = period.getYears();
                int months = period.getMonths();

                if (parseEy > parseSy || (parseEy == parseSy && parseEm >= paresSm)) {
                    // 종료 연도가 시작 연도보다 크거나, 같은 연도이면서 종료 월이 시작 월보다 크거나 같을 경우
                    months = years * 12 + months;
                } else {
                    // 종료 연도가 시작 연도보다 작고, 종료 월이 시작 월보다 작을 경우
                    months = years * 12 + months + 1;
                }

                System.out.println(months);

                map.put("co_month_" + i, months);
            }
        }
        formService.saveExperience(map);
    }

    @RequestMapping("/language")
    public void languageSave(HttpServletRequest request, HttpSession session) {

        long seq = (long) session.getAttribute("seq");

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> map = commonUtils.returnMapDataObj(requestMap);

        map.put("seq", seq);
        commonUtils.setDefaultValues(map, "language");

        for (int i = 1; i <= 5; i++) {
            commonUtils.splitDate(map, "lang_dt_" + i, "lang_dt_y_" + i, "lang_dt_m_" + i, "lang_dt_d_" + i);
        }
        formService.saveLanguage(map);
    }

    @RequestMapping("/certifications")
    public void certificationsSave(HttpServletRequest request, HttpSession session) {

        long seq = (long) session.getAttribute("seq");

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> map = commonUtils.returnMapDataObj(requestMap);

        map.put("seq", seq);
        commonUtils.setDefaultValues(map, "certifications");

        for (int i = 1; i <= 5; i++) {
            commonUtils.splitDate(map, "cert_dt_" + i, "cert_dt_y_" + i, "cert_dt_m_" + i, "cert_dt_d_" + i);
        }
        formService.saveCertifications(map);
    }

    @RequestMapping("/self-introduction")
    public void introSelfSave(HttpServletRequest request, HttpSession session) {

        long seq = (long) session.getAttribute("seq");

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> map = commonUtils.returnMapDataObj(requestMap);

        map.put("seq", seq);

        formService.saveIntroSelf(map);
    }

    @RequestMapping("/upload-file")
    public ResponseEntity<String> uploadFiles(MultipartHttpServletRequest request, HttpSession session) {

        Set<String> ALLOWED_CONTENT_TYPES = new HashSet<String>() {{
            add(MediaType.IMAGE_JPEG_VALUE);
            add(MediaType.IMAGE_PNG_VALUE);
            add(MediaType.IMAGE_GIF_VALUE);
            add("application/pdf");
            add("application/msword");
            add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            add("application/vnd.ms-excel");
            add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            add("application/vnd.ms-powerpoint");
            add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            add("application/x-hwp");
            add("application/x-hwpx");
        }};

        long seq = (long) session.getAttribute("seq");
        UserDTO user = (UserDTO) session.getAttribute("user");

        String remarks = user.getRemarks().replace("/", "-");

        String basePath = System.getProperty("user.dir") + "/recu/doc/" + user.getYyyy() + "-" + user.getCnt() + " " + remarks + "/(" + seq + ")" + user.getEmail();
        Path directoryPath = Paths.get(basePath);

        Iterator<String> fileNames = request.getFileNames();
        List<String> uploadedFiles = new ArrayList<>();

        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile file = request.getFile(fileName);

            System.out.println("file : " + file);

            if (file != null && !file.isEmpty()) {
                fileName = file.getOriginalFilename();
                Path savedFilePath = directoryPath.resolve(fileName);

                System.out.println("ORG : " + file.getOriginalFilename());
                File destFile = new File(savedFilePath.toUri());

                System.out.println("DDDDDDDDDDDD " + destFile);

                if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
                    return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
                }

                try {
                    if (!Files.exists(directoryPath)) {
                        Files.createDirectories(directoryPath);
                    }

                    file.transferTo(destFile);
                    uploadedFiles.add(destFile.getAbsolutePath());

                    ftpComponent.uploadFileFTP("doc/" + user.getYyyy() + "-" + user.getCnt() + " " + remarks + "/(" + seq + ")" + user.getEmail() + "/" + fileName, destFile);

                } catch (IOException e) {
                    return new ResponseEntity<>("Error occurred while uploading the file.", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
        }

        return new ResponseEntity<>("Files successfully uploaded.", HttpStatus.OK);
    }


    @RequestMapping("/file_delete")
    public ResponseEntity<String> deleteFile(HttpServletRequest request, HttpSession session) {

        String filename = request.getParameter("file");
        String basePath = System.getProperty("user.dir") + "/recu/doc/" + request.getParameter("yyyy") + "-" + request.getParameter("cnt") + " " + request.getParameter("remarks").replace("/", "-") + "/(" + request.getParameter("seq") + ")" + request.getParameter("email");
        File file = new File(basePath, filename);

        if (file.exists() && file.delete()) {
            try {
                ftpComponent.deleteFileFTP("doc/" + request.getParameter("yyyy") + "-" + request.getParameter("cnt") + " " + request.getParameter("remarks").replace("/", "-") + "/(" + request.getParameter("seq") + ")" + request.getParameter("email") + "/" + filename);
             } catch (IOException e) {
                e.printStackTrace();
            }
//
            return new ResponseEntity<>("파일이 성공적으로 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("파일 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @RequestMapping("/delete")
    public ResponseEntity<String> appDelete(HttpServletRequest request, HttpSession session) {
        long seq = Integer.valueOf(request.getParameter("seq"));
        String email = request.getParameter("email");

        String imgPath = System.getProperty("user.dir") + "/recu/photo/";
        String imgFileName = email + ".jpg";

        String uploadFilePath = System.getProperty("user.dir") + "/recu/doc/" + request.getParameter("yyyy") + "-" + request.getParameter("cnt") + " " + request.getParameter("remarks").replace("/", "-") + "/(" + request.getParameter("seq") + ")" + email;

//        File imgfile = new File(imgPath, imgFileName);
        File uploadfile = new File(uploadFilePath);

        try {
//            imgfile.delete();
//            ftpComponent.deleteFileFTP("PHOTO/" + imgFileName);

            File[] files = uploadfile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            uploadfile.delete();
            ftpComponent.deleteDirectoryFTP("doc/" + request.getParameter("yyyy") + "-" + request.getParameter("cnt") + " " + request.getParameter("remarks").replace("/", "-") + "/(" + request.getParameter("seq") + ")" + request.getParameter("email"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        formService.deleteForm(seq);

        List<UserDTO> updatedUserSession = userService.getUserList(email);
        session.setAttribute("userSession", updatedUserSession);

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

}
