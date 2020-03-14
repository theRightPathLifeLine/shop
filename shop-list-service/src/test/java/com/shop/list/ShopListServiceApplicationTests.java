package com.shop.list;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.PmsSkuInfo;
import com.shop.bean.SearchSkuInfo;
import com.shop.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopListServiceApplicationTests {
//	@Reference
	SkuService skuService;

	@Autowired
	JestClient jestClient;

	@Test
	public void contextLoads() throws IOException {
		List<PmsSkuInfo> skuInfos = skuService.getSkuInfos();
		System.out.println(skuInfos);
		skuInfos.stream().forEach(p ->{
			try {
				SearchSkuInfo searchSkuInfo = new SearchSkuInfo();
				BeanUtils.copyProperties(searchSkuInfo,p);
				Index index = new Index.Builder(searchSkuInfo).index("shop").type("skuInfo").id(searchSkuInfo.getId()).build();
				jestClient.execute(index);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void queryTest() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder builder = new BoolQueryBuilder();
		TermQueryBuilder termQueryBuilder = new TermQueryBuilder("pmsSkuAttrValueList.valueId","59");
		builder.filter(termQueryBuilder);
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","鲨鱼");
		builder.must(matchQueryBuilder);
		searchSourceBuilder.query(builder);
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<span style=\"color:red\">");
		highlightBuilder.field("skuName");
		highlightBuilder.postTags("</span>");
		searchSourceBuilder.highlighter(highlightBuilder);
		System.out.println(searchSourceBuilder.toString());
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("shop").addType("skuInfo").build();
		SearchResult execute = jestClient.execute(search);
		System.out.println(execute.getJsonString());
	}
}
