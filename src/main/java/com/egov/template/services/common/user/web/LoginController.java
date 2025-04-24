/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * LoginController.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * ${programDescription}
 * @author ${author}
 * @since ${since}
 * @version 1.0
 *
 * <pre>
 * == 개정이력(Modification Information)==
 * 수정일                 수정자          수정내용
 * -------      -------   ----------------------------
 * ${since}   ${author}   최초 작성
 *
 * </pre>
 */
@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/loginPage.do")
    public ModelAndView loginPage(HttpServletRequest request) {
        request.getSession().removeAttribute("loginInfo");

        ModelAndView mv = new ModelAndView();
		String message = request.getParameter("message");
		if (message!=null){
            mv.addObject("message", message);
        }
		String username = request.getParameter("username");	// 비밀번호 변경 시 필요
		if (username!=null){
            mv.addObject("username", username);
        }

		mv.setViewName("loginLayout");
		return mv;
    }

}
