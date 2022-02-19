package com.Cloud;//导入必需的 java 库


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;


//扩展 HttpServlet 类
@WebServlet(value = "/MyCloud",name = "MainResponse")
public class DisplayHeader extends HttpServlet {

    public String[] keyChain = new String[4];
    // 处理 GET 方法请求的方法
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {



       PrintWriter out = response.getWriter();
        String title = "使用 GET 方法读取表单数据";
        // 处理中文
        //举例：var SignUPRequest = "?Choice=SignUP&email=" + emailSU + '&usrname=' + usrNameSU + '&psd=' + pwdSU;
        String name =new String(request.getParameter("Choice").getBytes(ISO_8859_1), UTF_8);

        //进行数据读入
        Arrays.fill(keyChain,"");
        keyChain[0] = name;
        keyChain[1] = request.getParameter("email");
        keyChain[2] = request.getParameter("pwd");
        keyChain[3] = request.getParameter("usrname");

        int chic;
        if(name.equals("SignIN")){
            chic = 1;
        }else{chic = 2;}

        //todo:JDBC编程操作进行登录
        String strResp = null;
        try {
            strResp = JdbcSubModule.InnerStart(chic,keyChain[1],keyChain[2],keyChain[3]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.write(strResp);

        /*String docType = "<!DOCTYPE html> \n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>服务选择</b>："
                + name + "\n" +
                "  <li><b>邮箱</b>："
                + request.getParameter("email") + "\n" +
                "\n" +
                "  <li><b>密码</b>："
                + request.getParameter("pwd") + "\n" +
                "\n" +
                "  <li><b>用户名</b>："
                + request.getParameter("usrname") + "\n" +
                "\n" +
                "</body></html>");*/


    }

    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}