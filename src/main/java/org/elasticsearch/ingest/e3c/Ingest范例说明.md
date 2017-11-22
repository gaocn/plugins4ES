## 创建Ingest创建范例

### 什么是Ingest?
Ingest是elasticsearch-5.X后的新特定，以文档为单位，用于实时进行数据的转换，并在转换完成后将新的文档发送给elasticsearch处理。

Ingest的作用：

- 定义管道(pipeline)；
- 模拟管道，在不建立文档的情况下，显示管道的功能；
- 在建立前文档使用管道进行实时数据转换后入ES存储库；

pipeline用于定义能够对所有文档进行的操作(Action)，每一个操作是由处理器(Processor)进行处理。处理器的输入是文档(Doc)，对文档进行操作后，输出为新的文档。

####  定义管道

范例： 定义管道名为 e3c_lowercase，用于将指定文档中的某些域中的字符串转换为小写。

``` 
    curl -XPUT localhost:9200/_ingest/pipeline/lowercase-example -d '{
        "processors" : [
            {
                "e3c_lowercase" : {
                "field": "message"
                }
            }
        ]
    }'
```

#### 模拟管道

范例：自定义一个REST Endpoint名称_simulate，在不创建文档的情况下，用于显示指定pipeline的用法。

``` 
    curl -XGET "localhost:9200/_ingest/pipeline/_simulate?pretty" -d '{
      "pipeline" : {
        "processors" : [
         {
           "e3c_lowercase" : {
             "field": "foo"
           }
         }
        ]
      },
      "docs" : [
        {
          "_source" : {
            "foo" : "This test CONTAINS also UPPER-CASE chars"
          }
        }
      ]
    }'
    
    返回结果如下
    {
      "_index" : "test",
      "_type" : "doc",
      "_id" : "1",
      "_version" : 1,
      "found" : true,
      "_source" : {
        "foo" : "this test contains also upper-case chars"
      }
    }    
```

#### 使用实时转换数据

那么应该如何使用自定义的管道实现实时文档数据的转换呢？只需要在创建文档时，添加查询参数pipeline=e3c_lowercase就可以实现在创建文档时调用指定的管道对数据进行实时转换。

``` 
    创建文档并指定管道
    curl -XPUT "localhost:9200/test/doc/1?pipeline=lowercase-example&pretty" -d '{
        "foo" : "This test CONTAINS also UPPER-CASE chars"
    }'
    
    查看创建文档的结果
    curl -XGET "localhost:9200/test/doc/1?pretty"
    {
      "_index" : "test",
      "_type" : "doc",
      "_id" : "1",
      "_version" : 1,
      "found" : true,
      "_source" : {
        "foo" : "this test contains also upper-case chars"
      }
    }
    
    

```

### 实现自定义管道

