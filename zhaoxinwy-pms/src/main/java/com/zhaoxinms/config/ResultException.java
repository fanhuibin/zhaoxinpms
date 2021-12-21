package com.zhaoxinms.config;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSON;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ResultException {

    @Autowired
    private RedisUtil redisUtil;

    @ResponseBody
    @ExceptionHandler(value = LoginException.class)
    public ActionResult loginException(LoginException e) {
        ActionResult result = ActionResult.fail(e.getMessage());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ActionResult runtimeException(RuntimeException e) {
    	e.printStackTrace();
        ActionResult result = ActionResult.fail(e.getMessage());
        return result;
    }


    /**
     * 自定义异常内容返回
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = DataException.class)
    public ActionResult dataException(DataException e) {
        ActionResult result = ActionResult.fail(e.getMessage());
        return result;
    }


    @ResponseBody
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public ActionResult sqlException(SQLSyntaxErrorException e) {
        ActionResult result;
        log.error(e.getMessage());
        if(e.getMessage().contains("Unknown database")){
            result = ActionResult.fail("请求失败");
        }else{
            result = ActionResult.fail("数据库异常");
        }
        return result;
    }
    
    @ResponseBody
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ActionResult sqlException(BadSqlGrammarException e) {
        ActionResult result;
        log.error(e.getMessage());
        if(e.getMessage().contains("Unknown database")){
            result = ActionResult.fail("请求失败");
        }else{
            result = ActionResult.fail("数据库异常");
        }
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ActionResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>(16);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (int i = 0; i < allErrors.size(); i++) {
            String s = allErrors.get(i).getCodes()[0];
            //用分割的方法得到字段名
            String[] parts = s.split("\\.");
            String part1 = parts[parts.length - 1];
            map.put(part1, allErrors.get(i).getDefaultMessage());
        }
        String json = JSON.toJSONString(map);
        ActionResult result = ActionResult.fail(json);
        return result;
    }

   
}
