package com.bixuebihui.es;

import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {
    

    @Autowired
    private TransportClient transportClient;
    
    /**
     *利用TransportClient实现搜索功能
     * @param title   搜索标题
     * @param  page = 从第几条结果返回 | Integer（比如一次size=20，page=0,如果要显示下一页20条记录则需要size=20,page=20）这个和之前有点区别, size = 每页显示条数
     */

    @RequestMapping(value = "trsearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommandResult<List<Notice>> search(@RequestParam(value = "title", defaultValue = "比特币")String title, @RequestParam(value = "page", defaultValue = "0")Integer page,
                                              @RequestParam(value = "size", defaultValue = "20")Integer size) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //按标题进行查找
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        
       //在这里输入索引名称和type类型
        SearchResponse response = transportClient.prepareSearch("wantu_notice_info").setTypes("doc")

                // 设置查询类型java
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                // 设置查询关键词
                .setQuery(boolQueryBuilder)
                // 设置查询数据的位置,分页用
                .setFrom(page)
                // 设置查询结果集的最大条数
                .setSize(size)
                // 设置是否按查询匹配度排序
                .setExplain(true)
                // 最后就是返回搜索响应信息
                .get();
        SearchHits searchHits = response.getHits();

        List<Notice> list = Lists.newArrayListWithCapacity(size);
        
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
           //获得titie数据
            String titles = (String) sourceAsMap.get("title");
            //获得阅读量数据
            Integer readCount = (Integer) sourceAsMap.get("read_count");
           //把数据装入对象中
            Notice  notice=new  Notice();
            notice.setTitle(titles);
            notice.setReadCount(readCount);
            list.add(notice);
        }

        return CommandResult.ofSucceed(list);
    }

}