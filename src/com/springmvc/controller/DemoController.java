package com.springmvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.pojo.Demo;
import com.springmvc.pojo.DemoList;
import com.springmvc.pojo.People;

/*
 *  springmvc 出现Ambiguous mapping found. Cannot map 'DemoController' bean method
		1. bean重复初始化：使用@controller注解初始化一次，在applicationContext中又定义一次。
		2. 在不同的Controller中定义的映射重复。
 * */

@Controller
public class DemoController{

	@RequestMapping("demo1")
	public String demo1(String name,int age){
		System.out.println("执行 demo1"+" "+name+" "+age);
		return "main.jsp";
	}
	
	@RequestMapping("demo2")
	public String demo2(HttpServletRequest req){
		req.setAttribute("req", "demo2_req");
		System.out.println("执行 demo2");
		return "main.jsp";
	}
	
	@RequestMapping("demo3")
	public String demo3(String username,int age,People peo){
		//参数变量名需要与请求的参数名一致
		//HandlerMethod 中参数是对象类型,请求参数名和对象中属性名对应(get/set 方法).自动注入
		System.out.println("执行 demo3"+" "+username+" "+age+" "+peo.toString());
		return "main.jsp";
	}
	
	@RequestMapping("demo4")
	public String demo4( @RequestParam(value = "name2") String username,
							@RequestParam(value = "age2") int age){
		
		//如果参数变量名与请求的参数名不一致，可以使用@RequestParam
		System.out.println("执行 demo4"+" "+username+" "+age);
		return "main.jsp";
	}
	
	@RequestMapping("demo5")
	public String demo5( @RequestParam(defaultValue = "2") int pageSize,
							@RequestParam(defaultValue = "1") int pageNum){
		
		//如果方法参数是基本数据类型(不是封装类)可以通过@RequestParam 设置默认值.防止没有参数时 500
		System.out.println("执行 demo5"+" "+pageSize+" "+pageNum);
		return "main.jsp";
	}
	
	@RequestMapping("demo6")
	public String demo6( @RequestParam(required = true) String username){
		
		//如果强制要求必须有某个参数@RequestParam(required = true)
		System.out.println("执行 demo6"+" "+username);
		return "main.jsp";
	}
	
	@RequestMapping("demo7")
	public String demo7(String name,int age, @RequestParam("hover")ArrayList<String> CXhover){
		
		//请求参数中包含多个同名参数的获取方式,复选框传递的参数就是多个同名参数
		/*
		 * post表单数据：
					  name	df
					  age	12
					  hover	[…]
						  0	唱歌
						  1	跳舞
						  2	下棋
					  提交	提交查询
		 *
		 * List<String> hover
		 * 报错: Failed to instantiate [java.util.List]: Specified class is an interface
		 * 
		 * ArrayList<String> hover)
		 * ArrayList 没有传值...执行 demo7 df 12 []
		 * 
		 * 
		 * */
		System.out.println("执行 demo7"+" "+name+" "+age+" "+CXhover);
		return "main.jsp";
	}
	
	@RequestMapping("demo8")
	public String demo8(Demo demo){
		
		//请求参数中是对象.属性格式<input type="text" name="peo.username">
		//新建一个类， 对象名和参数中点前面名称对应
		System.out.println("执行 demo8"+" "+demo);
		return "main.jsp";
	}
	
	@RequestMapping("demo9")
	public String demo9(DemoList demo){
		
		//在请求参数中传递集合对象类型参数
		//jsp中：<input type="text" name="peo[0].username">
		//新建一个类， 对象名和参数中点前面名称对应，此时的名称在类中对应一个集合类型
		System.out.println("执行 demo9"+" "+demo);
		return "main.jsp";
	}
	
	
	/*
	 * restful 传值方式.
			简化 jsp 中参数编写格式，在 jsp 中设定特定的格式
				<a href="demo11/121/a_name1">跳转</a>
			在控制器中
				在@RequestMapping 中一定要和请求格式对应
				{名称} 中名称自定义名称
				@PathVariable 获取@RequestMapping 中内容,默认按照方法参数名称去寻找
			
	 * 
	 * */
	@RequestMapping("demo10")
	public String demo10(String name,int age){
		
		System.out.println("执行 demo10"+" "+name+" "+age);
		return "main.jsp";
	}
	
	@RequestMapping("demo11/{age1}/{name}")
	public String demo11(@PathVariable("name")String name, @PathVariable("age1")int age){
		
		//<a href="demo11/a_name1/121">跳转</a> ==>> 客户端报错：HTTP Status 400 - (参数类型不对应)
		//<a href="demo11/121/a_name1">
		//return "main.jsp" ==>> HTTP Status 404 - /SpringMVC/demo11/121/main.jsp
		//demo11/{age1}/{name}执行完，main.jsp 跳转时404，此时main.jsp在当前路径下(demo11/{age1}/{name}),所以找不到main.jsp
		//解决：return "/main.jsp"; 使用绝对路径
		System.out.println("执行 demo11"+" "+name+" "+age);
		return "/main.jsp";
	}
	

