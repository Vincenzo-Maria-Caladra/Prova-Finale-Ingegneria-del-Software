package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
    	LOGGER.error(throwable.getMessage());
        LOGGER.error("Un eccezione è stata attivata durante l'esecuzione della web app!", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Errore sconosciuto.");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}