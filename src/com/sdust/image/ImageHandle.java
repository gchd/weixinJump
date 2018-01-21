package com.sdust.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sdust.entity.Point;
import com.sdust.entity.RGBValue;

public class ImageHandle {

	// 图片宽度
	private int width;
	// 图片高度
	private int height;

	private BufferedImage bufferedImage;
	
	private Point chesspiecePoint;

	//private int rgbVerificationValue = 10;

	public void setImagePath(String imagePath) {
		try {
			bufferedImage = ImageIO.read(new File(imagePath));
			this.width = bufferedImage.getWidth();
			this.height = bufferedImage.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ImageHandle() {
	}

	/**
	 * 获取指定坐标的RGB值
	 */
	public RGBValue getRGBByPoint(int x, int y) {
		int pixel = bufferedImage.getRGB(x, y);
		return  new RGBValue((pixel & 0xff0000) >> 16,(pixel & 0xff00) >> 8,(pixel & 0xff));
	}

	public ImageHandle(String imagePath) {
		try {
			bufferedImage = ImageIO.read(new File(imagePath));
			this.width = bufferedImage.getWidth();
			this.height = bufferedImage.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 计算棋子位置，根据棋子最下方，最靠左和靠右的两个颜色位置取平均值
	 * @return
	 */
	public Point getChesspiecePoint() {
		RGBValue value = null;
		int startY = height / 4;
		int endY = (height / 4) * 3;
		int leftX = width, leftY = 0;
		int rightX = 0, rightY = 0;
		RGBValue leftColor = new RGBValue(43,43,73);
		RGBValue rightColor = new RGBValue(58,54,81);
		Point cp = new Point();
		for(int y = startY; y < endY; y++){
			for(int x = 0; x < width; x++){
				value = getRGBByPoint(x,y);
				//RGBValue.equalRGB(value, leftColor)
				if(value.getHexColor().equals(leftColor.getHexColor())){
					if(x < leftX){
						leftX = x;
						leftY = y;
					}
				}
				
//				if(RGBValue.equalRGB(value, rightColor)){
				if(value.getHexColor().equals(rightColor.getHexColor())){
					if(x > rightX){
						rightX = x;
						rightY = y;
					}
				}
			}
		}
		cp.setPointX((leftX+rightX)/2);
		cp.setPointY((leftY+rightY)/2);
		chesspiecePoint = cp;
		return cp;
	}
	/**
	 * 计算下一个方块的位置，判断棋子在中心线左方还是右方，取方块最靠上和靠右或靠左的颜色定位方块中心点
	 * @return
	 */
	public Point getNextBlock() {
		RGBValue value = null;
		RGBValue targetColor = new RGBValue();
		Point vertex = null;
		if(chesspiecePoint == null){
			chesspiecePoint = new Point(0,height-1);
		}
		int startY = height / 6;
		int endY = chesspiecePoint.getPointY();
		
		int leftX = width, leftY = height-1;
		int rightX = 0, rightY = height-1;
		//先找到方块定点位置和颜色
		OUT:
		for(int y = startY; y < endY; y++){
			for(int x = 0; x < width; x++){
				value = getRGBByPoint(x,y);
				if(RGBValue.isTargetColor(value) && vertex == null){
					targetColor = value;
					vertex = new Point(x,y);
					break OUT;
				}
			}
		}
		//未找到方块
		if(vertex == null){
			return new Point(-1,-1);
		}
		//计算中心点
		for(int y = vertex.getPointY(); y < endY; y++){
			for(int x = 0; x < width; x++){
				value = getRGBByPoint(x,y);
				if(value.getHexColor().equals(targetColor.getHexColor())){
					//棋子在中心线左侧
					if(chesspiecePoint.getPointX() < width/2){
						if(x > rightX){
							rightX = x;
							rightY = y;
						}
					}else{
						if(x < leftX){
							leftX = x;
							leftY = y;
						}
					}
				}
			}
		}
		
		if(chesspiecePoint.getPointX() < width/2){
			return new Point(vertex.getPointX(), rightY);
		}else{
			return new Point(vertex.getPointX(), leftY);
		}
		
	}
	
}
