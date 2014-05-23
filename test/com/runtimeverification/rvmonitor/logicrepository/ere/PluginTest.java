package com.runtimeverification.rvmonitor.logicrepository.ere;

import com.runtimeverification.rvmonitor.logicrepository.PluginHelper;

import com.runtimeverification.rvmonitor.logicrepository.parser.logicrepositorysyntax.LogicRepositoryType;
import com.runtimeverification.rvmonitor.logicrepository.parser.logicrepositorysyntax.PropertyType;

import org.junit.Test;
import static org.junit.Assert.*;

public class PluginTest {
	
	@Test
	public void testCompletePluginHasNext() throws Exception {
		//based on HasNext at http://fsl.cs.illinois.edu/index.php/Special:EREPlugin3
		LogicRepositoryType input = new LogicRepositoryType();
		input.setClient("testing");
		input.setEvents("hasnext next");
		input.setCategories("fail");
		
		PropertyType property = new PropertyType();
		property.setLogic("ere");
		property.setFormula("(hasnext hasnext* next)*");
		input.setProperty(property);
		
		LogicRepositoryType output = PluginHelper.runLogicPlugin("ere", input);
		
		assertEquals("testing", output.getClient());
		assertEquals("hasnext next", output.getEvents());
		String expectedFormula =
			"s0 [\n" +
			"   hasnext -> s1\n" +
			"]\n" +
			"s1 [\n" +
			"   hasnext -> s1\n" +
			"   next -> s0\n" +
			"]\n" +
			"alias match = s0 \n";
		assertEquals(expectedFormula, output.getProperty().getFormula());
		assertEquals("fail", output.getCategories());
		
	}
	
}