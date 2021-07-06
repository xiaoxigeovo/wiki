package com.lujingxi.wiki.controller;

import com.lujingxi.wiki.domain.Ebook;
import com.lujingxi.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public List<Ebook> list(){
        return ebookService.list();
    }
}
