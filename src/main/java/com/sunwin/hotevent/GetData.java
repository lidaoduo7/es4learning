package com.sunwin.hotevent;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

import java.net.UnknownHostException;

public class GetData {

	public static void main(String[] args) throws UnknownHostException {
		ElasticClient elasticClient = new ElasticClient();
		Client client = elasticClient.getClient();

//		GetResponse getResponse=client.prepareGet("hot_event", "jishou", "AWZWthRuziILex5WqmMS").get();
		GetResponse getResponse=client.prepareGet("hot_event", "jishou", "AWZWthRuziILex5WqmMS").get();

		System.out.println(getResponse.getFields());
		System.out.println("获取数据成功");
		System.out.println(getResponse.getSourceAsString());

		client.close();
	}
}
