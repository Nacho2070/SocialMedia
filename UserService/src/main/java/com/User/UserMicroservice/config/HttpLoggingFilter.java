package com.User.UserMicroservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        logResponse(requestWrapper, responseWrapper);
    }

    private void logResponse(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) throws IOException {
        // Log Headers from Request
        StringBuilder requestHeaders = new StringBuilder("Request Headers: ");
        requestWrapper.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            String headerValue = requestWrapper.getHeader(headerName);
            requestHeaders.append(headerName).append(": ").append(headerValue).append("; ");
        });
        log.info(requestHeaders.toString());
        log.info("Response {}", new String(responseWrapper.getContentAsByteArray()));
        responseWrapper.copyBodyToResponse();
    }
}
