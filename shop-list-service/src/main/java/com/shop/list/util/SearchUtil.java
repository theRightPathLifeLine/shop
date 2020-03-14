package com.shop.list.util;

import com.shop.bean.SearchParam;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

public class SearchUtil {

    public static String getDsl(SearchParam searchParam) {
        String keyword = searchParam.getKeyword();
        String catalog3Id = searchParam.getCatalog3Id();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder builder = new BoolQueryBuilder();
        if (StringUtils.isNotBlank(catalog3Id)) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            builder.filter(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(keyword)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            builder.must(matchQueryBuilder);
        }
        String[] valueId = searchParam.getValueId();
        if(valueId != null){
            for (String s : valueId) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("pmsSkuAttrValueList.valueId", s);
                builder.filter(termQueryBuilder);
            }
        }
        searchSourceBuilder.query(builder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        return searchSourceBuilder.toString();
    }

    public static void main(String[] args) {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder builder = new BoolQueryBuilder();
//        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", "61");
//        builder.filter(termQueryBuilder);
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "小米");
//        builder.must(matchQueryBuilder);
//        TermQueryBuilder termQueryBuilder1 = new TermQueryBuilder("pmsSkuAttrValueList.valueId", "43");
//        builder.filter(termQueryBuilder1);
//        searchSourceBuilder.query(builder);
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.field("skuName");
//        highlightBuilder.postTags("</span>");
//        searchSourceBuilder.highlighter(highlightBuilder);
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(20);
//        System.out.println(searchSourceBuilder);
        new MyTest("A",0).start();
        new MyTest("B",1).start();
        new MyTest("C",2).start();


    }

    static int i = 0;


}
