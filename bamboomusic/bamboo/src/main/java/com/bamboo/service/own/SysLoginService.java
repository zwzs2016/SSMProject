package com.bamboo.service.own;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
import javax.annotation.Resource;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.bamboo.entity.User;
import com.bamboo.entity.out.ResponseEntityResult;
import com.bamboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    @Lazy
    private CaptchaService captchaService;
    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void login(String username, String password, String code, String uuid){
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(code);
        ResponseModel response = captchaService.verification(captchaVO);
        if (!response.isSuccess())
        {
            throw new AuthenticationServiceException("验证码错误："+code);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseEntityResult(String.valueOf(HttpStatus.BAD_REQUEST.value()),"user.jcaptcha.error",null,false));
        }
        /*boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff)
        {
            validateCaptcha(username, code, uuid);
        }*/
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
   /* public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }
*/
    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
//        SysUser sysUser = new SysUser();
//        sysUser.setUserId(userId);
//        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
//        sysUser.setLoginDate(DateUtils.getNowDate());
//        userService.updateUserProfile(sysUser);
    }
}
