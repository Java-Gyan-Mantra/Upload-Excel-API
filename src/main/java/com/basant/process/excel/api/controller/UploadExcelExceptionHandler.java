package com.basant.process.excel.api.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class UploadExcelExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handleError1(Exception e,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", e.getCause()
				.getMessage());
		return "redirect:/uploadStatus";

	}
}
