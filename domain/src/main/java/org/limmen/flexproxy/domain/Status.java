package org.limmen.flexproxy.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(FIELD)
@XmlRootElement(name = "status")
@Slf4j
public class Status extends AbstractResult {
  
  @XmlElement(name = "status_code")
  private int statusCode;
  private String message;   

  @Override
  public void handleResult(HttpServletRequest req, HttpServletResponse response) throws IOException {
    log.debug("Response[statusCode={},message={}]", getStatusCode(), getMessage());
    response.sendError(getStatusCode(), getMessage());
  }   
}
