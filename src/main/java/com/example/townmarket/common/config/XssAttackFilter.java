package com.example.townmarket.common.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class XssAttackFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException, IOException {
    filterChain.doFilter(new RequestWrapper((HttpServletRequest) servletRequest), servletResponse);
  }

  @Override
  public void destroy() {

  }

  private class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
      super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
      String[] values = super.getParameterValues(parameter);
      if (values == null) {
        return null;
      }
      int count = values.length;
      String[] encodedValues = new String[count];
      for (int i = 0; i < count; i++) {
        encodedValues[i] = cleanXSS(values[i]);
      }
      return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
      String value = super.getParameter(parameter);
      if (value == null) {
        return null;
      }
      return cleanXSS(value);
    }

    @Override
    public String getHeader(String name) {
      String value = super.getHeader(name);
      if (value == null) {
        return null;
      }
      return cleanXSS(value);
    }

    private String cleanXSS(String value) {
      value = value.replaceAll("&", "&amp;");
      value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
      value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
      value = value.replaceAll("/", "&#x2F;");
      value = value.replaceAll("'", "&#x27;");
      value = value.replaceAll("\"", "&quot;");
      return value;
    }
  }
}
