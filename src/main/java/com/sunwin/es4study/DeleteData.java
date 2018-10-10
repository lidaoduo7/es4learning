package com.sunwin.es4study;


import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 *  删除数据
 */
public class DeleteData {
    private static String host="localhost"; // 服务器地址
    private static int port=9300; // 端口
    private static String clusterName = "elasticsearch"; //集群名称

    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder().put("cluster.name", DeleteData.clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(DeleteData.host), DeleteData.port));
        System.out.println(client);

        DeleteData es = new DeleteData();
        es.testDelete2(client);

        client.close();
    }

    /**
     *
     * @param client
     */
    public void testDelete(TransportClient client){
        DeleteResponse response=client.prepareDelete("twitter", "tweet", "1").get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }


    public void testDelete2(TransportClient client){
        DeleteRequestBuilder deleteRequestBuilder=client.prepareDelete();
        DeleteResponse deleteResponse=deleteRequestBuilder.setIndex("qq2").setType("tweet").setId("AWZWn_dMziILex5WqmMF").get();

    }


}
