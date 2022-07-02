package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task1_1_FunctionalTest {

	private Parser parser;
		
	@Before
	public void setUp() {
		parser = new Parser();
	}
		
	@Test
	//bug #1
	public void testEmptyShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "");
		assertTrue(!parser.optionOrShortcutExists(""));
	}
	@Test 
	//bug #2
	public void testGetBooleanInitValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		assertEquals(parser.getBoolean("input"),false);
	}
	@Test
	//bug #3
	public void testGetIntegerBoolean() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("--input falsess");
		assertEquals(parser.getInteger("input"),1);
	}
	@Test
	//bug #4
	public void testLongShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "i12323rerererer2123213213112321331qweqeqwedfsfsdfs312312312312321312213123");
		assertTrue(parser.optionOrShortcutExists("i12323rerererer2123213213112321331qweqeqwedfsfsdfs312312312312321312213123"));
	}
	@Test
	//bug 5
	//this cause an error in jar, reason is integer overflow, it is probably because when method getInteger trying to parse the value of the option to integer, and when this value is
	//negative,due to the bug the convert result is really large, cause integer overflow. Did not really find a way to make the test pass
	public void testGetIntegerNegtiveValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input  '-1'");
		assertEquals(parser.getInteger("input"),-1);
	}
	@Test 
	//bug #6
	public void testOptionEquality() {
		Option a = new Option("opt1",Type.STRING);
		Option b = new Option("opt2",Type.STRING);
		assertTrue(!a.equals(b));
	}
	@Test
	//bug 7
	public void testGetIntegerNumericValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input 1234567890");
		assertEquals(parser.getInteger("input"),1234567890);
	}
	@Test
	//bug #8 
	public void testAddExistOption() {	
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.addOption(new Option("input", Type.STRING),"in");
		assertTrue(parser.optionOrShortcutExists("in"));
	}
	@Test
	//bug #9
	public void testSpaceOption() {	
		int returned = parser.parse(" ");
		assertEquals(returned,0);
	}
	
	@Test 
	//bug #10
	public void testGetCharInitValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		assertEquals(parser.getCharacter("input"),'\0');
	}
	@Test(expected = IllegalArgumentException.class)
	//bug #11
	public void testSignOptionName() {
		parser.addOption(new Option("input#", Type.STRING),"o");
	}
	@Test 
	//bug #12
	public void testReplaceShortcutWithDash() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("-i", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	
	@Test
	//bug #13
	//test pass, but print bug13
	public void testParseSingleQuoteSIGN() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input='!@#$%^&*()_+-=[]{},<>?'");
		assertEquals(parser.getString("input"),"!@#$%^&*()_+-=[]{},<>?");
		}
	@Test
	//bug #14
	public void testGetStringControlCodeValue() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\\0\\a\\b\\t\\n\\v\\f\\r");
		assertEquals(parser.getString("input"),"\\0\\a\\b\\t\\n\\v\\f\\r");
	}
	
	@Test
	//bug 15
	public void testGetIntegerLongValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input 1231239083093210892310892310892310892318093120891238093210892");
		assertEquals(parser.getInteger("input"),0);
	}
	
	@Test(expected = NullPointerException.class)
	//bug #16
	public void testGetStringNull() {
		parser.getString(null);
	}
	@Test 
	//bug #17
	public void testLongOptionNameNoShort() {	
		Option opt = new Option("re1231239083093210892310892310892"
				, Type.STRING);
		parser.addOption(opt);
		//assertEquals(0,parser.parse("--re1231239083093210892310892310892310892318093120891238093210892"));
		assertTrue(parser.optionExists("re1231239083093210892310892310892"));
	}
	@Test 
	//bug #18
	public void testReplaceTabInMiddle() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("input    onput", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	
	@Test 
	//bug #19
	//test pass, but print bug13
	public void testParseDoubleQuoteSIGN() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\"!@#$%^&*()_+-=[]{},<>?\"");
		assertEquals(parser.getString("input"),"!@#$%^&*()_+-=[]{},<>?");
	}
	@Test 
	//bug #20
	public void testParseWithEqualAndSpace() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input = OldText");
		assertEquals(parser.getString("input"),"=");
	}
	@Test
	//no bug but fail in jar
	public void testEmptyArgumentParser() {
		assertFalse(parser.parse("")==0);
	}
	
	
	

	
	
	
}