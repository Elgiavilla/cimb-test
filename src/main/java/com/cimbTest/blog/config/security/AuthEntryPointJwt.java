package com.cimbTest.blog.config.security;

import com.cimbTest.blog.shared.base.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    OutputStream out = response.getOutputStream();
    response.setHeader("Content-Type", "application/json");
    response.setStatus(401);
    ObjectMapper mapper = new ObjectMapper();

    mapper.writeValue(out, CommonResponse.constructResponse(
        "401",
        "Need Header Authorization",
        null,
        null
    ));
    out.flush();
  }

}
