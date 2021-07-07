package com.lujingxi.wiki.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DocMapperCust {

    void increaseViewCount(@Param("id") Long id);
}
