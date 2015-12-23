/*
 * ����ݿ�Ĳ�����
 */
package com.lg.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.servlet.jsp.tagext.TryCatchFinally;

/*
 * ����һ�������ݿ�ĺù�����
 */
public class SqlHelper {
	//��������Ҫ�ı�
	private static Connection ct=null;
	private static PreparedStatement ps=null;
	private static ResultSet rs=null;
	private static CallableStatement cs=null;
	 
	//l����ݿ�Ĳ���
	private static String url="";
	private static String username="";
	private static String passwd="";
	private static String driver="";
	//�������ļ�
	static Properties pp=null;
	static InputStream fis=null;
	
	//������ֻ��һ��
	static
	{
			try {
				//��dbinfo.properties�ļ��ж�ȡ������Ϣ
				pp=new Properties();
				//fis=new FileInputStream("dbinfo.properties");//->tomcat����Ŀ¼��
				//������ʹ��java web��ʱ�򣬶�ȡ�ļ�Ҫ���������[��Ϊ�������ȥ��ȡ��Դ��ʱ��Ĭ�ϵ���Ŀ·��src]
				fis=SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
				pp.load(fis);
				driver=pp.getProperty("driver");
				url=pp.getProperty("url");
				username=pp.getProperty("username");
				passwd=pp.getProperty("passwd");
				Class.forName(driver);
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally
		{
			try 
			{
				fis.close();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fis=null;
		}
	}
	
	public static Connection getConnection()
	{
		try 
		{
			ct=DriverManager.getConnection(url,username,passwd);
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ct;
	}
	
	
	//��ҳ���⣿
	public static ResultSet executeQuery()
	{
		return null;
	}
	
	
	//���ô洢��̣��ַ���ֵResult
	public static CallableStatement callpro2
	(String sql,String []inparameters,Integer []outparameters)
	{
		try {
			ct=getConnection();
			cs=ct.prepareCall(sql);
			if(inparameters!=null)
			{
				for(int i=0;i<inparameters.length;i++)
				{
					cs.setObject(i+1, inparameters[i]);
				}
			}
			//��out����ֵ
			if(outparameters!=null)
			{
				for(int i=0;i<outparameters.length;i++)
				{
					cs.registerOutParameter(outparameters[i], inparameters.length+1+i);
				}
			}
			cs.execute();
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally
		{
			//��ʱ���ر�
		}
		return cs;
	}
	
	
	//���ô洢���
	//sql  �� {call ��̣�������������}
	public static void callProl(String sql, String []parameters )
	{
		try {
			ct=getConnection();
			cs=ct.prepareCall(sql);
			
			//��ֵ
			if(parameters!=null)
			{
				for(int i=0; i<parameters.length;i++)
				{
					cs.setObject(i+1, parameters[i]);
				}
			}
			cs.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally
		{
			close(rs,cs,ct);
		}
	}
	
	//�Բ�ѯ�����
	//����������� ����Ĺ��� ����ʹ����Դ ����ر���Դ 	
	public static ArrayList executeQuery(String sql, String []parms)
	{
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			//���ʺŸ�ֵ
			if(parms!=null&&!parms.equals(""))
			{
				for(int i=0;i<parms.length;i++)
				{
					pstmt.setString(i+1, parms[i]);
				}
			}
			rs=pstmt.executeQuery();
			ArrayList al=new ArrayList();
			ResultSetMetaData rsmd=rs.getMetaData();
			int column=rsmd.getColumnCount();//������Եõ���Ĳ�ѯ���
			
			while(rs.next())
			{
				Object[] ob=new Object[column];
				for(int i=1;i<=column;i++)
				{
					ob[i-1]=rs.getObject(i);
				}
				al.add(ob);
			}
			return al;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("executeSqlResultset ������?"+e.getLocalizedMessage());
		}finally
		{
			//�ر���Դ
			close(rs,pstmt,conn);
		}
	}
	
	//ͳһ��select
	public static ResultSet executeQuery3(String sql, String []parameters)
	{
		ct=getConnection();
		
		try {
			ps=ct.prepareStatement(sql);
			if(parameters!=null)
			{
			 for (int i=0;i<parameters.length;i++)
			 {
				ps.setString(i+1, parameters[i]);
			 }
			}
			rs=ps.executeQuery();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}finally
		{
			close(rs, ps, ct);
		}
		return rs;
	}  
	
	
	//����ж��update/delete/insert ����Ҫ�������
	public static void executeUpdate2(String []sql,String [][]parameters)
	{
		try {
			//����
			ct=getConnection();
			ct.setAutoCommit(false);
			for(int i=0;i<sql.length;i++)
			{
				if(parameters!=null)
				{
					ps=ct.prepareStatement(sql[i]);
					for(int j=0;j<parameters[i].length;j++)
					{
						ps.setString(j+1, parameters[i][j]);
						
					}
				}
				ps.executeUpdate();
			}
			ct.commit();
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
			
		}finally
		{
			close(rs,ps,ct);
		}
	}
	
	
	//��дһ��update/delete/insert
	//sql��ʽ�� update ���� set�ֶ���=? where �ֶ�=?
	//parametersӦ����{"abc"}��
	public static void executeUpdate(String sql,String []parameters)
	{
		
		try {
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			
			if(parameters!=null)
			{
				for(int i=0;i<parameters.length;i++)
				{
					ps.setString(i+1, parameters[i]);
					
				}	
			}
			//ִ��
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();//�����׶�
			//�����쳣 �׳������쳣���Ը���øú���ĺ���һ��ѡ��
			//���Դ��?Ҳ���Է�����
			throw new RuntimeException(e.getMessage());
		}finally
		{
			close(rs,ps,ct);
			
		}
		
	}
	
	//�ر���Դ�ĺ���
	public static void close(ResultSet rs,Statement ps,Connection ct)
	{
		if(rs!=null)
		{
			try 
			{
				rs.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		rs=null;
		if(ps!=null)
		{
			try 
			{
				ps.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		ps=null;
		if(ct!=null)
		{
			try 
			{
				ct.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		ct=null;
	}
	
	public static Connection getCt() 
	{
		return ct;
	}
	public static PreparedStatement getPs() 
	{
		return ps;
	}
	public static ResultSet getRs() 
	{
		return rs;
	}
	public static CallableStatement getCs() 
	{
		return cs;
	}
} 


