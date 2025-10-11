package org.limmen.flexproxy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.limmen.flexproxy.domain.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.limmen.flexproxy.domain.Endpoint;

public class EndpointWrapperServlet extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(EndpointWrapperServlet.class);

  private static final long serialVersionUID = -1;

  private final Service service;

  private final ProxyServlet proxyServlet;

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Service service;

    public Builder() {
    }

    Builder(Service service) {
      this.service = service;
    }

    public Builder service(Service service) {
      this.service = service;
      return Builder.this;
    }

    public EndpointWrapperServlet build() {
      return new EndpointWrapperServlet(this);
    }
  }

  public EndpointWrapperServlet(final Builder builder) {
    this.service = builder.service;
    this.proxyServlet = ProxyServlet.builder()
        .url(service.getProxyUrl())
        .mountpoint(service.getMountpoint())
        .debug(service.isDebug())
        .savePosts(service.isSavePosts())
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
