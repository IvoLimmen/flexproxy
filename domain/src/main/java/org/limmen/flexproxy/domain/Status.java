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

@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(FIELD)
@XmlRootElement(name = "status")
public class Status extends AbstractResult {
  
  @XmlElement(name = "status_code")
  private int statusCode;
  private String message;   

  @Override
  public void handleResult(HttpServletRequest req, HttpServletResponse response) throws IOException {
    response.sendError(getStatusCode(), getMessage());
  }   
}
