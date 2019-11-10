package org.limmen.flexproxy.domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(FIELD)
@XmlRootElement(name = "statis_file")
public class StaticFile extends AbstractResult {

  @XmlElement(name = "content_type", defaultValue = "text/json")
  private String contentType;

  @XmlElement(name = "file_name")
  private String fileName;

  @Override
  public void handleResult(HttpServletResponse response) throws IOException {
    response.setContentType(getContentType());
    try (PrintWriter output = response.getWriter()) {
      Files.lines(Paths.get(getFileName())).forEach(line -> {
        output.append(line);
      });
    }
  }
}
