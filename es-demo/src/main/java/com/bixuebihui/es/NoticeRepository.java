package com.bixuebihui.es;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
public interface NoticeRepository extends ElasticsearchRepository<Notice, Long> {
    @RestController
    @RequestMapping("/api/v1/article")
    public class NoticeController {


        @Autowired
        private NoticeRepository nticeRepository;

        @GetMapping("save")
        public CommandResult<Void> save(long id, String title){

            Notice article = new Notice();
            article.setId(id);
            article.setReadCount(123);
            article.setTitle("springboot整合elasticsearch，这个是新版本 2018年");
            nticeRepository.save(article);
            return CommandResult.ofSucceed();
        }


        /**
         * @param title   搜索标题
         * @param pageable page = 第几页参数, value = 每页显示条数
         */
        @GetMapping("search")
        public CommandResult<List<Notice>> search(String title,@PageableDefault(page = 1, value = 10) Pageable pageable){

            //按标题进行搜索
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title);

            //如果实体和数据的名称对应就会自动封装，pageable分页参数
            Iterable<Notice> listIt =  nticeRepository.search(queryBuilder,pageable);

            //Iterable转list
            List<Notice> list= Lists.newArrayList(listIt);

            return CommandResult.ofSucceed(list);
        }
    }
}