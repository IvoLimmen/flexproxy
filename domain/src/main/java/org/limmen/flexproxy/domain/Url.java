package org.limmen.flexproxy.domain;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(FIELD)
@XmlRootElement(name = "url")
public class Url {

  @XmlValue
  private String urlValue;

  @XmlAttribute(name = "match")
  private String match = "equals";

  public String getMatch() {
      return match;
  }

  public String getUrlValue() {
      return urlValue;
  }

  public void setMatch(String match) {
      this.match = match;
  }

  public void setUrlValue(String urlValue) {
      this.urlValue = urlValue;
  }
}