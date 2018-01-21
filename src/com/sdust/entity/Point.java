package com.sdust.entity;

public class Point {
	private int pointX;
	private int pointY;
	
	public Point() {
		this.pointX = 0;
		this.pointY = 0;
	}

	public Point(int pointX, int pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
	}

	public int getPointX() {
		return pointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}

	public static double getJumpDistance(Point start, Point end) {
		return Math.sqrt(Math.pow(Math.abs(end.getPointX() - start.getPointX()), 2)
				+ Math.pow(Math.abs(end.getPointY() - start.getPointY()), 2));
	}

	@Override
	public String toString() {
		return "[pointX=" + pointX + ", pointY=" + pointY + "]";
	}

}
