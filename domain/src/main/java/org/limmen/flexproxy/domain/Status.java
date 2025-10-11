package org.limmen.flexproxy.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@XmlAccessorType(FIELD)
@XmlRootElement(name = "status")
public class Status implements Result {

  private static final Logger log = LoggerFactory.getLogger(Status.class);
  
  @XmlElement(name = "status_code")
  private int statusCode;
  private String message;   

  @Override
  public void handleResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.debug("Response[statusCode={},message={}]", getStatusCode(), getMessage());
    response.sendError(getStatusCode(), getMessage());
  }   

  public String getMessage() {
      return message;
  }

  public int getStatusCode() {
      return statusCode;
  }

  public void setMessage(String message) {
      this.message = message;
  }

  public void setStatusCode(int statusCode) {
      this.statusCode = statusCode;
  }
}
