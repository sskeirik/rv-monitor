package com.runtimeverification.rvmonitor.java.rvj.output.combinedaspect.event;

import java.util.ArrayList;

import com.runtimeverification.rvmonitor.util.RVMException;
import com.runtimeverification.rvmonitor.java.rvj.output.RVMVariable;
import com.runtimeverification.rvmonitor.java.rvj.output.combinedaspect.CombinedAspect;
import com.runtimeverification.rvmonitor.java.rvj.output.combinedaspect.event.advice.AdviceBody;
import com.runtimeverification.rvmonitor.java.rvj.parser.ast.mopspec.EventDefinition;
import com.runtimeverification.rvmonitor.java.rvj.parser.ast.mopspec.RVMonitorSpec;

public class EndProgram {
	private final RVMVariable hookName;
	private final String className;

	private final ArrayList<EndThread> endThreadEvents = new ArrayList<EndThread>();
	private final ArrayList<AdviceBody> eventBodies = new ArrayList<AdviceBody>();

	public EndProgram(String name) {
		this.hookName = new RVMVariable(name + "_DummyHookThread");
		this.className = name + "RuntimeMonitor";
	}

	public void addEndProgramEvent(RVMonitorSpec mopSpec, EventDefinition event, CombinedAspect combinedAspect) throws RVMException {
		if (!event.isEndProgram())
			throw new RVMException("EndProgram should be defined only for an endProgram pointcut.");

		this.eventBodies.add(AdviceBody.createAdviceBody(mopSpec, event, combinedAspect));
	}

	public void registerEndThreadEvents(ArrayList<EndThread> endThreadEvents) {
		this.endThreadEvents.addAll(endThreadEvents);
	}

	public String printAddStatement() {
		String ret = "";
		
		if(eventBodies.size() == 0 && endThreadEvents.size() == 0)
			return ret;

		ret += "Runtime.getRuntime().addShutdownHook( (new " + className + "())" + ".new " + hookName + "());\n";

		return ret;
	}

	public String printHookThread() {
		String ret = "";

		if(eventBodies.size() == 0 && endThreadEvents.size() == 0)
			return ret;

		ret += "class " + hookName + " extends Thread {\n";
		ret += "public void run(){\n";

		if (endThreadEvents != null && endThreadEvents.size() > 0) {
			for (EndThread endThread : endThreadEvents) {
				ret += endThread.printAdviceBodyAtEndProgram();
			}
		}

		for (AdviceBody eventBody : eventBodies) {
			if (eventBodies.size() > 1) {
				ret += "{\n";
			}

			ret += eventBody;

			if (eventBodies.size() > 1) {
				ret += "}\n";
			}
		}

		ret += "}\n";
		ret += "}\n";

		return ret;
	}
}