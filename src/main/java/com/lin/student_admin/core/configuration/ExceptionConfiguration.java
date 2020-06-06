/**
 * @作者 努力中的杨先生
 * @描述 错误码注入 map形式
 * @创建时间 2020-06-06 11:45
 */
package com.lin.student_admin.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "lin")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
public class ExceptionConfiguration {
    private Map<Integer, String>codes;

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }
    public String getMessage(int code){
        String message =codes.get(code);
        return message;
    }
}
