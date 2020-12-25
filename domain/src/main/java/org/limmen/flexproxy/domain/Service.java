package org.limmen.flexproxy.domain;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@XmlAccessorType(FIELD)
public class Service {

  private String mountpoint;

  private String name;
  
  private boolean debug;

  @XmlElement(name = "save_posts")
  private boolean savePosts;

  @XmlElement(name = "proxy_url")
  private String proxyUrl;

  @XmlElementWrapper(name = "endpoints")
  @XmlElement(name = "endpoint")
  private List<Endpoint> endpoints = new ArrayList<>();
}
