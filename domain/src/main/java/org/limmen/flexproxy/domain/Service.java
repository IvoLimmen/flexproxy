package org.limmen.flexproxy.domain;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import lombok.Data;

@Data
@XmlAccessorType(FIELD)
public class Service {

  private String mountpoint;

  private String name;
  
  @XmlElement(name = "proxy_url")
  private String proxyUrl;

  @XmlElementWrapper(name = "endpoints")
  @XmlElement(name = "endpoint")
  private List<Endpoint> endpoints = new ArrayList<>();
}