	/*
	 * 跳转方式
				默认跳转方式请求转发.
				设置返回值字符串内容
						添加 redirect:资源路径 重定向
						添加 forward:资源路径 或省略 forward: 转发
	 * */
	@RequestMapping("demo12")
	public String demo12(String name,int age){
		
		System.out.println("执行 demo12"+" "+name+" "+age);
		System.out.println("redirect...");
		return "redirect:main.jsp";
	}
	
	/*
	 * 视图解析器
				SpringMVC 会提供默认视图解析器.
				程序员自定义视图解析器
				
					<bean id="viewResolver"
					class="org.springframework.web.servlet.view.InternalResourceViewResolver">
						<property name="prefix" value="/"></property>
						<property name="suffix" value=".jsp"></property>
					</bean>
					
				如果希望不执行自定义视图解析器，在方法返回值前面添加forward:或 redirect:
					return "redirect:main.jsp";
	 * */
	
	//----------------------------------->
	
	
	/*
	 * @ResponseBody
	 * 客户端报错：HTTP Status 406 - ==> 没有导入jackson的jar包
	 * Caused by: java.lang.NoClassDefFoundError: com/xxx.xml/jackson/core/util/DefaultIndenter
	 * ===> SpringMVC 版本与jackson jar包版本的冲突。
	 * 导包：
	 * 		jackson-annotations-2.10.0.jar
	 *		jackson-core-2.10.0.jar
	 *		jackson-databind-2.10.0.jar
	 *
	 *tomcat启动报错：
	 *			严重: Unable to process Jar entry [module-info.class] from Jar 
	 *				[jar:file:/H:/DAY/JavaEE/servlet/apache-tomcat-7.0.62-windows-x64/apache-tomcat-7.0.62/webapps/SpringMVC/WEB-INF/lib/jackson-annotations-2.10.0.jar!/] 
	 *				for annotations
	 *			提示  At least one JAR was scanned for TLDs yet contained no TLDs		
	 *
	 *		问题原因：
	 				从提示信息来看，不处理也可以，但具体处理步骤：
				    Tomcat启动时会扫描大量jar包，如果含有不符合TLD规范的就会出现这个问题
				    注意两个启动：一个是从tomcat的bin目录中启动，另外一个是从Eclipse中或者别的软件中启动，注意看Eclipse启动部分
			解决：
				1.更换jar包：
					jackson-annotations-2.9.8.jar
					jackson-core-2.9.8.jar
					jackson-databind-2.9.8.jar
				2.删除jar包中module-info.class文件(或者)
				3.打开tomcat\conf\catalina.properties文件(或者)..跳过TLD规范检查
					tomcat.util.scan.StandardJarScanFilter.jarsToSkip节点
					增加 jackson-annotations-2.10.0.jar,jackson-core-2.10.0.jar,jackson-databind-2.10.0.jar
					其中增加的节点是	报错的jar包
					注意：Eclipse中的Tomcat删除后重新创建后才生效。

	 * ----------- @ResponseBody-----------
				在方法上只有@RequestMapping 时，无论方法返回值是什么认为需要跳转
				在方法上添加@ResponseBody(恒不跳转)
					如果返回值满足 key-value 形式(对象或 map)
						把响应头设置为 application/json;charset=utf-8
						把转换后的内容输出流的形式响应给客户端
					
					如果返回值不满足 key-value,例如返回值为 String
						把相应头设置为 text/html
						把方法返回值以流的形式直接输出.
						
						如果返回值包含中文,出现中文乱码
							produces 表示响应头中 Content-Type 取值.
							@RequestMapping(value = "demo15",produces = "text/html;charset=utf-8")
	 * */
	@RequestMapping("demo13")
	public void demo13(HttpServletResponse resq) throws IOException{
		PrintWriter out = resq.getWriter();
		out.print("void demo13()...resq");
		out.flush();
		out.close();
	}
	
	@RequestMapping("demo14")
	@ResponseBody
	public People demo14(){
		People peo = new People();
		peo.setUsername("@ResponseBody...resq");
		peo.setAge(14);
		return peo;
	}
	
