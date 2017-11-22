package org.elasticsearch.ingest.e3c;

import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;


import java.util.Map;


/**
 * 第一步：创建Processor
 */
public class E3CLowercaseProcessor extends AbstractProcessor {
    /**
     * 第二步：用于注册{E3CLowercaseProcessor}类，告诉ES，这个Processor的存在。
     */
    public static final  class E3CLowercaseFactory implements Processor.Factory {
        @Override
        public Processor create(Map<String, Processor.Factory> map, String tag, Map<String, Object> map1) throws Exception {

            //TODO read from property file
            String source = "foo";
            String target = "new_foo";
            System.out.println(getClass().getName() + " #########Is Loaded!###########");
            return new E3CLowercaseProcessor(tag, source, target);
        }
    }

    public final static String NAME = "e3c_lowercase";

    private final String sourceField;
    private final String targetField;

    protected E3CLowercaseProcessor(String tag, String sourceField, String targetField) {
        super(tag);
        this.sourceField = sourceField;
        this.targetField = targetField;
        System.out.println(getClass().getName() + " #########Is Loaded!###########");
    }

    /**
     *  当调用下面的endpoint时，会返回的信息
     * curl -XPUT localhost:9200/_ingest/pipeline/e3c_lowercase -d '{
        "processors" : [
            {
                "e3c_lowercase" : { }
            }
        ]
       }
     *
     */
    @Override
    public String getType() {
        return NAME;
    }

    @Override
    public void execute(IngestDocument ingestDocument) throws Exception {
        // 添加字段
        if (ingestDocument.hasField(this.sourceField)) {
            ingestDocument.setFieldValue(targetField, ingestDocument.getFieldValue(sourceField, String.class));
        }
    }
}
