package com.springmvc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;


public class Test {

	public static void main(String[] args) {
		ApplicationContext ac = null;
		HandlerMapping hm =null;
		HandlerAdapter ha =null;
		ViewResolver vr =null;
	/*
	 * public class UrlBasedViewResolver extends AbstractCachingViewResolver implements Ordered 
	 * Prefix for special view names that specify a redirect URL (usually
	 * to a controller after a form has been submitted and processed).
	 * Such view names will not be resolved in the configured default
	 * way but rather be treated as special shortcut.
	 *
		public static final String REDIRECT_URL_PREFIX = "redirect:";

	/*
	 * Prefix for special view names that specify a forward URL (usually
	 * to a controller after a form has been submitted and processed).
	 * Such view names will not be resolved in the configured default
	 * way but rather be treated as special shortcut.
	 *
		public static final String FORWARD_URL_PREFIX = "forward:";
		private Class<?> viewClass;
		private String prefix = "";
		private String suffix = "";
		private String contentType;
		private boolean redirectContextRelative = true;
		private boolean redirectHttp10Compatible = true;
		private String requestContextAttribute;
		private final Map<String, Object> staticAttributes = new HashMap<String, Object>();
		private Boolean exposePathVariables;
		private Boolean exposeContextBeansAsAttributes;
		private String[] exposedContextBeanNames;
		private String[] viewNames;
		private int order = Integer.MAX_VALUE;
	* */
	}
}

/*
 * 
 * public class DispatcherServlet extends FrameworkServlet
 * 								
 * 		public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware
 * 						protected final void initServletBean() throws ServletException {
							this.webApplicationContext = initWebApplicationContext();
							initFrameworkServlet();
						}
 * 				public abstract class HttpServletBean extends HttpServlet implements EnvironmentCapable, EnvironmentAware
	  							public final void init() throws ServletException {
									...
									initServletBean();
									...
								}
 * ------
 * 	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext rootContext =
				WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		WebApplicationContext wac = null;
		
//SpringMVC 和 Spring 容器的父子关系
		if (this.webApplicationContext != null) {
			// A context instance was injected at construction time -> use it
			wac = this.webApplicationContext;
			if (wac instanceof ConfigurableWebApplicationContext) {
				ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
				if (!cwac.isActive()) {
					// The context has not yet been refreshed -> provide services such as
					// setting the parent context, setting the application context id, etc
					if (cwac.getParent() == null) {
						// The context instance was injected without an explicit parent -> set
						// the root application context (if any; may be null) as the parent
						cwac.setParent(rootContext);
					}
					configureAndRefreshWebApplicationContext(cwac);
				}
			}
		}
		if (wac == null) {
			// No context instance was injected at construction time -> see if one
			// has been registered in the servlet context. If one exists, it is assumed
			// that the parent context (if any) has already been set and that the
			// user has performed any initialization such as setting the context id
			wac = findWebApplicationContext();
		}
		if (wac == null) {
			// No context instance is defined for this servlet -> create a local one
			wac = createWebApplicationContext(rootContext);
		}

		if (!this.refreshEventReceived) {
			// Either the context is not a ConfigurableApplicationContext with refresh
			// support or the context injected at construction time had already been
			// refreshed -> trigger initial onRefresh manually here.
			onRefresh(wac);
		}

		if (this.publishContext) {
			// Publish the context as a servlet context attribute.
			String attrName = getServletContextAttributeName();
			getServletContext().setAttribute(attrName, wac);
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Published WebApplicationContext of servlet '" + getServletName() +
						"' as ServletContext attribute with name [" + attrName + "]");
			}
		}

		return wac;
	}

 * 
 * */
