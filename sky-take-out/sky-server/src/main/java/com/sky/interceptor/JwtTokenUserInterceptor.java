package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

		//获取登录数据员工id
        //Long employeeId = (Long) request.getSession().getAttribute("employee");

        try {
            //获取请求头的令牌数据
            String token = request.getHeader(jwtProperties.getUserTokenName());
            //解析令牌
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            //Long empId = (Long)claims.get(JwtClaimsConstant.EMP_ID) 注意：这是错误的，Long类型不可以这样转换，会发生报错
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString()) ;  //这是正确的
            log.info("登录的用户id：{}",userId);

            //将员工id存储到ThreadLocal
            BaseContext.setCurrentId(userId);

            //如果有登录数据，代表一登录，放行
            return true;
        } catch (Exception e) {
            //否则，发送未认证错误信息
            //throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
            //否则，发送未认证错误信息
            response.setStatus(401);
            return false;
        }
    }
}
