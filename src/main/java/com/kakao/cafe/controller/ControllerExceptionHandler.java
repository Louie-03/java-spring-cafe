package com.kakao.cafe.controller;

import com.kakao.cafe.controller.dto.ArticleSaveDto;
import com.kakao.cafe.controller.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView returnFormAndSendExceptionMessage(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException exception) throws IOException {
        logger.error("IllegalArgumentException={}", exception.getMessage());
        ModelAndView modelAndView = createModelAndView(exception, request);
        if (!modelAndView.hasView()) {
            response.sendError(404, "페이지를 찾을 수 없습니다");
        }
        return modelAndView;
    }

    private ModelAndView createModelAndView(IllegalArgumentException exception,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getMessage());

        String requestURI = request.getRequestURI();
        if (requestURI.equals("/users")) {
            UserDto userDto = changeParamsToUserDto(request);
            modelAndView.addObject(userDto);
            modelAndView.setViewName("user/form");
        }
        if (requestURI.equals("/questions")) {
            ArticleSaveDto articleSaveDto = changeParamsToArticleSaveDto(request);
            modelAndView.addObject(articleSaveDto);
            modelAndView.setViewName("qna/form");
        }
        return modelAndView;
    }

    private ArticleSaveDto changeParamsToArticleSaveDto(HttpServletRequest request) {
        ArticleSaveDto articleSaveDto = new ArticleSaveDto();
        articleSaveDto.setUserId(request.getParameter("userId"));
        articleSaveDto.setTitle(request.getParameter("title"));
        articleSaveDto.setContents(request.getParameter("contents"));
        return articleSaveDto;
    }

    private UserDto changeParamsToUserDto(HttpServletRequest request) {
        UserDto userDto = new UserDto();
        userDto.setUserId(request.getParameter("userId"));
        userDto.setPassword(request.getParameter("password"));
        userDto.setName(request.getParameter("name"));
        userDto.setEmail(request.getParameter("email"));
        return userDto;
    }
}