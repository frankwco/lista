package util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
 
//https://blog.sixthpoint.com/jsf-detect-session-timeouts-with-web-filter/
@WebFilter(filterName = "SessionTimeoutFilter", urlPatterns = "*.jsf")
public class SessionTimeoutFilter implements Filter {

    private final String timeoutPage = "/index.jsf";

    private final String loginPage = "/login.jsf";
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
    ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            if (isRequireSessionControl(httpServletRequest) && isSessionInvalid(httpServletRequest)) {

                if (isAJAXRequest(httpServletRequest)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"")
                        .append(httpServletRequest.getContextPath() + timeoutPage).append("\"></redirect></partial-response>");
                    httpServletResponse.setHeader("Cache-Control", "no-cache");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.setContentType("text/xml");
                    PrintWriter pw = response.getWriter();
                    pw.println(sb.toString());
                    pw.flush();
                    return;
                }

                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + timeoutPage);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private boolean isAJAXRequest(HttpServletRequest request) {
        boolean check = false;
        String facesRequest = request.getHeader("Faces-Request");
        if (facesRequest != null && facesRequest.equals("partial/ajax")) {
            check = true;
        }
        return check;
    }

    private boolean isRequireSessionControl(HttpServletRequest httpServletRequest) {
        String requestPath = httpServletRequest.getRequestURI();
        return !requestPath.contains(timeoutPage) && !requestPath.contains(loginPage)
                        && !requestPath.contains("javax.faces.resource");
    }

    private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestedSessionId() != null && !httpServletRequest.isRequestedSessionIdValid();
    }
}