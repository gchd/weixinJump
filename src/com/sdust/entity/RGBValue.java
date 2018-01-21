package com.sdust.entity;

import org.junit.Test;

public class RGBValue {
	private int rValue;
	private int gValue;
	private int bValue;
	//允许的误差
	private static int rgbVerificationValue = 5;
	
	//方块颜色
	private static String[] targetColors  = {"#575757","#BEBEBE","#90BA35","#4E7352","#BE632D","#C2C2C2",
											 "#BABABA","#C8B84B","#BEBEBE","#CCCCCC","#5379C0","#AE9A6E",
											 "#CC868A","#727272","#6A5FB1","#B49C87","#5A5A5A","#9A939B",
											 "#584A46","#3A7C42","#C53A40","#C9B84B","#383838","#FFFFFF"};
//	private static String[] targetColors  = {"#C9B84B"};
	public RGBValue(){
		this.rValue = 0;
		this.gValue = 0;
		this.bValue = 0;
	}
	//例子#FFFFFF
	public RGBValue(String hexColor){
		if(!(hexColor.length() ==7) || !hexColor.startsWith("#")){
			this.rValue = 0;
			this.gValue = 0;
			this.bValue = 0;
        }else{
        	try{
    			this.rValue = Integer.parseInt(hexColor.substring(1,3),16);  
            	this.gValue = Integer.parseInt(hexColor.substring(3,5),16);  
            	this.bValue = Integer.parseInt(hexColor.substring(5,7),16); 
    		}catch (Exception e) {
    			this.rValue = 0;
    			this.gValue = 0;
    			this.bValue = 0;
    		}
        }
	}
	
	public RGBValue(int rValue, int gValue,int bValue){
		this.rValue = rValue;
		this.gValue = gValue;
		this.bValue = bValue;
	}

	public void setRgbVerificationValue(int rgbVerificationValue) {
		this.rgbVerificationValue = rgbVerificationValue;
	}
	public int getrValue() {
		return rValue;
	}
	public void setrValue(int rValue) {
		this.rValue = rValue;
	}
	public int getgValue() {
		return gValue;
	}
	public void setgValue(int gValue) {
		this.gValue = gValue;
	}
	public int getbValue() {
		return bValue;
	}
	public void setbValue(int bValue) {
		this.bValue = bValue;
	}
	
	public static boolean isTargetColor(RGBValue rgbColor){
		if(rgbColor == null){
			return false;
		}
		for(String s : RGBValue.targetColors){
			if(s.equalsIgnoreCase(rgbColor.getHexColor())){
				return true;
			}
		}
		return false;
	}
	
	public String getHexColor(){
          String hexColor = "#" +Integer.toHexString(rValue)+Integer.toHexString(gValue)+Integer.toHexString(bValue);  
          return hexColor;
	}
	
	@Override
	public String toString() {
		return "RGBValue [rValue=" + rValue + ", gValue=" + gValue
				+ ", bValue=" + bValue + "]";
	}

	/**
	 * 
	 * @param A 当前点的颜色
	 * @param B 要比较的颜色中心值（加减误差值）
	 * @return
	 */
	public static boolean equalRGB(RGBValue A, RGBValue B) {
		if(A.getrValue() > B.getrValue()+rgbVerificationValue || A.getrValue() < B.getrValue()-rgbVerificationValue){
			return false;
		}
		
		if(A.getgValue() > B.getgValue()+rgbVerificationValue && A.getgValue() < B.getgValue()-rgbVerificationValue){
			return false;
		}
		
		if(A.getbValue() > B.getbValue()+rgbVerificationValue && A.getbValue() < B.getbValue()-rgbVerificationValue){
			return false;
		}
		return true;
	}
	
}
