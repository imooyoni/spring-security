package com.moonie.authorization.util;

import jakarta.servlet.*;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class UniqueKeyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 작업이 필요하면 구현
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 고유 키 생성 (UUID)
//        String uniqueId = UUID.randomUUID().toString();
        String uniqueId = generateRandomString(13);

        // MDC에 고유 키 저장
        ThreadContext.put("uniqueId", uniqueId);

        try {
            // 다음 필터나 서블릿 실행
            chain.doFilter(request, response);
        } finally {
            // 요청이 끝난 후 MDC에서 값 제거
            ThreadContext.remove("uniqueId");
        }
    }

    @Override
    public void destroy() {
        // 필터가 파괴될 때 필요하면 구현
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}