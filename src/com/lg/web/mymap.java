package com.lg.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lg.dao.MapService;

public class mymap extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		data(request,response);
		
	}


	private void data(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//用于接收设备传过来的经纬度信息 再不断向数据库插入数据
		//int s1 = Integer.parseInt(request.getParameter("result1"));
		System.out.println(request.getParameter("result"));
		String str=request.getParameter("result");
		//对字符串进行分割
		String[] result=str.split(",");
//		for(int i=0;i<result.length;i++){
//			System.out.println(result[i]);
//		}
		String longitude=result[2];
		String latitude=result[3];
		System.out.println(longitude+" ****** "+latitude);
		if(!longitude.equals("")&&(!latitude.equals(""))){
			//获得经纬度更新数据库
			System.out.println("空的");
			MapService mapService=new MapService();
			mapService.updateMap(longitude, latitude);
		}else{
			System.out.println("更新数据库失败，经纬度数据为空！");
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("收到的结果:"+str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
