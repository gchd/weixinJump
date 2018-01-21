package com.sdust.cmd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Command {

	public CommandResult execCommand(String command) {
		return execCommand(new String[] { command }, true);
	}

	public CommandResult execCommand(List<String> commands) {
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}), true);
	}

	public CommandResult execCommand(String[] commands) {
		return execCommand(commands, true);
	}
	
	public CommandResult execCommand(String command, boolean isRoot,
			boolean isNeedResultMsg) {
		return execCommand(new String[] { command }, isNeedResultMsg);
	}
	
	public CommandResult execCommand(List<String> commands, boolean isRoot,
			boolean isNeedResultMsg) {
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}), isNeedResultMsg);
	}


	public CommandResult execCommand(String[] commands, boolean isNeedResultMsg) {
		int result = -1;
		
		StringBuilder successMsg = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();;
		
		if (commands == null || commands.length == 0) {
			return new CommandResult(result, null, null);
		}
		
		for (String command : commands) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(command);
				//System.out.println("exec command start: " + command);
				process.waitFor();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getErrorStream()));
				String s = null;
				while ((s  = bufferedReader.readLine()) != null) {
					successMsg.append(s);
				}
				result = 1;
				//System.out.println("exec command end: " + command);
			} catch (Exception e) {
				//e.printStackTrace();
				errorMsg.append(e.getMessage());
				result = 0;
			} finally {
				if (process != null) {
					process.destroy();
				}
			}
		}
		return new CommandResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}

	public class CommandResult {

		public int result;
		public String successMsg;
		public String errorMsg;

		public CommandResult(int result) {
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}
}