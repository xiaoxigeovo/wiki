package com.lujingxi.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujingxi.wiki.domain.Doc;
import com.lujingxi.wiki.domain.DocExample;
import com.lujingxi.wiki.mapper.DocMapper;
import com.lujingxi.wiki.req.DocQueryReq;
import com.lujingxi.wiki.req.DocSaveReq;
import com.lujingxi.wiki.resp.DocQueryResp;
import com.lujingxi.wiki.resp.PageResp;
import com.lujingxi.wiki.util.CopyUtil;
import com.lujingxi.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    //@Autowired
    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<DocQueryResp> all() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        //列表复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        return respList;
    }

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数 : {}",pageInfo.getTotal());
        LOG.info("总页数 : {}",pageInfo.getPages());

        /*List<DocResp> respList = new ArrayList<>();
        for (Doc doc : docList) {
            DocResp docResp = new DocResp();
            BeanUtils.copyProperties(doc,docResp);
            respList.add(docResp);
        }*/

        //列表复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /*
     * 保存
     * */
    public void save (DocSaveReq req) {
        Doc doc = CopyUtil.copy(req,Doc.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    /**
     * 删除
     * */
    public void delete (Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
}