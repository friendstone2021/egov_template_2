/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * UserController.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.user.web;

import com.egov.template.common.util.EgovMessageSource;
import com.egov.template.services.common.user.service.UserService;
import com.egov.template.services.common.user.vo.TbCmUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * ${programDescription}
 *
 * @author ${author}
 * @version 1.0
 *
 * <pre>
 * == 개정이력(Modification Information)==
 * 수정일                 수정자          수정내용
 * -------      -------   ----------------------------
 * ${since}   ${author}   최초 작성
 *
 * </pre>
 * @since ${since}
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    EgovMessageSource egovMessageSource;
    @Autowired
    private UserService userService;

    /**
     * Handles the request to display the user registration form.
     *
     * @return the path to the user registration form view.
     */
    @RequestMapping("/userJoinForm.do")
    public String userJoinForm() {
        return "/user/userJoinForm";
    }

    /**
     * Handles requests to check for duplicate user IDs during user registration.
     * This method prepares a JSON view as the response.
     *
     * @return a ModelAndView object configured with a "jsonView" view name.
     */
    @RequestMapping("/selectJoinUserIdDplct.do")
    public ModelAndView selectJoinUserIdDplct(@RequestParam("userId") String userId) {
        ModelAndView mv = new ModelAndView("jsonView");

        boolean isDplct = userService.isUserIdDplct(userId);
        if (isDplct) {
            mv.addObject("status", "FAIL");
            mv.addObject("message", egovMessageSource.getMessage("cm.um.userManage.userIdDplct"));    // 이미 사용중인 아이디 입니다.
        } else {
            mv.addObject("status", "SUCCESS");
            mv.addObject("message", egovMessageSource.getMessage("cm.um.userManage.userIdOk"));    // 사용 가능한 아이디 입니다.
        }

        return mv;
    }

    /**
     * Handles the user registration process.
     * This method inserts user information into the system and prepares a JSON response indicating the result.
     *
     * @param cmTbUser the user information to be registered, validated using @Valid annotation
     * @return a ModelAndView object configured with a "jsonView" view name, containing the status and message of the operation
     */
    @RequestMapping("/insertUserJoin.do")
    public ModelAndView insertUserJoin(@ModelAttribute @Valid TbCmUserVO cmTbUser) {
        ModelAndView mv = new ModelAndView("jsonView");

        if (cmTbUser != null) {
            userService.insertUser(cmTbUser);
            mv.addObject("status", "SUCCESS");
            mv.addObject("message", egovMessageSource.getMessage("li.li.userJoin.success"));    //회원가입이 완료되었습니다. 로그인을 해주세요.
        } else {
            mv.addObject("status", "FAIL");
        }

        return mv;
    }
}
