package com.sdust.testJump;
import org.junit.Test;

import com.sdust.main.JumpAuxiliary;

public class TestJumpAuxiliary {
	
	@Test
	public void test() throws InterruptedException{
		JumpAuxiliary jumpAuxiliary = new JumpAuxiliary(2.05);
		jumpAuxiliary.start();
	}
}
