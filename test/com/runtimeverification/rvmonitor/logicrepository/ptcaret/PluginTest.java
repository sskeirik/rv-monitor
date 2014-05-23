package com.runtimeverification.rvmonitor.logicrepository.ptcaret;

import com.runtimeverification.rvmonitor.logicrepository.PluginHelper;

import com.runtimeverification.rvmonitor.logicrepository.parser.logicrepositorysyntax.LogicRepositoryType;
import com.runtimeverification.rvmonitor.logicrepository.parser.logicrepositorysyntax.PropertyType;

import org.junit.Test;
import static org.junit.Assert.*;

public class PluginTest {
	
	@Test
	public void testCompletePluginHasNext() throws Exception {
		LogicRepositoryType input = new LogicRepositoryType();
		input.setClient("testing");
		input.setProperty(new PropertyType());
		input.getProperty().setLogic("ptcaret");
		input.getProperty().setFormula("a S (b Sa (*)a)");
		
		LogicRepositoryType output = PluginHelper.runLogicPlugin("ptcaret", input);
		
		assertEquals("testing", output.getClient());
		assertEquals("ptcaret pseudo-code", output.getProperty().getLogic());
		String expectedFormula =
			"$beta$[0] := ($alpha$[0] || b && $beta$[0]);\n$alpha$[1] := ($beta$[0] || a && $alpha$[1]);\n" +
			"output($alpha$[1])\n"+
			"$alpha$[0] := a;\n";
		assertEquals(expectedFormula, output.getProperty().getFormula());
	}
}