	@RequestMapping(value = "demo15",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String demo15(){
		People peo = new People();
		peo.setUsername("@ResponseBody...resq");
		peo.setAge(14);
		return "中文";//"@ResponseBody...string"
	}
	
	/*
	 * SpringMVC 作用域传值的几种方式
	 * 四大作用域
			page
				在当前页面不会重新实例化.
			request
				在一次请求中同一个对象,下次请求重新实例化一个request 对象.
			session
				一次会话.
				只要客户端 Cookie 中传递的 Jsessionid 不变,Session 不会重新实例化(不超过默认时间.)
				实际有效时间:
						浏览器关闭.Cookie 失效.
						默认时间.在时间范围内无任何交互.在 tomcat 的web.xml 中配置
			application
				只有在 tomcat 启动项目时实例化.关闭 tomcat 时销毁application
	 * 
	 * */
	//使用原生 Servlet
	@RequestMapping("demo16")
	public String demo16(HttpServletRequest reqParam,HttpSession sessionParam){
		//request作用域
		reqParam.setAttribute("reqParam", "req 的值");
		//session作用域
		HttpSession session = reqParam.getSession();
		session.setAttribute("session", "session 的值");
		sessionParam.setAttribute("sessionParam","sessionParam 的值");
		//application作用域
		ServletContext application =reqParam.getServletContext();
		application.setAttribute("application","application 的值");
		return "/scope.jsp";
	}
	
	//使用 Map 集合
	//会把 map 中内容放在 request 作用域中，根据map集合的key值取值
	//spring 会对 map 集合通过 BindingAwareModelMap 进行实例化
	@RequestMapping(value = "demo17")
	public String demo17(Map<String,Object> map){
		System.out.println(map.getClass());//class org.springframework.validation.support.BindingAwareModelMap
		map.put("mapK", "map的值V");
		return "/scope.jsp";
	}
	
	//使用 SpringMVC 中 Model 接口
	//把内容最终放入到 request 作用域中
	@RequestMapping("demo18")
	public String demo18(Model model){
		model.addAttribute("model", "model 的值");
		return "/scope.jsp";
	}
	
	//使用 SpringMVC 中 ModelAndView 类
	@RequestMapping("demo19")
	public ModelAndView demo19(){
		ModelAndView mav = new ModelAndView("/scope.jsp");
		mav.addObject("mav", "ModelAndView的值");
		return mav;
	}
	
	/*
	 * 文件下载
			访问资源时相应头如果没有设置 Content-Disposition,浏览器默认按照 inline 值进行处理
			inline 能显示就显示,不能显示就下载.
			只需要修改相应头中 Context-Disposition="attachment;filename=文件名"
				attachment : 下载，以附件形式下载
				filename:    下载时显示的下载文件名
	 * */
	@RequestMapping("download")
	public void download(String fileName,HttpServletResponse resp,HttpServletRequest req) {
		FileInputStream fos=null;
		ServletOutputStream sos=null;
		
		//resq.setContentType("");
		resp.setHeader("Content-Disposition", "attachment;filename=test_download.txt");
		
		String path = req.getServletContext().getRealPath("files");
		File file = new File(path, fileName);
		
		try {
			fos = new FileInputStream(file);
			sos = resp.getOutputStream();
			
			byte[] tmp = new byte[1024];
			while(fos.read(tmp)!=-1) {
				sos.write(tmp);
			}
			sos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					if(fos!=null) fos.close();
					if(sos!=null) sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/*
	 * 使用apatch提供的包下载
	 * 导包：
	 * 		commons-fileupload-1.3.1.jar
	 *		commons-io-2.2.jar
	 * */
	@RequestMapping("down")
	public void downloadForAp(String fileName,HttpServletResponse resp,HttpServletRequest req) throws Exception {
		resp.setHeader("Content-Disposition", "attachment;filename=test_download_Ap.txt");
		
		String path = req.getServletContext().getRealPath("files");
		File file = new File(path, fileName);
		
		byte[] byteArray = FileUtils.readFileToByteArray(file);
		ServletOutputStream os = resp.getOutputStream();
		os.write(byteArray);
		os.flush();
		os.close();
	}
	
	/*
	 * 文件上传
			基于 apache 的 commons-fileupload.jar 完成文件上传.
			导包：commons-fileupload.jar
				基于SpringMVC 的MultipartResolver 作用:
									把客户端上传的文件流转换成 MutipartFile 封装类.
									通过 MutipartFile 封装类获取到文件流
				
				 * Servlet-based {@link MultipartResolver} implementation for
				 * <a href="http://commons.apache.org/proper/commons-fileupload">Apache Commons FileUpload</a>
				 * 1.2 or above.
				public class CommonsMultipartResolver extends CommonsFileUploadSupport 
														implements MultipartResolver, ServletContextAware
														
				<bean id="multipartResolver" 
						class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>	
				
				------
				
				客户端：
				表单数据类型分类，在<form>的 enctype 属性控制表单类型
					application/x-www-form-urlencoded		[默认值 ]普通表单数据(上传少量文字信息)
					text/plain 								大文字量时使用的类型 ，邮件，论文
					multipart/form-data 					表单中包含二进制文件内容
	 
				 <form action="up" enctype="multipart/form-data" method="post">
						姓名：<input type="text" name="name" > <br/>
						文件：<input type="file" name="file" > <br/>
							<input type="submit" value="提交" > <br/>
				</form>
	 * 
	 * 
	 * 
	 * */
	@RequestMapping("up")
	public void upload(MultipartFile file,String name,HttpServletResponse resp) throws IOException{
		
		String fileName = file.getOriginalFilename();
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html;charset=utf-8");
		
		if(fileName!=""){
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			if(suffix.equalsIgnoreCase(".jpg")) {
				String uuid = UUID.randomUUID().toString();
				//System.out.println("G:\\test"+uuid+suffix);
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File("G:\\test\\"+uuid+suffix));
				writer.print("上传成功");
			} else {
				writer.print("仅支持jpg格式图片");
			}
		} else {
			writer.print("请选择上传文件");
		}
		
		//return "upload.jsp";
	}
}
