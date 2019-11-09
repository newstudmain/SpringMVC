package com.springmvc.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * 一.自定义拦截器
			跟过滤器比较像的技术，发送请求时被拦截器拦截,在控制器的前后添加额外功能.
				跟 AOP 区分开:
						 AOP 	在特定方法前后扩充(对 ServiceImpl)
						  拦截器	请求的拦截.针对点是控制器方法.(对 Controller)
				SpringMVC 拦截器和 Filter 的区别:
						  拦截器	只能拦截器 Controller
						 Filter 可以拦截任何请求.

	实现自定义拦截器的步骤:
			新建类实现 HandlerInterceptor implements HandlerInterceptor
			在 springmvc.xml 配置拦截器需要拦截哪些控制器
				拦截所有控制器
							 <mvc:interceptors>
								<bean id="demoIntercept" class="com.springmvc.intercept.DemoIntercept"></bean>
							 </mvc:interceptors>
				拦截特定的的 url
							<mvc:interceptors>
								<mvc:interceptor>
									<mvc:mapping path="/demo"/>
									<bean id="demoIntercept" class="com.springmvc.intercept.DemoIntercept"></bean>
								</mvc:interceptor>
			 				</mvc:interceptors>
	
	拦截器栈
			多个拦截器同时生效时,组成了拦截器栈，顺序:先进后出.
			执行顺序和在 springmvc.xml 中配置顺序有关
				设置先配置拦截器 A 在配置拦截器 B 执行顺序为
					preHandle(A) --> preHandle(B) --> 
					控制器方法 --> postHandle(B) --> 
					postHanle(A) --> 
					JSP --> 
					afterCompletion(B) --> afterCompletion(A)
 * */

public class DemoIntercept implements HandlerInterceptor{
	
	//在进入控制器之前执行
	//如果返回值为 false,阻止进入控制器
	//控制代码
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//被拦截的控制器
		System.out.println("handler: "+handler);//com.springmvc.controller.DemoController.demo2
		System.out.println("preHandle");
		return true;
	}

	
	//控制器执行完成,进入到 jsp 之前执行.
	//日志记录.
	//敏感词语过滤
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("往 " +modelAndView.getViewName()+" 跳");
		//demo2没有设置Model值
		System.out.println("model的值： "+modelAndView.getModel());//{}
		//demo18 Model 中设置的属性值可以被修改
		String str = modelAndView.getModel().get("model").toString();
		String newstr = str.replace("model", "new_model");
		modelAndView.getModel().put("model", newstr);
		System.out.println("model的值： "+modelAndView.getModel());
		
		System.out.println("postHandle");
	}

	
	//jsp 执行完成后执行
	//记录执行过程中出现的异常.
	//可以把异常记录到日志中
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		
	}

}
