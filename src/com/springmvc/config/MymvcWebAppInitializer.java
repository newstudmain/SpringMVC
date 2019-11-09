package com.springmvc.config;

import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.servlet.support.
								AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/*
 * Base class for org.springframework.web.WebApplicationInitializer 
 * 	implementations 
 * that register a DispatcherServlet configured with annotated classes, 
 * 	e.g. Spring's @Configuration classes. 
 * 
 *  我们可能只需要知道扩展AbstractAnnotation-ConfigDispatcherServletInitializer的任意类
 *  都会自动地配置Dispatcher-Servlet和Spring应用上下文， Spring的应用上下文会位于应用程序的Servlet上下文之中。
 *  在Servlet 3.0环境中， 容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类， 
 *  如果能发现的话， 就会用它来配置Servlet容器。
 *  
 *  Spring提供了这个接口的实现， 名为SpringServletContainerInitializer， 
 *  这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。
 *  Spring 3.2引入了一个便利的WebApplicationInitializer基础实现， 也就是AbstractAnnotationConfigDispatcherServletInitializer。 
 *  因为我们的MymvcWebAppInitializer扩展了AbstractAnnotationConfig DispatcherServlet-Initializer
 *  同时也就实现了WebApplicationInitializer， 因此当部署到Servlet 3.0容器中的时候， 容器会自动发现它， 并用它来配置Servlet上下文。
 * 
 * public abstract class AbstractAnnotationConfigDispatcherServletInitializer
		extends AbstractDispatcherServletInitializer
		
   public abstract class AbstractDispatcherServletInitializer extends AbstractContextLoaderInitializer
   
   
 * */
public class MymvcWebAppInitializer //extends AbstractAnnotationConfigDispatcherServletInitializer
{
	
	/*
	 * 第一个方法是getServletMappings()， 
	 * 它会将一个或多个路径映射到DispatcherServlet上。
	 * 在本例中， 它映射的是"/"， 这表示它会是应用的默认Servlet。 它会处理进入应用的所有请求。
	 * */
//	@Override
//	protected String[] getServletMappings() {
//		return new String[] {"/"};
//	}
//
//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		return new Class<?>[] {RootConfig.class};
//	}
//
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return new Class<?>[] {WebConfig.class};
//	}


}

class WebConfig{}

class RootConfig{}

/*
 * 两个应用上下文之间的故事
		当DispatcherServlet启动的时候， 它会创建Spring应用上下文， 并加载配置文件或配置类中所声明的bean。 
		在getServletConfigClasses()方法中， 我们要求DispatcherServlet加载应用上下文时， 
		使用定义在WebConfig配置类（使用Java配置） 中的bean。

		但是在Spring Web应用中， 通常还会有另外一个应用上下文。 
		另外的这个应用上下文是由ContextLoaderListener创建的。
		我们希望DispatcherServlet加载包含Web组件的bean， 如控制器、 视图解析器以及处理器映射， 
		而ContextLoaderListener要加载应用中的其他bean。 这些bean通常是驱动应用后端的中间层和数据层组件。
		
		实际上， AbstractAnnotationConfigDispatcherServletInitializer会同时创建
		DispatcherServlet和ContextLoaderListener。 

		GetServlet-ConfigClasses()方法返回的带有@Configuration注解的类
			将会用来定义DispatcherServlet应用上下文中的bean。 
		getRootConfigClasses()方法返回的带有@Configuration注解的类
			将会用来配置ContextLoaderListener创建的应用上下文中的bean。

		根配置定义在RootConfig中， DispatcherServlet的配置声明在WebConfig中。
		通过AbstractAnnotationConfigDispatcherServlet-Initializer
		来配置DispatcherServlet是传统web.xml方式的替代方案。 如果你愿意的话， 
		可以同时包含web.xml和AbstractAnnotationConfigDispatcher-ServletInitializer， 
		但这其实并没有必要。如果按照这种方式配置DispatcherServlet， 而不是使用web.xml的话， 
		那唯一问题在于它只能部署到支持Servlet 3.0的服务器中才能正常工作， 如Tomcat 7或更高版本。 
		Servlet 3.0规范在2009年12月份就发布了， 因此很有可能你会将应用部署到支持Servlet 3.0的Servlet容器之中。

		如果你还没有使用支持Servlet 3.0的服务器， 
		那么在AbstractAnnotation-ConfigDispatcherServletInitializer子类中
		配置DispatcherServlet的方法就不适合你了。 你别无选择， 只能使用web.xml了。 
		我们将会在第7章学习web.xml和其他配置选项。 但现在，我们先看一下程序清单5.1中所引用的WebConfig和RootConfig， 
		了解一下如何启用Spring MVC。
 * */
