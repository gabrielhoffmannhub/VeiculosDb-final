package com.example.veiculosdb.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LogRequisicoesAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {}

    @Before("controller()")
    public void logAntes(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        log.info(" [ENTRADA] {} {} - Método: {}", request.getMethod(), request.getRequestURI(), joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void logDepois(JoinPoint joinPoint, Object result) {
        log.info(" [SAÍDA] Método: {} - Resultado: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "controller()", throwing = "ex")
    public void logErro(JoinPoint joinPoint, Throwable ex) {
        log.error(" [ERRO] Método: {} - Exceção: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}
