package org.elasticsearch.ingest.e3c;

import org.elasticsearch.ingest.Processor;
import org.elasticsearch.plugins.IngestPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Collections;
import java.util.Map;


/**
 * 第三步：向plugin注册以实现的插件，
 * pipeline用于定义能够对所有文档进行的操作(Action)，每一个操作是由处理器(Processor)进行处理。
 *   处理器的输入是文档(Doc)，对文档进行操作后，输出为新的文档。
 */
public class IngestE3CLowercasePlugin extends Plugin implements IngestPlugin{
    @Override
    public Map<String, Processor.Factory> getProcessors(Processor.Parameters parameters) {
        return Collections.singletonMap("e3c_lowercase", new E3CLowercaseProcessor.E3CLowercaseFactory());
    }


}
