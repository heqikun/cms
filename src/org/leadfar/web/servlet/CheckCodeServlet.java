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
	 * ����������ɫǿ��
	 * ���0 - 255�������
	 * @return
	 */
	private int getColor(){
		return (int)(255 * Math.random());
	}
	
	/**
	 * ����������
	 * @return
	 */
	public int getDistance(){
		return 4 + (int)(7 * Math.random());
	}
	/**
	 * ��û������ƫ����
	 * @return
	 */
	public int getBaseLine(){
		return 4 - (int)(8 * Math.random());
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer sb = new StringBuffer();
		//�������һ����֤��
		for(int i = 0 ; i < size ; i++){
			int index = (int)(code.length() * Math.random());
			sb.append(code.charAt(index));
		}
		//����֤��浽session����
		String checkcode = sb.toString();
		request.getSession().setAttribute(CHECK_CODE, checkcode);
		//������֤������ͼƬ�����ظ��ͻ��������
		BufferedImage img = new BufferedImage(44, 20, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = (Graphics2D)img.getGraphics();//ȡ������
		
		//�������ɰ�ɫ
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//������ʼ����
		int x = 5;
		int y = 14;
		
		for(int i = 0 ; i < checkcode.length() ;i++){
			String s = String.valueOf(checkcode.charAt(i));
			//��ȡR,G,B��ԭɫ
			int r,gg,b;
			r = getColor();
			gg = getColor();
			b = getColor();
			//���ʸ�ǰ��ɫ
			g.setColor(new Color(r,gg,b));
			//���λ��
			int d , bl ;
			d = getDistance();
			bl = getBaseLine();
			int t = x + (d * i);
			y = y + bl;
			//������֤��
			g.drawString(s, t, y);
		}
		
		//���ӵ�
		for(int j = 0 ; j < 50 ; j++){
			int x1 , y1;
			x1 = 8 + (int)(27 * Math.random());
			y1 = 2 + (int)(16 * Math.random());
			//��ȡR,G,B��ԭɫ
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
