package com.cx.mapper;

import com.cx.pojo.ItemsCustom;
import com.cx.pojo.ItemsQueryVo;

import java.util.List;

public interface ItemsMapperCustom {

    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
}
