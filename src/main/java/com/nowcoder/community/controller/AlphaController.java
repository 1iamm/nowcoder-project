package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.PartHttpMessageWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    //没有返回值，因为通过response对象可以直接输出
    //从自变量中直接可以传入request对象以及response对象
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());  //获取请求路径
        Enumeration<String> enumeration = request.getHeaderNames(); //得到请求行的所有的key，请求行是key-value结构
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);  //通过key获得value
            System.out.println(name + ":" + value);
        }

        System.out.println(request.getParameter("code"));  //获取请求中的code参数

        // response是返回相应数据的对象
        // 1.需要设置返回数据的类型
        response.setContentType("text/html;charset=utf-8"); // 返回html的文本数据
        // 2.获取输出流
        try (
                PrintWriter writer = response.getWriter(); //在try()括号中创建，try中运行完后会自动close
        ) {
            writer.write("<h1>牛客网</h1>"); //输出一级标题牛客网
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 处理GET请求

    // 假设查询所有的学生 /students?current=1&limit=20 当前页以及每页的数据量
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current, //@RequestParam(name = "current") int current表示获取对应名字的参数
            @RequestParam(name = "limit", required = false, defaultValue = "20") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "student/{id}", method = RequestMethod.GET)  //"student/{id}"表示{id}是变量
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) { //@PathVariable()表示路径里的变量
        System.out.println(id);
        return "a student";
    }

    // POST请求
    @RequestMapping(path = "student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) { //直接声明参数，当参数的名字与表单中的名字一致时就会传过来
        System.out.println(name);
        System.out.println(age);
        return "success!";
    }

    // 响应动态HTML数据
    @RequestMapping(path = "teacher", method = RequestMethod.GET)
    // 返回HTML时不需要加@ResponseBody
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 15);
        mav.setViewName("/demo/view"); //设置模板的名字
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) { //model
        model.addAttribute("name", "北大");
        model.addAttribute("age", "120");
        return "/demo/view"; //代表返回HTML路径，view
    }

    // 响应JSON数据（异步请求，当前网页不刷新，但仍然访问了服务器，如注册时判断昵称是否被使用）
    // 得到JAVA对象 -> JSON字符串 ->浏览器得到JS对象，
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody //加此注解表示不返回HTML
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp; //会自动转成JSON字符串
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody //加此注解表示不返回HTML
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "里斯");
        emp.put("age", 50);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 45);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "马六");
        emp.put("age", 88);
        emp.put("salary", 8000.00);
        list.add(emp);

        return list; //会自动转成JSON字符串
    }

    // cookie示例，浏览器初次访问服务器，服务器发送cookie给浏览器
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody //即不返回HTML格式
    public String setCookie(HttpServletResponse httpServletResponse) { //利用response传送数据给浏览器
//        创建cookie，cookie为Key-value结构并且只能存一对key-value
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
//        设置cookie生效范围
        cookie.setPath("/community/alpha");
//        cookie生存时间，单位是s
        cookie.setMaxAge(60 * 10);
//        发送cookie
        httpServletResponse.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

//    session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody  //session只需要声明，springMVC会自动注入
    public String setSession(HttpSession session) {
        //session可以存多个key-value，因为存在服务端不用来回传
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody  //session只需要声明，springMVC会自动注入
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

}






































