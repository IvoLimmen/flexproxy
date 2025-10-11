package org.limmen.flexproxy.domain;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

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

  public List<Endpoint> getEndpoints() {
      return endpoints;
  }

  public String getMountpoint() {
      return mountpoint;
  }

  public String getName() {
      return name;
  }

  public String getProxyUrl() {
      return proxyUrl;
  }

  public void setDebug(boolean debug) {
      this.debug = debug;
  }

  public void setEndpoints(List<Endpoint> endpoints) {
      this.endpoints = endpoints;
  }

  public void setMountpoint(String mountpoint) {
      this.mountpoint = mountpoint;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void setProxyUrl(String proxyUrl) {
      this.proxyUrl = proxyUrl;
  }

  public void setSavePosts(boolean savePosts) {
      this.savePosts = savePosts;
  }

  public boolean isDebug() {
      return debug;
  }

  public boolean isSavePosts() {
      return savePosts;
  }
}
