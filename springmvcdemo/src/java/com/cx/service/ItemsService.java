package com.cx.service;

import com.cx.pojo.ItemsCustom;
import com.cx.pojo.ItemsQueryVo;

import java.util.List;

public interface ItemsService {

    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
    public ItemsCustom findItemsById(Integer id) throws Exception;
    public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception;

}
