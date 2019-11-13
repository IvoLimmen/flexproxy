package org.limmen.flexproxy.domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
