package org.limmen.flexproxy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.limmen.flexproxy.domain.Service;
import org.limmen.flexproxy.domain.Endpoint;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EndpointWrapperServlet extends HttpServlet {

  private static final long serialVersionUID = -1;

  private final Service service;

  private final ProxyServlet proxyServlet;

  @Builder
  public EndpointWrapperServlet(final Service service) {
    this.service = service;
    this.proxyServlet = ProxyServlet.builder()
        .url(service.getProxyUrl())
        .mountpoint(service.getMountpoint())
        .debug(service.isDebug())
        .build();
    log.info("Service '{}' contains {} endpoints", this.service.getName(), this.service.getEndpoints().size());
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    StringBuilder fullUrl = new StringBuilder();
    if (req.getPathInfo() != null) {
      fullUrl.append(req.getPathInfo());
    }
    if (req.getQueryString() != null) {
      fullUrl.append("?").append(req.getQueryString());
    }

    log.info("Request: {}", fullUrl);

    for (Endpoint endpoint : this.service.getEndpoints()) {
      if (endpoint.getMethod().equalsIgnoreCase(req.getMethod())) {        
        if (endpoint.getUrl() != null) {
          String url = endpoint.getUrl().getUrlValue();
          String match = endpoint.getUrl().getMatch();
          log.debug("url={},match={}", url, match);
          if (endpoint.getResult().canHandle(match, fullUrl.toString(), url)) {
            
            log.info("Handle {} for URL '{}' on service '{}'", endpoint.getMethod(), url, service.getName());
            endpoint.getResult().handleResult(req, res);
            return;
          }  
        } else {
          log.info("Handle {} on service '{}'", endpoint.getMethod(), service.getName());
          endpoint.getResult().handleResult(req, res);
          return;
        }
      }
    }

    // else: proxy it
    log.info("Handle proxy call on service '{}'", service.getName());
    this.proxyServlet.service(req, res);
  }
}
