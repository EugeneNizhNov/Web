package Handler;

import Request.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface Handler {
    public void handle(Request request, BufferedOutputStream bufOut) throws IOException;
}
