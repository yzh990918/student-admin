/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 11:35
 */
package com.lin.student_admin.core;

import com.lin.student_admin.core.configuration.ExceptionConfiguration;
import com.lin.student_admin.exception.HttpException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalException {
    @Autowired

    private ExceptionConfiguration exceptionConfiguration;
    // 未知异常

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyException handleException(HttpServletRequest req, Exception e){
        // 当有checkedException异常抛出,加上这两个方法,会在全局捕捉到异常,并运行方法
        String url = req.getRequestURI();
        String method =req.getMethod();
        UnifyException message = new UnifyException(999,"服务器异常",method+" "+url);
        return message;
    }

    //已知异常

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyException> handleHttpException(HttpServletRequest req, HttpException e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        UnifyException message = new UnifyException(e.getCode(),exceptionConfiguration.getMessage(e.getCode()),method+" "+url);
        // 设置已知异常的Httpstatus 利用泛型设置
        // 泛型方法需要三个参数 ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message,header,httpStatus);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyException> r = new ResponseEntity<>(message,header,httpStatus);
        return r;
    }

    // 校验参数异常(body形式)

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyException hanldeMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        // 拿到所有的验证异常
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String messages = format(errors);
        return new UnifyException(10001,messages,method+" "+url);
    }
    private String format(List<ObjectError> errors){
        // 获取错误信息数组 将数组拼接成字符串
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error-> errorMsg.append(error.getDefaultMessage()).append(";"));;
        return errorMsg.toString();
    }

    // 校验参数异常(在url显示)

    @ExceptionHandler(value= ConstraintViolationException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyException handlerConstrainException(HttpServletRequest req, ConstraintViolationException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();

        //这里如果有多个错误 是拼接好的，但是如果需要特殊处理，就不能用它了
        String message = e.getMessage();
        /*
        // 自定义错误信息时
        for (ConstraintViolation error:e.getConstraintViolations()) {
            ConstraintViolation a = error;
        }
        */
        return new UnifyException(10001,message,method + " " + requestUrl);
    }
}
