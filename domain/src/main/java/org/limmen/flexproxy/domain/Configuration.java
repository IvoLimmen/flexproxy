package org.limmen.flexproxy.domain;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
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
