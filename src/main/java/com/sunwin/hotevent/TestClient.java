package com.sunwin.hotevent;

import org.elasticsearch.client.Client;

import java.net.UnknownHostException;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException {
		ElasticClient elasticClient = new ElasticClient();
		Client client = elasticClient.getClient();

		client.close();
	}
}
