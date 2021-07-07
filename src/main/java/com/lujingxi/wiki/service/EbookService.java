package com.lujingxi.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujingxi.wiki.domain.Ebook;
import com.lujingxi.wiki.domain.EbookExample;
import com.lujingxi.wiki.mapper.EbookMapper;
import com.lujingxi.wiki.req.EbookReq;
import com.lujingxi.wiki.resp.EbookResp;
import com.lujingxi.wiki.resp.PageResp;
import com.lujingxi.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数 : {}",pageInfo.getTotal());
        LOG.info("总页数 : {}",pageInfo.getPages());

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook, ebookResp);
////            对象赋值
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//
//            respList.add(ebookResp);
//        }

//        列表复制
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);

        PageResp<EbookResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }
}
