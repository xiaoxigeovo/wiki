package com.lujingxi.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujingxi.wiki.domain.Category;
import com.lujingxi.wiki.domain.CategoryExample;
import com.lujingxi.wiki.mapper.CategoryMapper;
import com.lujingxi.wiki.req.CategoryQueryReq;
import com.lujingxi.wiki.req.CategorySaveReq;
import com.lujingxi.wiki.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    //@Autowired
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        //列表复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return respList;
    }

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数 : {}",pageInfo.getTotal());
        LOG.info("总页数 : {}",pageInfo.getPages());

        /*List<CategoryResp> respList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResp categoryResp = new CategoryResp();
            BeanUtils.copyProperties(category,categoryResp);
            respList.add(categoryResp);
        }*/

        //列表复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /*
     * 保存
     * */
    public void save (CategorySaveReq req) {
        Category category = CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除
     * */
    public void delete (Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
