package org.limmen.flexproxy.domain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import lombok.Data;

@Data
@XmlAccessorType(FIELD)
@XmlRootElement(name = "url")
public class Url {

  @XmlValue
  private String urlValue;

  @XmlAttribute(name = "match")
  private String match = "equals";
}