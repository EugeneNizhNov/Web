package Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Request {
    private final String path;
    private final String method;
    private final Map<String, String> header;
    private final InputStream input;

    private Request(String method, String path, Map<String, String> header, InputStream input) {
        this.path = path;
        this.method = method;
        this.header = header;
        this.input = input;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public InputStream getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "Request{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", header='" + header + '\'' +
                '}';
    }

    public static Request fromInputStream(InputStream input) throws IOException {
        var in = new BufferedReader(new InputStreamReader(input));
        // read only request line for simplicity
        // must be in form GET /path HTTP/1.1
        final var requestLine = in.readLine();
        final var parts = requestLine.split(" ");
        if (parts.length != 3) {
            throw new IOException("Invalid Request");
        }
        var method = parts[0];
        var path = parts[1];
        Map<String, String> header = new HashMap<>();
        String line;
        while (!(line = in.readLine()).isEmpty()) {
            int i = line.indexOf(":");
            String nameHeader = line.substring(0, i);
            String valueHeader = line.substring(i + 2);
            header.put(nameHeader, valueHeader);
        }
        return new Request(method, path, header, input);
    }
}
