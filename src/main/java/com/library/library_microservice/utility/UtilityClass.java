package com.library.library_microservice.utility;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UtilityClass {
    public static int changeNullToEmptyString(Integer str) {
        return (str == null) ? 0 : str;
    }
}
