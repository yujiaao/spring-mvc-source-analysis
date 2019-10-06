package com.github.yujiaao.reloading.filter;

import com.github.yujiaao.reloading.Application;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(description = "my web filter", urlPatterns = "/", filterName = "global-filter")
public class GlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("GlobalFilter init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       if(Application.Holder.getInstance().isRunning()){
           chain.doFilter(request, response);
       }else {
           response.getWriter().println("loading...");
       }
    }


    @Override
    public void destroy() {

    }
}
