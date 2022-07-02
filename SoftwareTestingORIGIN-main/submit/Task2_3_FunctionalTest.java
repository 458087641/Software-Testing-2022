package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task2_3_FunctionalTest {//ff ft
	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}
	//1
	@Test(expected = IllegalArgumentException.class)
	public void testAddOptionNameNull() {
		parser.addOption(new Option(null, Type.STRING), "i");

	}
	//1
	@Test 
	public void testEqualsNull() {
		Option opt1 =new Option("opt1",Type.STRING);
		assertTrue(!opt1.equals(null));
	}
	//1
	@Test 
	public void testEqualsNullNameOpt1() {
		Option opt1 =new Option(null,Type.STRING);
		Option opt2 =new Option("opt2",Type.STRING);
		assertTrue(!opt1.equals(opt2));
	}
	//1
	@Test(expected = NullPointerException.class)
	public void testEqualsNullNameOptAll() {
		Option opt1 =new Option(null,Type.STRING);
		Option opt2 =new Option(null,Type.STRING);
		opt2.equals(opt1);
	}
	//1
	@Test 
	public void testEqualsSameName() {
		Option opt1 =new Option("opt1",Type.STRING);
		Option opt2 =new Option("opt1",Type.STRING);
		assertTrue(opt2.equals(opt1));
	}
	//1
	@Test 
	public void testEqualsDiffType() {
		Option opt1 =new Option("opt1",Type.STRING);
		Option opt2 =new Option("opt2",Type.INTEGER);
		assertTrue(!opt1.equals(opt2));
	}
	//1
	@Test 
	public void testEqualsItself() {
		Option opt1 =new Option("opt1",Type.STRING);
		assertTrue(opt1.equals(opt1));
	}
	//1
	@Test(expected = IllegalArgumentException.class)
	public void testNullShortCut() {	
		parser.addOption(new Option("input", Type.STRING), null);
	}
	//1
	@Test(expected = IllegalArgumentException.class)
	public void testSignShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "#");
	}
	//1
	@Test
	public void testSameShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.addOption(new Option("no", Type.STRING), "i");
		assertTrue(parser.optionOrShortcutExists("no"));
		assertTrue(parser.optionOrShortcutExists("i"));
	}
	//1
	@Test
	public void testAddExistOptionNoShort() {	
		parser.addOption(new Option("input", Type.STRING));
		parser.addOption(new Option("input", Type.STRING));
	}
	//1
	@Test(expected = IllegalArgumentException.class)
	public void testAddNoTypeOption() {
		parser.addOption(new Option("input", Type.NOTYPE));
		assertTrue(!parser.optionExists("input"));
	}
	//1
	@Test(expected = IllegalArgumentException.class)
	public void testAddEmptyNameOption() {
		parser.addOption(new Option("", Type.STRING));
		assertTrue(parser.optionExists(""));
	}	
	//1
	@Test(expected = RuntimeException.class)
	public void testGetIntegerNotExistOptionDash() {
		parser.getInteger("--notExist");
	}
	//1
	@Test(expected = RuntimeException.class)
	public void testGetIntegerNotExistShortcutDash() {
		parser.getInteger("-e");
	}
	//1
	@Test
	public void testGetIntegerBooleanTypeF() {
		parser.addOption(new Option("input", Type.BOOLEAN));
		parser.parse("--input false");
		assertEquals(parser.getInteger("input"),0);
	}
	//2
	@Test
	public void testGetIntegerCharType() {
		parser.addOption(new Option("input", Type.CHARACTER));
		parser.parse("--input a");
		assertEquals(parser.getInteger("input"),97);
	}
	//1
	@Test
	public void testGetBooleanInit() {
		parser.addOption(new Option("input", Type.BOOLEAN));
		parser.parse("--input");
		assertEquals(parser.getBoolean("input"),false);
	}
	//1
	@Test
	public void testGetBooleanFalse() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input false");
		assertEquals(parser.getBoolean("input"),false);
	}
	//1
	@Test
	public void testGetBoolean0() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input 0");
		assertEquals(parser.getBoolean("input"),false);
	}
	
	//1
	@Test
	//bug #1
	public void testEmptyShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "");
		assertTrue(!parser.optionOrShortcutExists(""));
	}
	//2
	@Test
	//bug #3
	public void testGetIntegerBoolean() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("--input falsess");
		assertEquals(parser.getInteger("input"),1);
	}
	//1
	@Test
	public void testParseEmpty() {
		assertEquals(parser.parse(""),-2);
	}
	//1
	@Test
	public void testParseNull() {
		assertEquals(parser.parse(null),-1);
	}
	//1
	@Test(expected = RuntimeException.class)
	public void testParseNoOptioName() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse(" =false");
	}
	//3
	@Test
	public void testParseNoValue() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("input= ");
		assertEquals(parser.getString("input"),"");
	}
	//1
	@Test
	public void testParseBoolean0() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("--input 0");
		assertEquals(parser.getBoolean("input"),false);
	}
	
	//1
	@Test 
	//bug #6
	public void testOptionEquality() {
		Option a = new Option("opt1",Type.STRING);
		Option b = new Option("opt2",Type.STRING);
		assertTrue(!a.equals(b));
	}
	//1
	@Test
	//bug #8 
	public void testAddExistOption() {	
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.addOption(new Option("input", Type.STRING),"in");
		assertTrue(parser.optionOrShortcutExists("in"));
	}
	//1
	@Test 
	//bug #10
	public void testGetCharInitValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		assertEquals(parser.getCharacter("input"),'\0');
	}
	//1
	@Test(expected = IllegalArgumentException.class)
	//bug #11
	public void testSignOptionName() {
		parser.addOption(new Option("input#", Type.STRING),"o");
	}
	//1
	@Test
	//bug 15
	public void testGetIntegerLongValue() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input 1231239083093210892310892310892310892318093120891238093210892");
		assertEquals(parser.getInteger("input"),0);
	}
	//2
	@Test 
	//bug #18
	public void testReplaceTabInMiddle() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("input    onput", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	//2
	@Test 
	public void testParseDoubleQuoteNormal() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\"123\"");
		assertEquals(parser.getString("input"),"123");
	}
	//2
	@Test 
	public void testParseSingleQuoteNormal() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\'123\'");
		assertEquals(parser.getString("input"),"123");
	}
	//1
	@Test 
	public void testParseDiffQuote() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\"123\'");
		assertEquals(parser.getString("input"),"\"123\'");
	}
	//1
	@Test 
	public void testParseDiffQuoteVice() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\'123\"");
		assertEquals(parser.getString("input"),"\'123\"");
	}
	//1
	@Test
	public void testSetShortcut() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.setShortcut("input", "o");
		assertTrue(parser.shortcutExists("o"));
	}
	//1
	@Test
	public void testSetShortcutNullOption() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.setShortcut(null, "o");
		assertFalse(parser.shortcutExists("o"));
	}
	//2
	@Test 
	public void testReplaceDashOption() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("--input --onput", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	//3
	@Test 
	public void testReplaceShortcutOption() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("i o", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	//3
	@Test 
	public void testReplaceShortcutOptionDash() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("-i -o", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	//1
	@Test 
	public void testsetValueWithOptionNameNull() {
		OptionMap optionMap=new OptionMap();
		optionMap.setValueWithOptionName("123", "123");
	}


	
}
