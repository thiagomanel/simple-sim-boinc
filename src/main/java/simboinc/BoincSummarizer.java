package simboinc;

import simboinc.model.Machine;
import core.Context;
import core.Summarizer;

public class BoincSummarizer implements Summarizer {

	@Override
	public String summarize(Context context) {
		Machine machine1 = (Machine) context.get("machine1");
		Machine machine2 = (Machine) context.get("machine2");
		
		return String.format("Normal:\n%s\n" +
				"========================================\n" +
				"Modified:\n%s", machine1.formatedResume(), machine2.formatedResume());
	}

}
