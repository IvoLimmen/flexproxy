package org.limmen.flexproxy.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Result {

  default boolean canHandle(String match, String fullUrl, String url) {
    return match.equalsIgnoreCase("equals") && fullUrl.equalsIgnoreCase(url)
        || match.equalsIgnoreCase("contains") && fullUrl.contains(url)
        || match.equalsIgnoreCase("endswith") && fullUrl.endsWith(url)
        || match.equalsIgnoreCase("startswith") && fullUrl.startsWith(url);
  }

  void handleResult(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
