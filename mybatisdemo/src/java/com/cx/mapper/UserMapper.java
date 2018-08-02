package com.cx.mapper;

import com.cx.pojo.User;
import com.cx.pojo.UserQueryVo;

import java.util.List;
import java.util.Map;

public interface  UserMapper {

    public User findById(int id);
    public List<User> findAll();
    public User findAllByCondition(User user);
    public void insert(User user);
    public void update(User user);
    public void updateByCondition(User user);
    public void deleteById(int id);
    public void deleteArray(int[] ids);
    public void deleteList(List<Integer> list);
    public void deleteMap(Map<String, Object> map);
    public List<User> findUserList(UserQueryVo userQueryVo) throws Exception;
    public User findUserByIdResultMap(int id) throws Exception;

}
