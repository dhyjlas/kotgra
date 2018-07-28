package com.example.kotgra;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class KotgraAppInitializer extends SpringBootServletInitializer {
        /**
         *重写configure
         * @param builder
         * @return
         */
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(KotgraApplication.class);
        }
}
