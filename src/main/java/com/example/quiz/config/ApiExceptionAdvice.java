package com.example.quiz.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestControllerAdvice
public @interface ApiExceptionAdvice {
    @AliasFor(annotation = RestControllerAdvice.class, attribute = "basePackages")
    String[] basePackages() default {};
}
