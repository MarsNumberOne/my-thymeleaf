package com.mch;

import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
public class CranwStart {
    public static void main(String[] args) {
        start();
    }

    public static void start(){
        XxlCrawler crawler = new XxlCrawler.Builder()
                //设置URL
                .setUrls("https://www.autohome.com.cn/grade/carhtml/X.html")
                //URL白名单
                .setWhiteUrlRegexs("https://www.autohome.com.cn/grade/carhtml/X.html")
                .setThreadCount(3)
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        // 解析封装 PageVo 对象
                        String topViewPostsBlock = pageVo.getTopViewPostsBlock();
                        List<String> data = pageVo.getData();
                        List<String> logo = pageVo.getLogo();
                        List<String> hrel = pageVo.getHrel();
                        System.out.println(topViewPostsBlock);
                        String pageUrl = html.baseUri();
                        System.out.println(pageUrl + "：" + pageVo.toString());
                    }
                })
                .build();

        crawler.start(true);
    }
}
