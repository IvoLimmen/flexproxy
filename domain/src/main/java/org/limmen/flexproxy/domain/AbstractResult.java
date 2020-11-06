package org.limmen.flexproxy.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.IOException;

@XmlTransient
@XmlSeeAlso({StaticFile.class, Status.class})
public abstract class AbstractResult {

  public abstract void handleResult(HttpServletRequest req, HttpServletResponse response) throws IOException;
}
