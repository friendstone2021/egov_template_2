package com.egov.template.config.error;

import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

@Slf4j
public class ExcepHndlr implements ExceptionHandler {

	@Override
	public void occur(Exception ex, String packageName) {
		log.debug("##### ExcepHndlr Run...");
	}

}
