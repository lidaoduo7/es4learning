package com.sunwin.es4study;


import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 *  根据索引名称，类别，文档ID来获取数据
 */
public class GetData {
    private static String host="localhost"; // 服务器地址
    private static int port=9300; // 端口
    private static String clusterName = "elasticsearch"; //集群名称

    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder().put("cluster.name", GetData.clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(GetData.host), GetData.port));
        System.out.println(client);

        GetData es = new GetData();
        es.testGet(client);

        client.close();
    }

    public void testGet(TransportClient client){
        GetResponse getResponse=client.prepareGet("twitter", "tweet", "1").get();
        System.out.println("获取数据成功");
        System.out.println(getResponse.getSourceAsString());
    }


}
