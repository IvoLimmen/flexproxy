package org.limmen.flexproxy.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlAccessorType(FIELD)
@XmlRootElement(name = "statis_file")
public class StaticFile implements Result {

  private static final Logger log = LoggerFactory.getLogger(StaticFile.class);

  @XmlElement(name = "content_type", defaultValue = "application/json")
  private String contentType;

  @XmlElement(name = "file_name")
  private String fileName;

  @Override
  public void handleResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.debug("Response[contentType={},fileName={}]", getContentType(), getFileName());
    response.setContentType(getContentType());

    if (getContentType().equalsIgnoreCase("application/json")) {
      log.debug("Unformatting json...");
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
      JsonNode jsonNode = objectMapper.readTree(getFile().toFile());
      objectMapper.writeValue(response.getOutputStream(), jsonNode);
    } else {
      try (PrintWriter output = response.getWriter()) {
        String content = Files.readString(getFile());
        output.write(content);
      }
    }
  }

  private Path getFile() {
    return Paths.get(getFileName());
  }

  public String getContentType() {
      return contentType;
  }

  public String getFileName() {
      return fileName;
  }  

  public void setContentType(String contentType) {
      this.contentType = contentType;
  }

  public void setFileName(String fileName) {
      this.fileName = fileName;
  }
}
