package org.elasticsearch.rest.e3c;

import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestHandler;

import java.util.Collections;
import java.util.List;

public class HelloRestPlugin extends Plugin implements ActionPlugin{
    @Override
    public List<Class<? extends RestHandler>> getRestHandlers() {
        return Collections.singletonList(HelloRestAction.class);
    }
}
