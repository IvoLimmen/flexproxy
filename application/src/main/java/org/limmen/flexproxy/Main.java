package org.limmen.flexproxy;

import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.PathResource;
import org.limmen.flexproxy.config.ConfigReader;
import org.limmen.flexproxy.domain.Configuration;

@Slf4j
public class Main {

  public static void main(String[] args) throws Exception {

    Configuration configuration = ConfigReader.load(Paths.get("./config.xml"));

    Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
    Server server = new Server(configuration.getPort());
    server.setErrorHandler(new CustomErrorHandler());

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.setBaseResource(new PathResource(tempDir));
    server.setHandler(context);

    log.info("Services: {}", configuration.getServices().size());

    configuration.getServices().forEach(service -> {

      log.info("Mounting service at {} for proxy {}", service.getMountpoint(), service.getProxyUrl());
      ServletHolder holder = new ServletHolder(EndpointWrapperServlet.builder()
          .service(service)
          .build());

      context.addServlet(holder, createMountpoint(service.getMountpoint()));
    });

    server.start();
    server.join();
  }

  private static String createMountpoint(String mountpoint) {
    return "/" + mountpoint + "/*";
  }
}
