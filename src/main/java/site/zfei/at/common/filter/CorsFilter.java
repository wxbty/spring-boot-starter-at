package site.zfei.at.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {

    static final String ORIGIN = "Origin";

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String origin = request.getHeader(ORIGIN);
        if(origin == null) {
            origin = request.getHeader("Referer");
        }

        response.setHeader("Access-Control-Allow-Origin", origin);//* or origin as u prefer
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type, authorization, accesstoken");

        if (request.getMethod().equals("OPTIONS"))
            response.setStatus(HttpServletResponse.SC_OK);
        else
            filterChain.doFilter(request, response);

    }
}
