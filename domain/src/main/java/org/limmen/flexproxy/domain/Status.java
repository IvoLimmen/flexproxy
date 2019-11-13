package org.limmen.flexproxy.domain;

import java.io.IOException;

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
