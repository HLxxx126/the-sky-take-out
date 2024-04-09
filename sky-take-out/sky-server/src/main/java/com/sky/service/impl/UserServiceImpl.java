package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HLxxx
 * @version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    //WeChatのAPIサービスのアドレス
    public static final String WC_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     * WeChatロクイン
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wcLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());
        //OpenIDがnullであるかどうかを判断し、nullであればログインに失敗したとして例外をスローします.
        if (openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //このユーザーは新たなユーザーであるかどうかを判断する
        User user = userMapper.getByOpenid(openid);
        //新たなユーザーの場合、自動的に登録します
        if (user == null){
            user= User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * WeChatのAPIサービスを呼び出し、現在のWeChatユーザーのOpenIDを取得します
     * @param code
     * @return
     */
    private String getOpenid(String code){
        //WeChatのAPIサービスを呼び出し、現在のWeChatユーザーのOpenIDを取得します
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WC_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
