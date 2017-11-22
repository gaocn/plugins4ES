package org.elasticsearch.rest.e3c;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 实现_hello endpoint
 * curl -XGET "http://localhost:9200/_hello?name=David&pretty"
 *
 * 返回：
 * {"message": "Hello David!"}
 */
public class HelloRestAction extends BaseRestHandler  {
    Logger logger = Logger.getLogger(getClass().getName());

//    @Inject
    public HelloRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(RestRequest.Method.GET, "/_hello", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient nodeClient) throws IOException {
        String name = restRequest.param("name");
        return new RestChannelConsumer() {
            @Override
            public void accept(RestChannel restChannel) throws Exception {
                Message msg = new Message(name);
                XContentBuilder builder = restChannel.newBuilder();
                builder.startObject();
                msg.toXContent(builder, restRequest);
                builder.endObject();
                logger.info("endpoint /_hello is called!!");
                restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            }
        };
    }

}

/**
 * 调用_hello endpoint后，返回给用于一个JSON串，在ES中用ToXContent处于JSON
 */
class Message implements ToXContent {
    private final String name;

    public Message(String name) {
        if (name == null) {
            this.name = "World";
        } else {
            this.name = name;
        }
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder xContentBuilder, Params params) throws IOException {
        return xContentBuilder.field("message", "Hello " + name + "!");
    }
}