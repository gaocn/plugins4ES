package org.elasticsearch.ingest.e3c;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.AbstractITCase;
import org.elasticsearch.client.Response;
import org.junit.Test;

import java.io.IOException;

public class E3CLowercaseProcessorTest extends AbstractITCase {

    @Test
    public void testClient() {
        if (client != null) {
            try {
                Response response = client.performRequest("GET", "/_cat/health?v");
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}