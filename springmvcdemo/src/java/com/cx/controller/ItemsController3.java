package com.cx.controller;

import com.cx.pojo.ItemsCustom;
import com.cx.pojo.ItemsQueryVo;
import com.cx.service.ItemsService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class ItemsController3 {

    @Autowired
    private ItemsService itemsService;

    @RequestMapping("/queryItems")
    public ModelAndView queryItems() throws Exception {

        // 调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsService.findItemsList(null);

        // 返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");

        return modelAndView;
    }

    @RequestMapping("/editItems")
    public String editItems(Model model,
                            @RequestParam(value = "id", required = true) Integer items_id)
            throws Exception {
        // 根据id查询对应的Items
        ItemsCustom itemsCustom = itemsService.findItemsById(items_id);

        if (itemsCustom == null) {
            throw new CustomException("修改的商品信息不存在！");
        }

        model.addAttribute("itemsCustom", itemsCustom);

        return "/WEB-INF/jsp/items/editItems.jsp";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download() throws IOException {
        File file = new File("D:\\line.jpg");
        HttpHeaders headers = new HttpHeaders();
        String filename = new String("hello.jpg".getBytes("utf-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment",filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
    }

    @RequestMapping("/doupload")
    public String doupload(MultipartFile uploadFile,HttpSession session) throws Exception{
        String filename = uploadFile.getOriginalFilename();
        String leftPath= session.getServletContext().getRealPath("/images");
        System.out.println("leftpath======" + leftPath);
        File file = new File(leftPath,filename);
        uploadFile.transferTo(file);
        return "/WEB-INF/jsp/success.jsp";
    }

    @RequestMapping("/doupload2")
    public String doupload(@RequestParam MultipartFile[] uploadFile2,HttpSession session) throws Exception{
        for(MultipartFile item :uploadFile2){
            if(item.getSize()>0){
                String filename = item.getOriginalFilename();
                String leftPath= session.getServletContext().getRealPath("/images");
                File file = new File(leftPath,filename);
                item.transferTo(file);
            }else{
                return "/WEB-INF/jsp/error.jsp";
            }
        }

        return "/WEB-INF/jsp/success.jsp";
    }

    @RequestMapping("/editItemsSubmit")
    public String editItemsSubmit(Model model, HttpServletRequest request,
                                  Integer id,
                                  @Validated(value = { ValidGroup1.class }) ItemsCustom itemsCustom,
                                  BindingResult bindingResult,
                                  @RequestParam MultipartFile[] items_pic)
            throws Exception {

        // 获取校验错误信息
        if (bindingResult.hasErrors()) {
            // 输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                // System.out.println(objectError.getDefaultMessage());
                // 原来是上面这句，但是由于properties文件默认无法输入中文，所以我把properties文件改成了utf-8编码，
                // 但是这样的话读取出来就是乱码了，所以我先用iso打乱，再用utf-8生成，即可解决乱码问题
                System.out.println(new String(objectError.getDefaultMessage()
                        .getBytes("ISO-8859-1"), "UTF-8"));
            }
            // 将错误信息传到页面
            model.addAttribute("allErrors", allErrors);
        }

        //多个文件上传
        for(MultipartFile myfile : items_pic) {
            if(myfile.isEmpty()){
                System.out.println("文件未上传");
            }else{
                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());
                System.out.println("文件名称: " + myfile.getName());
                System.out.println("文件原名: " + myfile.getOriginalFilename());
                System.out.println("========================================");

                String originalFileName = myfile.getOriginalFilename();
                String pic_path = "E:\\github\\develop\\upload\\temp\\";
                String newFileName = UUID.randomUUID()
                        + originalFileName.substring(originalFileName
                        .lastIndexOf("."));
                File newFile = new File(pic_path + newFileName);
                myfile.transferTo(newFile);
            }
        }
/*
		// 处理上传的单个图片
		// 原始名称
		String originalFileName = items_pic.getOriginalFilename();
		// 上传图片
		if (items_pic != null && originalFileName != null && originalFileName.length() > 0) {
			// 存储图片的物理路径
			String pic_path = "E:\\github\\develop\\upload\\temp\\";
			// 新的图片名称
			String newFileName = UUID.randomUUID()
					+ originalFileName.substring(originalFileName
							.lastIndexOf("."));
			// 新图片
			File newFile = new File(pic_path + newFileName);
			// 将内存中的数据写入磁盘
			items_pic.transferTo(newFile);
			// 将新图片名称写到itemsCustom中
			itemsCustom.setPic(newFileName);
		} else {
			//如果用户没有选择图片就上传了，还用原来的图片
			ItemsCustom temp = itemsService.findItemsById(itemsCustom.getId());
			itemsCustom.setPic(temp.getPic());
		}
		// 调用service更新商品信息，页面需要将商品信息传到此方法
		itemsService.updateItems(id, itemsCustom);
*/
        // return "redirect:queryItems.action";
        // return "forward:queryItems.action";

        return "/WEB-INF/jsp/success.jsp";
    }

    @RequestMapping("/editItemsAllSubmit")
    public String editItemsAllSubmit(Model model, ItemsQueryVo itemsQueryVo)
            throws Exception {

        // 打个断点，即可查看itemsQueryVo中itemsCustom是否已经接收到了前面传来的name
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
        model.addAttribute("itemsList", itemsList);
        return "/WEB-INF/jsp/items/itemsList.jsp";
    }

    @RequestMapping("/deleteItems")
    public String deleteItems(Integer[] items_id) throws Exception {

        // 这里就不删了，因为数据我们后面还需要，在return前打个断点，看一下items_id中的值即可
        return "/WEB-INF/jsp/success.jsp";
    }

    // 批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息
    @RequestMapping("/editItemsQuery")
    public ModelAndView editItemsQuery(HttpServletRequest request,
                                       ItemsQueryVo itemsQueryVo) throws Exception {

        // 调用service查找 数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

        // 返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        // 相当 于request的setAttribut，在jsp页面中通过itemsList取数据
        modelAndView.addObject("itemsList", itemsList);

        modelAndView.setViewName("/WEB-INF/jsp/items/editItemsQuery.jsp");

        return modelAndView;

    }

    @RequestMapping("/editItemsQueryResult")
    public String editItemsQueryResult(ItemsQueryVo itemsQueryVo)
            throws Exception {
        // 下面打个断点，进来后看看itemsQueryVo中的List<ItemsCustom>属性有没有正确接收参数
        return "/WEB-INF/jsp/success.jsp";
    }

    //测试请求的是json串(商品信息)，输出json(商品信息)
    //@RequestBody将请求的商品信息的json串转成itemsCustom对象
    //@ResponseBody将itemsCustom对象转成json输出
    @RequestMapping("/requestJson")
    public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) {

        return itemsCustom; //由于@ResponseBody注解，将itemsCustom转成json格式返回
    }

    //测试请求的是key/value(商品信息)，输出json(商品信息)
    //@ResponseBody将itemsCustom对象转成json输出
    @RequestMapping("/responseJson")
    public @ResponseBody
    ItemsCustom responseJson(ItemsCustom itemsCustom) {

        return itemsCustom; //由于@ResponseBody注解，将itemsCustom转成json格式返回
    }

    //查询商品信息，输出json，使用RESTful
    @RequestMapping("/itemsView/{id}")
    public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception {
        ItemsCustom itemsCustom = itemsService.findItemsById(id);
        return itemsCustom;
    }

    //登陆
    @RequestMapping("/login")
    public String login(HttpServletRequest request, String username, String password) throws Exception {

        //实际中要去和数据库匹配的
        //....
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        return "redirect:queryItems.action";
    }

    //退出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {


        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:queryItems.action";
    }
}
