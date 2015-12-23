package com.lg.dao;

import java.util.ArrayList;
import java.util.List;

import com.lg.domain.Map;
import com.lg.util.SqlHelper;

public class MapService {
	
	//从数据库中获取经纬度数据
	public Map getMapInfo(){
		String sql="select * from map";
		
		SqlHelper sqlHelper=new SqlHelper();
		ArrayList al = sqlHelper.executeQuery(sql, null);
		if(al.size()>0){
			//永远取出数据库的最后一条数据
			Object[] object = (Object[]) al.get(al.size()-1);
			Map map=new Map();
			//map.setId(Integer.parseInt(object[1].toString()));
			
			map.setLongitude(Float.parseFloat(object[1].toString()));
			System.out.println();
			map.setLatitude(Float.parseFloat(object[2].toString()));
			return map;
		}else{
		return null;
		}
		
	}
	//根据几个维度更新数据库
	@SuppressWarnings("static-access")
	public void updateMap(String longitude,String latitude ){
		String sql="update map set longitude=?,latitude=?";
		
		SqlHelper helper=new SqlHelper();
		String[] parameters={longitude,latitude};
		helper.executeUpdate(sql, parameters);
		
	}

}
