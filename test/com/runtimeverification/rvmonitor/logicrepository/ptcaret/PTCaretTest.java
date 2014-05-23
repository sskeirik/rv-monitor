package com.runtimeverification.rvmonitor.logicrepository.ptcaret;

import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.Code;
import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.ast.PTCARET_Formula;
import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.parser.PTCARETParser;
import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.parser.ParseException;
import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.visitor.CodeGenVisitor;
import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.visitor.NumberingVisitor;
import com.runtimeverification.rvmonitor.logicrepository.plugins.ptcaret.visitor.SimplifyVisitor;

import org.junit.Test;
import static org.junit.Assert.*;

public class PTCaretTest {
	// String logicStr = "! a";
	
	@Test
	public void testSimplify() throws ParseException {
		PTCARET_Formula ptCaretFormula = PTCARETParser.parse("!!!(!a && (b || c))");
		assertEquals("!!!(!a && (b || c))", ptCaretFormula.toString());
		
		ptCaretFormula = ptCaretFormula.accept(new SimplifyVisitor(), null);
		assertEquals("a || (!b && !c)", ptCaretFormula.toString());
	}
	
	@Test
	public void testSimplifyCode() throws ParseException {
		PTCARET_Formula ptCaretFormula = PTCARETParser.parse("a S (b Sa (*)a)");
		assertEquals("a S (b Sa (*)a)", ptCaretFormula.toString());
		
		ptCaretFormula = ptCaretFormula.accept(new SimplifyVisitor(), null);
		assertEquals("a S (b Sa (*)a)", ptCaretFormula.toString());
		
		ptCaretFormula.accept(new NumberingVisitor(), null);
		assertEquals("a S (b Sa (*)a)", ptCaretFormula.toString());
		
		Code code = ptCaretFormula.accept(new CodeGenVisitor(), null);
		assertEquals("a S (b Sa (*)a)", ptCaretFormula.toString());
		//This is pretty much regression testing, I don't know what this is supposed to do.
		assertEquals("$beta$[0] := ($alpha$[0] || b && $beta$[0]);\n$alpha$[1] := ($beta$[0] || a && $alpha$[1]);\n", code.beforeCode);
		assertEquals("$alpha$[0] := a;\n", code.afterCode);
		assertEquals("$alpha$[1]", code.output);
	}
}
