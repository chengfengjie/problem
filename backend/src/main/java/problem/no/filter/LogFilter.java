package problem.no.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by chengfj on 2017/10/11.
 */
public class LogFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
