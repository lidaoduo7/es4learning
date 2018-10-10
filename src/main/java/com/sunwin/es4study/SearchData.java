package com.sunwin.es4study;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Map;
import java.util.Set;

public class SearchData {
    private static String host="localhost"; // 服务器地址
    private static int port=9300; // 端口
    private static String clusterName = "elasticsearch"; //集群名称

    public static void main(String[] args) throws Exception{
        Settings settings = Settings.builder().put("cluster.name", SearchData.clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(SearchData.host), SearchData.port));
        System.out.println(client);

        SearchData es = new SearchData();
        es.testSearch(client);

        client.close();
    }


    public void testSearch(TransportClient client){
        SearchResponse searchResponse = client.prepareSearch("hot_event").get();
        System.out.println(searchResponse.status());

        // MatchAll on the whole cluster with all default options
        SearchResponse response = client.prepareSearch().get();
        System.out.println(response.getHits());

        SearchResponse response2 = client.prepareSearch("hot_event")
                .setTypes("jishou")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .get();
    }

    private  void querySearch(TransportClient client,String index, String type,String term,String queryString){
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                // 设置查询类型
// 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
// 2.SearchType.SCAN = 扫描查询,无序
// 3.SearchType.COUNT = 不设置的话,这个为默认值,还有的自己去试试吧
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                // 设置查询关键词
                .setQuery(QueryBuilders.matchQuery(term, queryString))
//                .addHighlightedField(term)
//                .setHighlighterPreTags("<em>")
//                .setHighlighterPostTags("</em>")
                // 设置查询数据的位置,分页用
                .setFrom(0)
// 设置查询结果集的最大条数
                .setSize(60)
// 设置是否按查询匹配度排序
                .setExplain(true)
// 最后就是返回搜索响应信息
                .execute()
                .actionGet();
        SearchHits searchHits = response.getHits();
        System.out.println("-----------------在["+term+"]中搜索关键字["+queryString+"]---------------------");
        System.out.println("共匹配到:"+searchHits.getTotalHits()+"条记录!");
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit searchHit : hits) {
//获取高亮的字段
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField highlightField = highlightFields.get(term);
            System.out.println("高亮字段:"+highlightField.getName()+"\n高亮部分内容:"+highlightField.getFragments()[0].string());
            Map<String, Object> sourceAsMap = searchHit.sourceAsMap();
            Set<String> keySet = sourceAsMap.keySet();
            for (String string : keySet) {
//key value 值对应关系
                System.out.println(string+":"+sourceAsMap.get(string));
            }
            System.out.println();
        }
    }


}
