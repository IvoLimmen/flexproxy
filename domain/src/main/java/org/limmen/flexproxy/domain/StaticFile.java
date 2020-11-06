package org.limmen.flexproxy.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(FIELD)
@XmlRootElement(name = "statis_file")
@Slf4j
public class StaticFile extends AbstractResult {

  @XmlElement(name = "content_type", defaultValue = "text/json")
  private String contentType;

  @XmlElement(name = "file_name")
  private String fileName;

  @Override
  public void handleResult(HttpServletRequest req, HttpServletResponse response) throws IOException {
    log.debug("Response[contentType={},fileName={}]", getContentType(), getFileName());
    response.setContentType(getContentType());
    try (PrintWriter output = response.getWriter()) {
      Files.lines(Paths.get(getFileName())).forEach(line -> {
        output.append(line);
      });
    }
  }
}
