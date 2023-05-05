package com.evaluation.tenpo.filter;

import com.evaluation.tenpo.model.AuditRequest;
import com.evaluation.tenpo.service.AuditRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class AuditRequestFilter extends OncePerRequestFilter {
    
    @Autowired
    private AuditRequestService auditRequestService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestRemoteAddress = request.getRemoteAddr();
        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        int requestStatus = response.getStatus();
        String requestBody = new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

    var auditRequest =
        AuditRequest.builder()
            .requestMethod(requestMethod)
            .requestUri(requestUri)
            .requestBody(requestBody)
            .responseBody(responseBody)
            .timestamp(new Date())
                .responseStatus(requestStatus)
                .remoteAddress(requestRemoteAddress)
            .build();
        auditRequestService.save(auditRequest);
        
        wrappedResponse.copyBodyToResponse();
    }
}
