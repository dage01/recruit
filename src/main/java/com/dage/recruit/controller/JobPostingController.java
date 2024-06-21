package com.dage.recruit.controller;

import com.dage.recruit.dto.JobPostingDTO;
import com.dage.recruit.services.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class JobPostingController {

    @Autowired
    JobPostingService jobPostingService;

    @GetMapping({"/", "/job-posting/list"})
    public String jobPostingList(Model model) {

        List<JobPostingDTO> jobPostingList = jobPostingService.jobPostingList();

        model.addAttribute("jobPostingList", jobPostingList);
        return "jobPosting/job_posting_list";
    }

    @RequestMapping("/job-posting/fill-form")
    public String fillAppForm(HttpSession session,
                              HttpServletRequest request,
                              Model model) {
        session.removeAttribute("authenticatedEmail");

        String yyyy = request.getParameter("yyyy");
        String degrees = request.getParameter("degrees");

        JobPostingDTO jobPostingDTO = jobPostingService.selectJobPosting(yyyy, degrees);



        session.setAttribute("jobPostingDTO", jobPostingDTO);
        model.addAttribute("jobPostingDTO", jobPostingDTO);

        return "jobPosting/fill_app_form";
    }

}
