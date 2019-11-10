package org.limmen.flexproxy.domain;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import lombok.Data;

@Data
@XmlAccessorType(FIELD)
public class Endpoint {

  private String method;

  private String url;

  @XmlElements({
    @XmlElement(name = "status", type = Status.class),
    @XmlElement(name = "static_file", type = StaticFile.class)})
  private AbstractResult result;
}
