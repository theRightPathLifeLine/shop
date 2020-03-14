package com.shop.list.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.bean.PmsSkuAttrValue;
import com.shop.bean.SearchParam;
import com.shop.bean.SearchSkuInfo;
import com.shop.list.util.SearchUtil;
import com.shop.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<SearchSkuInfo> getSearchData(SearchParam searchParam) {
        String dsl = SearchUtil.getDsl(searchParam);
        Search search = new Search.Builder(dsl).addIndex("shop").addType("skuInfo").build();
        List<SearchSkuInfo> searchSkuInfos = new ArrayList<>();
        try {
            SearchResult execute = jestClient.execute(search);
            List<SearchResult.Hit<SearchSkuInfo, Void>> hits = execute.getHits(SearchSkuInfo.class);
            hits.stream().forEach(p -> {
                Map<String, List<String>> highlight = p.highlight;
                if(highlight != null) {
                    List<String> skuName = highlight.get("skuName");
                    p.source.setSkuName(skuName.get(0));
                }
                searchSkuInfos.add(p.source);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchSkuInfos;
    }
}
