package com.sunwin.es4study;


import com.google.gson.JsonObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  添加索引
 */
public class AddIndex {
    private static String host="localhost"; // 服务器地址
    private static int port=9300; // 端口
    private static String clusterName = "elasticsearch"; //集群名称

    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder().put("cluster.name", AddIndex.clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(AddIndex.host), AddIndex.port));
        System.out.println(client);

        AddIndex es = new AddIndex();
        es.addIndex4(client);

        client.close();
    }


    /**
     *  添加索引 方式一
     * @param client
     * @throws Exception
     */
    public void addIndex(TransportClient client)throws Exception{
        IndexResponse response =client.prepareIndex("twitter", "tweet", "1")
                .setSource(XContentFactory.jsonBuilder()    //设置包含文档结构的文档源
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 添加索引 方式二
     * @param client
     * @throws Exception
     */
    public void addIndex2(TransportClient client)throws Exception{
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        IndexResponse response =client.prepareIndex("weibo", "tweet")
                .setSource(json, XContentType.JSON)    //设置包含文档结构的文档源
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    /**
     *  添加索引 方式三
     * @param client
     * @throws Exception
     */
    public void addIndex3(TransportClient client)throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user","kimchy");
        map.put("postDate",new Date());
        map.put("message","trying out Elasticsearch");

        IndexResponse response =client.prepareIndex("qq", "tweet")
                .setSource(map)            //设置包含文档结构的文档源
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    /**
     *  添加索引 方式四
     * @param client
     * @throws Exception
     */
    public void addIndex4(TransportClient client)throws Exception{
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user", "kimchy");
        jsonObject.addProperty("postDate", "1989-11-11");
        jsonObject.addProperty("message", "trying out Elasticsearch");

        IndexResponse response =client.prepareIndex("qq2", "tweet")
                .setSource(jsonObject.toString(),XContentType.JSON)   //设置包含文档结构的文档源
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }
}
