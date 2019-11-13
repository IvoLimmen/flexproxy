package org.limmen.flexproxy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyServlet extends HttpServlet {

  private static final long serialVersionUID = -1;  
  private final HttpClient client;
  private final String url;
  private final String mountpoint;

  @Builder
  public ProxyServlet(final String url, final String mountpoint) {
    this.url = url;
    client = HttpClient.newBuilder()
        .sslContext(SSLContextProvider.getSSLContext())
        .connectTimeout(Duration.ofSeconds(30))
        .build();
    this.mountpoint = mountpoint;
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    StringBuilder url = new StringBuilder();

    url.append(this.url);
    url.append("/");
    url.append(this.mountpoint);
    if (req.getPathInfo() != null) {
      url.append(req.getPathInfo());
    }      
    if (req.getQueryString() != null) {
      url.append("?").append(req.getQueryString());
    }

    log.info("{} {}", req.getMethod(), url);

    List<String> headers = new ArrayList<>();
    Enumeration<String> reqHeaders = req.getHeaderNames();
    while (reqHeaders.hasMoreElements()) {
      String key = reqHeaders.nextElement();
      if (isAllowedHeaderKey(key)) {
        headers.add(key);
        headers.add(req.getHeader(key));  
      }
    }

    try {
      BodyPublisher bodyPublisher = null;
      if (req.getMethod().equalsIgnoreCase("GET") || req.getMethod().equalsIgnoreCase("DELETE")) {
        bodyPublisher = BodyPublishers.noBody();
      } else {
        bodyPublisher = BodyPublishers.ofString(req.getReader().lines().collect(Collectors.joining()));
      }

      HttpRequest proxyReq = HttpRequest.newBuilder()      
          .method(req.getMethod(), bodyPublisher)
          .headers(headers.toArray(new String[headers.size()]))
          .uri(URI.create(url.toString()))
          .build();
      
      HttpResponse<String> proxyRes = client.send(proxyReq, BodyHandlers.ofString());

      proxyRes.headers().map().entrySet().forEach(entry -> {        
        String value = entry.getValue().iterator().next();
        res.setHeader(entry.getKey(), value);
      });    
      res.setStatus(proxyRes.statusCode());
      res.getWriter().write(proxyRes.body());
      res.getWriter().flush();

    } catch (IOException | InterruptedException e) {
      throw new ServletException(e);
    }
  }

  public boolean isAllowedHeaderKey(String key) {
    // Host is not allowed to be set as the webserver sets it
    return !key.equalsIgnoreCase("host");
  }
}
