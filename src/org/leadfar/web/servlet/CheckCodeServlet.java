package org.leadfar.web.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckCodeServlet extends HttpServlet {
	public static final String CHECK_CODE = "checkcode";
	private String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private int size = 4;
	private int width = 44;
	private int height = 20;
	
	@Override
	public void init() throws ServletException {
		this.code = this.getServletConfig().getInitParameter("code");
		this.size = Integer.parseInt(this.getInitParameter("size"));
		this.width = Integer.parseInt(this.getInitParameter("width"));
		this.height = Integer.parseInt(this.getInitParameter("height"));
	}
	/**
	 * 获得随机的颜色强度
	 * 获得0 - 255的随机数
	 * @return
	 */
	private int getColor(){
		return (int)(255 * Math.random());
	}
	
	/**
	 * 获得随机距离
	 * @return
	 */
	public int getDistance(){
		return 4 + (int)(7 * Math.random());
	}
	/**
	 * 获得基线随机偏移量
	 * @return
	 */
	public int getBaseLine(){
		return 4 - (int)(8 * Math.random());
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer sb = new StringBuffer();
		//随机生成一个验证码
		for(int i = 0 ; i < size ; i++){
			int index = (int)(code.length() * Math.random());
			sb.append(code.charAt(index));
		}
		//把验证码存到session当中
		String checkcode = sb.toString();
		request.getSession().setAttribute(CHECK_CODE, checkcode);
		//根据验证码生成图片，返回给客户端浏览器
		BufferedImage img = new BufferedImage(44, 20, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = (Graphics2D)img.getGraphics();//取出画笔
		
		//背景填充成白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//文字起始坐标
		int x = 5;
		int y = 14;
		
		for(int i = 0 ; i < checkcode.length() ;i++){
			String s = String.valueOf(checkcode.charAt(i));
			//获取R,G,B三原色
			int r,gg,b;
			r = getColor();
			gg = getColor();
			b = getColor();
			//画笔改前景色
			g.setColor(new Color(r,gg,b));
			//随机位置
			int d , bl ;
			d = getDistance();
			bl = getBaseLine();
			int t = x + (d * i);
			y = y + bl;
			//画出验证码
			g.drawString(s, t, y);
		}
		
		//加杂点
		for(int j = 0 ; j < 50 ; j++){
			int x1 , y1;
			x1 = 8 + (int)(27 * Math.random());
			y1 = 2 + (int)(16 * Math.random());
			//获取R,G,B三原色
			int r,gg,b ,a;
			r = getColor();
			gg = getColor();
			b = getColor();
			a = getColor();
			g.setColor(new Color(r,gg,b,a));
			
			g.fillOval(x1, y1, 2,2);
		}
		
		ImageIO.write(img,"png",response.getOutputStream());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
