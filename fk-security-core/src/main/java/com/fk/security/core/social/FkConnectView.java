package com.fk.security.core.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author fankun
 * @date 2018/3/29 18:34
 */
public class FkConnectView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if(map.get("connection") == null){
            response.getWriter().write("<h3>解绑成功</h3>");
        }else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}
