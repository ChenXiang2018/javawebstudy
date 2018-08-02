package com.cx.test;

import com.cx.mapper.ItemsMapper;
import com.cx.mapper.UserMapper;
import com.cx.mapper.UserMapperOrders;
import com.cx.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

public class UserTest {

    @BeforeClass
    public static void globalInit() {  // 在所有方法执行之前执行
        System.out.println("@BeforeClass标注的方法，在所有方法执行之前执行...");
    }

    @AfterClass
    public static void globalDestory() {  // 在所有方法执行之后执行
        System.out.println("@AfterClass标注的方法，在所有方法执行之后执行...");
    }

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void setUp() throws Exception{  // 在每个测试方法之前执行
        System.out.println("@Before标注的方法，在每个测试方法之前执行...");

        String resource = "mybatis-config.xml"; //mybatis配置文件
        //得到配置文件的流
//        Reader reader = Resources.getResourceAsReader(resource);;

        //创建会话工厂SqlSessionFactory,要传入mybaits的配置文件的流
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        // 得到配置文件的流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 创建会话工厂SqlSessionFactory,要传入mybaits的配置文件的流
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    @After
    public void tearDown() {  // 在每个测试方法之后执行
        System.out.println("@After标注的方法，在每个测试方法之后执行...");
    }


    @Test
    public void testItemInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemsMapper itemsMapper = sqlSession.getMapper(ItemsMapper.class);

        Items items = new Items();
        items.setName("手机");
        items.setPrice(5000f);
        items.setCreatetime(new Date());
        itemsMapper.insert(items);
        sqlSession.commit();
        //还有一个insertSelective(items);方法，是插入的项不为空时，才将那个字段拼接到sql中
        //可以下自动生成的xml文件就知道了。
    }

    //自定义查询
    @Test
    public void testSelectByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemsMapper itemsMapper = sqlSession.getMapper(ItemsMapper.class);

