/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * FormValidatorController.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.validator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
public class FormValidatorController {

    @RequestMapping("/validator.do")
    public String validator() {
        return "validator";
    }

}
