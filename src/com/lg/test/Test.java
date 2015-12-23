package com.lg.test;

import java.util.ArrayList;

import com.lg.dao.MapService;
import com.lg.domain.Map;
import com.lg.util.SqlHelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//从数据库中获取经纬度数据
//		
//			String sql="select * from map";
//			
//			SqlHelper sqlHelper=new SqlHelper();
//			ArrayList al = sqlHelper.executeQuery(sql, null);
//			if(al.size()==1){
//				//Object[] object = (Object[]) al.get(0);
//				Map map=new Map();
//				System.out.println(1111);
//				Object[] ojs= (Object[]) al.get(0);
//				System.out.println(ojs[1]);
//				map.setLongitude(Float.parseFloat(ojs[1].toString()));
//				System.out.println(map.getLongitude());

				
				

		MapService mapService=new MapService();
		mapService.updateMap("112.3", "123.6");
			
			
		

	}

}
