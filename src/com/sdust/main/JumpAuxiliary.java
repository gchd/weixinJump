package com.sdust.main;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.sdust.cmd.Command;
import com.sdust.entity.Point;
import com.sdust.image.ImageHandle;

public class JumpAuxiliary {

	private Command cmd;

	private ImageHandle imageHandle;

	private boolean isStop = true;

	private volatile Thread jump;
	// 截图名称
	private final String PIC_NAME = "screenshot";

	// 电脑截图保存路径
	private final String PC_STORE_PATH = "F:\\";

	// 按压时间系数
	private double pressCoefficient;

	public JumpAuxiliary() {
		init();
	}

	public JumpAuxiliary(double pressCoefficient) {
		this.pressCoefficient = pressCoefficient;
		init();
	}

	/**
	 * 初始化数据
	 */
	public void init() {
		cmd = new Command();
		imageHandle = new ImageHandle();
	}

	public double getPressCoefficient() {
		return pressCoefficient;
	}

	/**
	 * 开始辅助
	 * 
	 * @throws InterruptedException
	 */
	public void start() {
		System.out.println("启动");
		while (isStop) {
			// 截图
			getScreenshotImageFile();
			imageHandle.setImagePath(PC_STORE_PATH + PIC_NAME + ".png");
			// 得到棋子的位置
			Point chesspiecePoint = imageHandle.getChesspiecePoint();
			System.out.println("棋子位置" + chesspiecePoint.toString());
			// 得到下一个方块的位置
			Point nextBlockPoint = imageHandle.getNextBlock();
			System.out.println("下一个方块位置" + nextBlockPoint.toString());
			if (nextBlockPoint.getPointX() == -1) {
				break;
			}
			// 计算两个方块的距离
			double jumpDistance = Point.getJumpDistance(chesspiecePoint,
					nextBlockPoint);
			// 点击屏幕跳跃
			press(chesspiecePoint,
					(int) Math.max(jumpDistance * pressCoefficient, 200));
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 停止辅助,同一个线程调用不到
	 */
	public void stop() {
		this.isStop = false;
	}

	public void setPressCoefficient(double pressCoefficient) {
		this.pressCoefficient = pressCoefficient;
	}

	/**
	 * 得到手机屏幕截图保存到PC_STORE_PATH目录下
	 */
	public void getScreenshotImageFile() {
		String cmdString1 = "adb shell screencap -p /sdcard/" + PIC_NAME
				+ ".png";
		cmd.execCommand(cmdString1);
		String cmdString2 = "adb pull /sdcard/" + PIC_NAME + ".png "
				+ PC_STORE_PATH;
		cmd.execCommand(cmdString2);
	}

	/**
	 * 执行按压操作
	 */
	public void press(Point p, int pressTime) {
		String command = String.format("adb shell input swipe %s %s %s %s %s",
				p.getPointX(), p.getPointY(), p.getPointX(), p.getPointY(),
				pressTime);
		cmd.execCommand(command);
	}

}
