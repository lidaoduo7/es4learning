package com.sunwin.es4study;


import com.google.gson.JsonObject;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 *  根据索引名称，类别，文档ID来修改数据，修改的设置数据可以是Map，Json串，自带工具。我们实际开发一般用Json；
 */
public class UpdateData {
    private static String host="localhost"; // 服务器地址
    private static int port=9300; // 端口
    private static String clusterName = "elasticsearch"; //集群名称

    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder().put("cluster.name", UpdateData.clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(UpdateData.host), UpdateData.port));
        System.out.println(client);

        UpdateData es = new UpdateData();
        es.testUpdate(client);

        client.close();
    }

    /**
     *
     * @param client
     */
    public void testUpdate(TransportClient client){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user", "锋哥");
        jsonObject.addProperty("postDate", "1989-11-11");
        jsonObject.addProperty("message", "学习Elasticsearch");

        UpdateResponse response = client.prepareUpdate("twitter", "tweet", "1").setDoc(jsonObject.toString(), XContentType.JSON).get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    public void testUpdate2(TransportClient client){
        UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate();   //更新请求构建器
        UpdateResponse updateResponse =updateRequestBuilder.setIndex("twitter")
                                                    .setType("tweet")
                                                    .setId("1")
                                                    .get();


    }

}
