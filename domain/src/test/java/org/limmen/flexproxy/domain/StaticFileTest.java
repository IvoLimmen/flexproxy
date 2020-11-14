package org.limmen.flexproxy.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class StaticFileTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Test
  void shouldReturnUnformattedJson() throws IOException {
    DummyServletOutputStream stream = new DummyServletOutputStream();

    when(response.getOutputStream()).thenReturn(stream);

    StaticFile subject = new StaticFile();
    subject.setFileName("src/test/resources/formatted.json");
    subject.setContentType("application/json");

    subject.handleResult(request, response);

    String content =  """
                      [{"id":1,"name":"Person 1","email":"p1@gmail.com"},{"id":2,"name":"Person 2","email":"p2@gmail.com"}]""";

    assertEquals(content, stream.getContent());
  }

  @Test
  void shouldNotUnformatNonJson() throws IOException {
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(arrayOutputStream);

    when(response.getWriter()).thenReturn(writer);

    StaticFile subject = new StaticFile();
    subject.setFileName("src/test/resources/formatted.json");
    subject.setContentType("text/json");

    subject.handleResult(request, response);

    String content = """
        [
          {
            "id": 1,
            "name": "Person 1",
            "email": "p1@gmail.com"
          },
          {
            "id": 2,
            "name": "Person 2",
            "email": "p2@gmail.com"
          }
        ]""";

    assertEquals(content, arrayOutputStream.toString());
  }
}
