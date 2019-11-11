package org.limmen.flexproxy.domain;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({StaticFile.class, Status.class})
public abstract class AbstractResult {

  public abstract void handleResult(HttpServletRequest req, HttpServletResponse response) throws IOException;
}
