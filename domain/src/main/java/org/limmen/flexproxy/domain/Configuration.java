package org.limmen.flexproxy.domain;

import java.util.ArrayList;
import java.util.List;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement
@XmlAccessorType(FIELD)
@Data
public class Configuration {

  private int port;

  @XmlElementWrapper(name = "services")
  @XmlElement(name = "service")
  private List<Service> services = new ArrayList<>();
}
