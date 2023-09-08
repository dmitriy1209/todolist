package com.digital.uwp;

import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class TestUtil {

    @SneakyThrows
    public static String getContentAsString(String fileLocation) {
        return StreamUtils.copyToString(new ClassPathResource(fileLocation).getInputStream(), 
            StandardCharsets.UTF_8);
    }

}
