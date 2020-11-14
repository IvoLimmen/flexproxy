package org.limmen.flexproxy.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

public class DummyServletOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // Not needed
    }

    @Override
    public void write(int b) throws IOException {
        baos.write(b);
    }

    public String getContent() {
        return baos.toString();
    }
}
