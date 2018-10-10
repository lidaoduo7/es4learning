package com.sunwin.es4study;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 通过ES 的JAVA API 完成各种CRUD操作
 *  连接ES   Connect2ES
 *  创建索引 AddIndex
 *  创建文档
 *  获取文档 GetData
 *  修改文档 UpdateData
 *  删除文档 DeleteData
 *
 *  把我们索引的文档再查询出来 SearchData
 */
public class Connect2ES {
    private static String host="localhost"; // 服务器地址
    private static int port=9300; // 端口
    private static String clusterName = "elasticsearch"; //集群名称

    public static void main(String[] args) throws Exception{
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Connect2ES.host), Connect2ES.port));
        System.out.println(client);



        client.close();
    }

}
