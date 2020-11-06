package org.limmen.flexproxy.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.limmen.flexproxy.domain.Configuration;

public class ConfigReader {

  private ConfigReader() {
    super();
  }

  public static Configuration load(Path path) {
    try (InputStream inputStream = new FileInputStream(path.toFile())) {
      JAXBContext context = JAXBContext.newInstance(Configuration.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
            
      return check((Configuration) unmarshaller.unmarshal(inputStream));
    } catch (JAXBException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Configuration check(Configuration configuration) {
    configuration.getServices().forEach(service -> {
      if (service.getMountpoint().startsWith("/")) {
        throw new IllegalArgumentException(String.format("Mountpoint %s may not start with /", service.getMountpoint()));
      }
      if (service.getMountpoint().endsWith("/")) {
        throw new IllegalArgumentException(String.format("Mountpoint %s may not end with /", service.getMountpoint()));
      }
      if (service.getProxyUrl().endsWith("/")) {
        throw new IllegalArgumentException(String.format("ProxyUrl %s may not end with /", service.getMountpoint()));
      }
    });
    return configuration;
  }
}