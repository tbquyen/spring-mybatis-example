/**
 * 
 */
package com.github.tbquyen.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author QUYENTB
 *
 */
public class ErrorHandlerFilter implements Filter {
	public static final String ERROR_HANDLER_FILTER = "ERROR_HANDLER_FILTER";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} catch (Throwable e) {
			request.setAttribute(ERROR_HANDLER_FILTER, e);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/500.html");
			dispatcher.forward(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
