package org.limmen.flexproxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.limmen.flexproxy.domain.Endpoint;
import org.limmen.flexproxy.domain.Service;

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
        .build();
    log.info("Service contains {} endpoints", this.service.getEndpoints().size());
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

    for (Endpoint endpoint : this.service.getEndpoints()) {
      if (endpoint.getMethod().equalsIgnoreCase(req.getMethod())) {
        if (fullUrl.toString().equalsIgnoreCase(endpoint.getUrl())) {
          
          endpoint.getResult().handleResult(res);
          return;
        }
      }
    }

    // else: proxy it
    this.proxyServlet.service(req, res);
  }
}
