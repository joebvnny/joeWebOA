package com.joebunny.common;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.joebunny.entity.dto.UserInfo;

/**
 * URL资源访问权限拦截器
 */
public class SecurityInterceptor implements HandlerInterceptor {
    
	private List<String> excludeUrls;
	
	public List<String> getExcludeUrls() {
		return excludeUrls;
	}
	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestUrl = requestUri.substring(contextPath.length());
        if(excludeUrls.contains(requestUrl)) {
            return true;
        } else {
            UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
            if(userInfo == null) {
                request.setAttribute("msg", "用户未登录或会话超时，请重新登录！");
                request.getRequestDispatcher("/WEB-PAGE/jsp/error/noSession.jsp").forward(request, response);
                return false;
            } else {
                String urls = userInfo.getFuncUrls();
                List<String> funcUrls = Arrays.asList(urls.split(","));
                if(funcUrls.contains(requestUrl)) {
                    return true;
                } else {
                    request.setAttribute("msg", "没有访问此资源[" + requestUrl + "]的权限！");
                    request.getRequestDispatcher("/WEB-PAGE/jsp/error/noSecurity.jsp").forward(request, response);
                    return false;
                }
            }
        }
    }
    
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
	}
	
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
    }
    
}