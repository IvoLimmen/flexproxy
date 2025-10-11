package org.limmen.flexproxy.domain;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElements;

@XmlAccessorType(FIELD)
public class Endpoint {

  private String method;

  @XmlElementRef(name = "url")
  private Url url;

  @XmlElements({
    @XmlElement(name = "status", type = Status.class),
    @XmlElement(name = "static_file", type = StaticFile.class)})
  private Result result;

  public String getMethod() {
      return method;
  }

  public Result getResult() {
      return result;
  }

  public Url getUrl() {
      return url;
  }

  public void setMethod(String method) {
      this.method = method;
  }

  public void setResult(Result result) {
      this.result = result;
  }

  public void setUrl(Url url) {
      this.url = url;
  }
}
