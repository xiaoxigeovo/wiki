package com.lujingxi.wiki.controller;

import com.lujingxi.wiki.req.EbookReq;
import com.lujingxi.wiki.resp.CommonResp;
import com.lujingxi.wiki.resp.EbookResp;
import com.lujingxi.wiki.resp.PageResp;
import com.lujingxi.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
