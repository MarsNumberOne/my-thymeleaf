package com.mch;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

/**
 * @description: Copyright 2011-2018 B5M.COM. All rights reserved
 * @author: yangren
 * @version: 1.0
 * @createdate: 2018/10/10
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2018/10/10      yangren       1.0             Why & What is modified
 */
@Data
@PageSelect(cssQuery = "body")
public class PageVo {

    @PageFieldSelect(cssQuery = ".fn-left .red",selectType = XxlCrawlerConf.SelectType.TEXT)
    private String topViewPostsBlock;

    @PageFieldSelect(cssQuery = "#tab-content-item2 .find-letter-list li a",selectType = XxlCrawlerConf.SelectType.TEXT)
    private List<String> data;

    @PageFieldSelect(cssQuery = "#tab-content #contentSeries a img",selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:src" )
    private List<String> logo;


    @PageFieldSelect(cssQuery = "dl dt a",selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href" )
    private List<String> hrel;
}
