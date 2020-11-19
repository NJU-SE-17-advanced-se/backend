package org.njuse17advancedse.entitypaper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final SecurityProblemSupport problemSupport;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Other security-related configuration
    http
      .exceptionHandling()
      .authenticationEntryPoint(problemSupport)
      .accessDeniedHandler(problemSupport);
    http.csrf().disable();
  }

  public SecurityConfig(SecurityProblemSupport problemSupport) {
    this.problemSupport = problemSupport;
  }
}
