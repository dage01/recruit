package com.dage.recruit.interceptor;


import com.dage.recruit.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserSessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();

        List<UserDTO> userSession = (List<UserDTO>) session.getAttribute("userSession");
        if (userSession != null) {
            request.setAttribute("userSession", userSession.get(0));
        }

        return true;
    }
}
