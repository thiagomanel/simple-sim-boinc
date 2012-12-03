package simboinc;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ResultsLogger {
	private final boolean useStdOut;
	private final boolean useStdErr;
	private final BufferedWriter outputStream;
	
	public ResultsLogger(String logFilePath, boolean useStdOut, boolean useStdErr) {
		this.outputStream = outputStream(logFilePath);
		this.useStdOut = useStdOut;
		this.useStdErr = useStdErr;
	}
	
	public void log(String info) {
		printStdOut(info);
		printStdErr(info);
		saveToFile(info);
	}
	
	public void close() {
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printStdOut(String info) {
		if(useStdOut) {
			System.out.println(info);
		}
	}
	
	private void printStdErr(String info) {
		if(useStdErr) {
			System.err.println(info);
		}
	}
	
	private void saveToFile(String info) {
		try {
			outputStream.write(info);
			outputStream.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedWriter outputStream(String filePath) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8");
			return new BufferedWriter(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
