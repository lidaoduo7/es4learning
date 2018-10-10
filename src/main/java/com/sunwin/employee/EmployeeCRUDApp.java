package com.sunwin.employee;


import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 参考学习：https://blog.csdn.net/lyg_come_on/article/details/78845032
 * 员工增删改查的应用程序
 */
public class EmployeeCRUDApp {
    public static void main(String[] args) throws  Exception{
        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
//        	addEmploy(client);
        //	undateEmployee(client);
        //	delEmployee(client);
        //	getEmployee(client);
        client.close();	}
    public static void addEmploy(TransportClient  client) throws Exception{
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("name","zhangsan")
                .field("age",27)
                .field("position","technique english")
                .field("country","China")
                .field("join_date","2017-01-01")
                .field("salary","10000")
                .endObject();
        IndexResponse response = client.prepareIndex("company","employee","6")
                .setSource(builder).get();
        System.out.println(response.getResult());
    }
    public static void delEmployee(TransportClient client){
        DeleteResponse response  = client.prepareDelete("company","employee","6").get();
        System.out.println(response.getResult());
    }
    public static  void undateEmployee(TransportClient client) throws  Exception{
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("name","lisi").endObject();
        UpdateResponse response  =  client.prepareUpdate("company","employee","6")
                .setDoc(builder).get();
        System.out.println(response.getResult());
    }
    public static  void getEmployee(TransportClient client){
        GetResponse response = client.prepareGet("company","employee","6").get();
        System.out.println(response.getSourceAsString());
    }

}