        //自定义查询，这就用到了ItemsExample类了，里面有个Criteria内部类，专门用来封装自定义查询条件的
        ItemsExample itemsExample = new ItemsExample();
        ItemsExample.Criteria criteria = itemsExample.createCriteria();
        //andNameEqualTo相当于在sql中拼接一个“AND name='背包'”
        //还有其他很多方法，都是用来自定义查询条件的，可以自己看一下不同的方法
        criteria.andNameEqualTo("背包");
        List<Items> itemsList = itemsMapper.selectByExample(itemsExample);
        System.out.println(itemsList);
    }

    //根据主键查询，跟原来一样
    @Test
    public void testSelectByPrimaryKey() {
        ItemsMapper itemsMapper = sqlSessionFactory.openSession().getMapper(ItemsMapper.class);
        Items items = itemsMapper.selectByPrimaryKey(1);
        System.out.println(items);
    }

    //根据主键更新item，跟原来一样
    @Test
    public void testUpdateByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemsMapper itemsMapper = sqlSession.getMapper(ItemsMapper.class);
        Items items = itemsMapper.selectByPrimaryKey(1);
        items.setPrice(3540f);
        itemsMapper.updateByPrimaryKey(items);
        sqlSession.commit();
    }


    @Test
    public void testPageHelper() {
        // 创建一个spring容器
        // 从spring容器中获取mapper代理对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemsMapper itemsMapper = sqlSession.getMapper(ItemsMapper.class);
        //自定义查询，这就用到了ItemsExample类了，里面有个Criteria内部类，专门用来封装自定义查询条件的
        ItemsExample itemsExample = new ItemsExample();
        ItemsExample.Criteria criteria = itemsExample.createCriteria();
        //andNameEqualTo相当于在sql中拼接一个“AND name='背包'”
        //还有其他很多方法，都是用来自定义查询条件的，可以自己看一下不同的方法
        criteria.andNameEqualTo("背包");
        //分页处理，显示第一页的10条数据
        PageHelper.startPage(1, 10);
        List<Items> itemsList = itemsMapper.selectByExample(itemsExample);

        // 取商品列表
        for(Items item : itemsList) {
            System.out.println(item.getName());
        }
        // 取分页信息
        PageInfo<Items> pageInfo = new PageInfo<Items>(itemsList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有商品信息：" + total);
    }




    @Test
    public void testCache1() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();//创建代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //下边查询使用一个SqlSession
        //第一次发起请求，查询id为1的用户
        User user1 = userMapper.findById(1);
        System.out.println(user1.getName());

//      如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

        //更新user1的信息
        user1.setName("测试用户22");
        userMapper.update(user1);
        //执行commit操作去清空缓存
        sqlSession.commit();

        //第二次发起请求，查询id为1的用户
        User user2 = userMapper.findById(1);
        System.out.println(user2.getName());

        sqlSession.close();

    }

    @Test
    public void testCache2() throws Exception {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        // 创建代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper1.findById(1);
        System.out.println(user1.getName());
        //这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
        sqlSession1.close();

        //sqlSession3用来清空缓存的，如果要测试二级缓存，需要把该部分注释掉
        //使用sqlSession3执行commit()操作
        UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
        User user  = userMapper3.findById(1);
        user.setName("chenxiang");
        userMapper3.update(user);
        //执行提交，清空UserMapper下边的二级缓存
        sqlSession3.commit();
        sqlSession3.close();

        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper2.findById(1);
        System.out.println(user2.getName());

        sqlSession2.close();

    }





    @Test
    public void testFindOrdersUserLazyLoading() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        //查询订单表（单表）
        List<Orders> list = userMapperOrders.findOrdersUserLazyLoading();

        //遍历上边的订单列表
        for(Orders orders : list) {
            //执行getUser()去查询用户信息，这里实现按需加载
            User user = orders.getUser();
            System.out.println(user.getName());
        }
    }



    @Test
    public void findUserAndItemsResultMap() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        List<User> list = userMapperOrders.findUserAndItemsResultMap();

        for (User item:list) {
            System.out.println(item.getName());
        }
        System.out.println(list.size());
    }

    @Test
    public void testFindOrdersAndOrderDetailResultMap() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        List<Orders> list = userMapperOrders.findOrdersAndOrderDetailResultMap();

        for (Orders item:list) {
            System.out.println(item.getUser().getName());
        }
        System.out.println(list);
    }

    @Test
    public void testUserMapperOrdersResultMap() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        List<Orders> list = userMapperOrders.findOrdersUserResultMap();
        for (Orders item:list) {
            System.out.println(item.getUser().getName());
        }
        System.out.println(list.size());
    }

    @Test
    public void testUserMapperOrders() throws Exception {
        //获取sqlSessionFactory的代码省略了
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        List<OrdersCustom> list = userMapperOrders.findOrdersUser();
        for (OrdersCustom item:list) {
            System.out.println(item.getName());
        }
        System.out.println(list.size());
    }

    @Test
    public void testFindById() {
//        String resource ="mybatis-config.xml";
//        Reader reader = null;
//        try {
//            reader = Resources.getResourceAsReader(resource);
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//            SqlSession session = sqlSessionFactory.openSession();
//
//            User user = session.selectOne("findById",2);
//
//            session.commit();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.findById(1);
        System.out.println(user.getName());
    }

    @Test
    public void testFindUserList() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        User user = new User();
        user.setAge(1);
        user.setName("1");
        userQueryVo.setUser(user);

        //调用userMapper的方法
        List<User> list = userMapper.findUserList(userQueryVo);
        System.out.println(list.size());
    }

    @Test
    public void testFindUserByIdResultMap() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.findUserByIdResultMap(1);
        System.out.println(user.getName());
    }

    @Test
    public void testFindAll() {
        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

           List<User> list =  session.selectList("findAll");

            session.commit();
            System.out.println("list.size=======" + list.size());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAllByCondition() {
        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            User user = new User();
            user.setAge(12);
            List<User> list =  session.selectList("findAllByCondition",user);

            session.commit();
            System.out.println("size=====" + list.size());
            for (User u : list) {
                System.out.println(u.getName());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            User user = new User();
            user.setId(5);
            user.setName("gavin");
            user.setAge(12);
            session.insert("insert", user);
            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Test
    public void testUpdate(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            User user = session.selectOne("findById",2);
            user.setName("adadf");
            user.setAge(12);
            session.insert("update", user);

            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateByCondition(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            User user = new User();
            user.setId(2);
            user.setName("teststeset");
            user.setAge(12);
            session.insert("updateByCondition", user);

            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            session.delete("deleteById",5);

            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteArray(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            session.delete("deleteArray",new Integer[]{1,2,3});

            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteList(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();


            List<Integer> uList = new ArrayList<Integer>();

            uList.add(2);
            uList.add(3);
            uList.add(4);
            session.delete("deleteList",uList);

            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteMap(){

        String resource ="mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();


            Map<String, Object> map = new HashMap<String, Object>();

            map.put("ids", new Integer[]{2, 3, 4});
            map.put("age",18);
            session.delete("deleteMap",map);

            session.commit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
