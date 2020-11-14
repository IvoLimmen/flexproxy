package org.limmen.flexproxy.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class StatusTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void shouldReturnConfiguredStatus() throws IOException {

        Status subject = new Status();
        subject.setMessage("Error");
        subject.setStatusCode(444);

        subject.handleResult(request, response);

        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> errorCaptor = ArgumentCaptor.forClass(String.class);

        verify(response).sendError(statusCodeCaptor.capture(), errorCaptor.capture());

        assertEquals("Error", errorCaptor.getValue());
        assertEquals(444, statusCodeCaptor.getValue());
    }
}
