package com.bixuebihui.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.Document;

//indexName代表所以名称,type代表表名称
@Document(indexName = "wantu_notice_info", type = "doc")
public class Notice {
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExchangeMc(String exchangeMc) {
        this.exchangeMc = exchangeMc;
    }

    public void setOriginCreateTime(String originCreateTime) {
        this.originCreateTime = originCreateTime;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    //id
    @JsonProperty("auto_id")
    private Long id;

    //标题
    @JsonProperty("title")
    private String title;

    //公告标签
    @JsonProperty("exchange_mc")
    private String exchangeMc;

    //公告发布时间
    @JsonProperty("create_time")
    private String originCreateTime;

    //公告阅读数量
    @JsonProperty("read_count")
    private Integer readCount;

}