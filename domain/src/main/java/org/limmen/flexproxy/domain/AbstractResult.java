package org.limmen.flexproxy.domain;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({StaticFile.class, Status.class})
public abstract class AbstractResult {

  public abstract void handleResult(HttpServletResponse response) throws IOException;
}
