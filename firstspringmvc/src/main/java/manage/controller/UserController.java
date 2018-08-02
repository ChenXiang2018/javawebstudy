package manage.controller;

import manage.model.TMgtUser;
import manage.service.IUserService;
import manage.utils.ConstantSrting;
import manage.utils.resultUtils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/user/")
public class UserController {
    @Autowired
    private IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param pw       密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login")
    public BaseResult<TMgtUser> login(String userName, String pw) {

        BaseResult<TMgtUser> br = new BaseResult<TMgtUser>();
        if (userName == null || userName.length() == 0 || pw == null || pw.length() == 0) {
            logger.error("用户名和密码不能为空!");
            br.setStatus(ConstantSrting.STATUS_FAIL);
            br.setMessage("用户名和密码不能为空!");
            return br;
        }
        if( userName.length() < 3 || userName.length() > 16 ){
            br.setStatus(ConstantSrting.STATUS_FAIL);
            br.setMessage("用户名只能包含中文、英文字符、数字和下横线，且长度在4~16字符之间!");
            return br;
        }

        if( pw.length() < 6 || pw.length() > 16 ){
            br.setStatus(ConstantSrting.STATUS_FAIL);
            br.setMessage("密码只能包含英文字符和数字，且长度在6~16字符之间!");
            return br;
        }

        try {
            TMgtUser user = userService.login(userName, pw);
            if (user == null) {
                br.setStatus(ConstantSrting.STATUS_FAIL);
                br.setMessage("用户名或密码不正确！");
                return br;
            }
            br.setEntity(user);
            br.setStatus(ConstantSrting.STATUS_SUCCESS);
        } catch (Exception e) {
            logger.error("登录时发生异常：" + e);
            e.printStackTrace();
            br.setStatus(ConstantSrting.STATUS_FAIL);
            br.setMessage("登录时发生异常!");
        }
        return br;
    }

}
