package com.mci.gulimall.search.service;

import com.mci.gulimall.search.vo.SearchParam;
import com.mci.gulimall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam searchParam);
}
