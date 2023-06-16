package com.cimbTest.blog.config.documentation;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@SecurityRequirement(name = DocumentationConstant.tagAuthenticationHeader)
@SecurityScheme(
    name = DocumentationConstant.tagAuthenticationHeader,
    type = SecuritySchemeType.HTTP,
    bearerFormat = DocumentationConstant.tagAuthenticationFormat,
    scheme = DocumentationConstant.getTagAuthenticationType
)
public class SwaggerConfig extends WebMvcConfigurationSupport {

  @Bean
  public GroupedOpenApi authDocumentation(){
    return GroupedOpenApi.builder()
        .group(DocumentationConstant.AUTH_GROUP_NAME)
        .pathsToMatch("/auth/**")
        .build();
  }

  @Bean
  public GroupedOpenApi userDocumentation(){
    return GroupedOpenApi.builder()
        .group(DocumentationConstant.USER_GROUP_NAME)
        .pathsToMatch("/user/**")
        .build();
  }

  @Bean
  public GroupedOpenApi blogDocumentation(){
    return GroupedOpenApi.builder()
        .group(DocumentationConstant.BLOG_GROUP_NAME)
        .pathsToMatch("/blog/**")
        .build();
  }
}
