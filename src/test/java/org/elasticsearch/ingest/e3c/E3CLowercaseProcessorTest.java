package org.elasticsearch.ingest.e3c;

import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.AbstractITCase;
import org.elasticsearch.client.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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

    @Test
    public void testSimulateProcessor() throws Exception {
        String josn = jsonBuilder().startObject()
                .startObject("pipeline")
                    .startArray("processors")
                        .startObject()
                            .startObject("e3c_lowercase")
                            .endObject()
                        .endObject()
                    .endArray()
                .endObject()
                .startArray("docs")
                    .startObject()
                        .field("_index", "index")
                        .field("_type", "test")
                        .field("_id", "id")
                        .startObject("_source")
                            .field("foo", "bar")
                        .endObject()
                    .endObject()
                .endArray()
                .endObject().string();

        Map<String, Object> expected = new HashMap<>();
        expected.put("foo", "bar");

        Response response = client.performRequest("POST", "/_ingest/pipeline/_simulate",
                Collections.emptyMap(), new NStringEntity(josn, ContentType.APPLICATION_JSON));

        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}