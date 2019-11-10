package org.limmen.flexproxy.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.limmen.flexproxy.domain.Configuration;

public class ConfigReader {

  public static Configuration load(Path path) {
    try (InputStream inputStream = new FileInputStream(path.toFile())) {
      JAXBContext context = JAXBContext.newInstance(Configuration.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
            
      return (Configuration) unmarshaller.unmarshal(inputStream);
    } catch (JAXBException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}