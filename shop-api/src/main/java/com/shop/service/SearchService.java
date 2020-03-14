package com.shop.service;

import com.shop.bean.SearchParam;
import com.shop.bean.SearchSkuInfo;

import java.util.List;

public interface SearchService {

    List<SearchSkuInfo> getSearchData(SearchParam searchParam);
}
