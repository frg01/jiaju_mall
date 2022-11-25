package com.hspedu.furns.filter;

import com.hspedu.furns.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

/**控制事务的Filter
 * @author: guorui fu
 * @versiion: 1.0
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtilsByDruid.commit();
        } catch (Exception e) {
            //只有在try中出现了异常，才会进行catch{}
            //才会进行回滚
            JDBCUtilsByDruid.rollBack();
            //为了抛出异常给tomcat  tomcat根据errorPage 来显示
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
