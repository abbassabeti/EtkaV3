package ir.etkastores.app.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

public class IntercentorHelper {

    public static String getResponseBody(Response proceed) throws IOException {
        BufferedSource source = proceed.body().source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Headers headers = proceed.headers();
        Long gzippedLength = null;
        if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
            gzippedLength = buffer.size();
            GzipSource gzippedResponseBody = null;
            try {
                gzippedResponseBody = new GzipSource(buffer.clone());
                buffer = new Buffer();
                buffer.writeAll(gzippedResponseBody);
            } finally {
                if (gzippedResponseBody != null) {
                    gzippedResponseBody.close();
                }
            }
        }

        final Charset UTF8 = Charset.forName("UTF-8");
        Charset charset = UTF8;
        MediaType contentType = proceed.body().contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        return buffer.clone().readString(charset);
    }

}
