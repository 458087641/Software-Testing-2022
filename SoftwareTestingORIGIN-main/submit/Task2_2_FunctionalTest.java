package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task2_2_FunctionalTest {//ff ft
	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}

	@Test 
	public void testsSetOptionName() {
		Option opt1 =new Option("opt1",Type.STRING);
		opt1.setName("input");
		assertEquals(opt1.getName(),"input");
	}
	@Test 
	public void testUnderscoreOptionname1() {
		Option opt1 =new Option("____",Type.STRING);
		parser.addOption(opt1);
		assertTrue(parser.optionExists("____"));
	}
	@Test 
	public void testUnderscoreOptionname2() {
		Option opt1 =new Option("_option1",Type.STRING);
		parser.addOption(opt1);
		assertTrue(parser.optionExists("_option1"));
	}
	@Test 
	public void testUnderscoreOptionname3() {
		Option opt1 =new Option("option_1",Type.STRING);
		parser.addOption(opt1);
		assertTrue(parser.optionExists("option_1"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAddOptionNameNull() {
		parser.addOption(new Option(null, Type.STRING), "i");
	}
	@Test 
	public void testEqualsNull() {
		Option opt1 =new Option("opt1",Type.STRING);
		assertTrue(!opt1.equals(null));
	}
	@Test 
	public void testEqualsNullNameOpt1() {
		Option opt1 =new Option(null,Type.STRING);
		Option opt2 =new Option("opt2",Type.STRING);
		assertTrue(!opt1.equals(opt2));
	}
	@Test(expected = NullPointerException.class)
	public void testEqualsNullNameOptAll() {
		Option opt1 =new Option(null,Type.STRING);
		Option opt2 =new Option(null,Type.STRING);
		opt2.equals(opt1);
	}
	@Test 
	public void testEqualsSameName() {
		Option opt1 =new Option("opt1",Type.STRING);
		Option opt2 =new Option("opt1",Type.STRING);
		assertTrue(opt2.equals(opt1));
	}
	@Test
	public void testEqualsDiffOpt() {
		Option opt1 =new Option("opt1",Type.STRING);
		Option opt2 =new Option("opt2",Type.STRING);
		assertTrue(!opt1.equals(opt2));
	}
	@Test 
	public void testEqualsDiffType() {
		Option opt1 =new Option("opt1",Type.STRING);
		Option opt2 =new Option("opt2",Type.INTEGER);
		assertTrue(!opt1.equals(opt2));
	}
	@Test 
	public void testEqualsItself() {
		Option opt1 =new Option("opt1",Type.STRING);
		assertTrue(opt1.equals(opt1));
	}
	
	@Test
	public void testShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "i");
		assertTrue(parser.optionOrShortcutExists("i"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNullShortCut() {	
		parser.addOption(new Option("input", Type.STRING), null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSignShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "#");
	}
	@Test
	public void testSameShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.addOption(new Option("no", Type.STRING), "i");
		assertTrue(parser.optionOrShortcutExists("no"));
		assertTrue(parser.optionOrShortcutExists("i"));
	}
	@Test
	public void testSameShortCutAndName() {	
		parser.addOption(new Option("input", Type.STRING), "input");
		assertTrue(parser.shortcutExists("input"));
		assertTrue(parser.optionExists("input"));
	}
	@Test
	public void testAddExistOptionNoShort() {	
		parser.addOption(new Option("input", Type.STRING));
		parser.addOption(new Option("input", Type.STRING));
	}
	@Test 
	public void testAddStringOption() {	
		parser.addOption(new Option("input", Type.STRING),"i");
		assertTrue(parser.optionExists("input"));
	}
	@Test 
	public void testAddBooleanOption() {
		parser.addOption(new Option("input", Type.BOOLEAN));
		assertTrue(parser.optionExists("input"));
	}
	@Test 
	public void testAddCharOption() {
		parser.addOption(new Option("input", Type.CHARACTER));
		assertTrue(parser.optionExists("input"));
	}
	@Test 
	public void testAddIntegerOption() {
		parser.addOption(new Option("input", Type.INTEGER));
		assertTrue(parser.optionExists("input"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAddNoTypeOption() {
		parser.addOption(new Option("input", Type.NOTYPE));
		assertTrue(!parser.optionExists("input"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAddEmptyNameOption() {
		parser.addOption(new Option("", Type.STRING));
		assertTrue(parser.optionExists(""));
	}
	@Test 
	public void testShortOptionName() {	
		parser.addOption(new Option("i", Type.STRING),"o");
		assertEquals(0,parser.parse("i"));
		assertEquals(0,parser.parse("o"));
	}
	@Test 
	public void testNormalOptionNameNoShort() {	
		parser.addOption(new Option("input", Type.STRING));
		assertEquals(0,parser.parse("input"));
	}
	@Test 
	public void testShortOptionNameNoShort() {	
		parser.addOption(new Option("i", Type.STRING));
		assertEquals(0,parser.parse("i"));
	}
	@Test 
	public void testLongOptionName() {	
		Option opt = new Option("re1231239083093210892310892310892310892318093120891238093210892"
				, Type.STRING);
		parser.addOption(opt,"s");
		assertEquals(0,parser.parse("re1231239083093210892310892310892310892318093120891238093210892"));
		assertEquals(0,parser.parse("s"));
	}
	@Test 
	public void testNullArgumetParser() {	
		assertFalse(parser.parse(null)==0);
	}
	@Test
	public void testEmptyArgumentParser() {	
		assertFalse(parser.parse("")==0);
	}
	@Test(expected = RuntimeException.class)
	public void testNotDefinedShortcut() {
		//parser.addOption(new Option("input",Type.STRING),"x");
		parser.parse("-x 123");
	}
	@Test(expected = RuntimeException.class)
	public void testNotDefinedOption() {
		parser.parse("--notExist 123");
	}
	@Test
	public void testDefinedShortcut() {
		parser.addOption(new Option("input",Type.STRING),"x");
		assertEquals(parser.parse("-x 123"),0);
	}
	
	@Test 
	public void testGetStringInitValue() {
		parser.addOption(new Option("input", Type.STRING));
		assertEquals(parser.getString("input"),"");
	}
	@Test 
	public void testGetStringNormalValue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input test1");
		assertEquals(parser.getString("input"),"test1");
	}
	@Test 
	public void testGetStringNormalValueDash() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input test1");
		assertEquals(parser.getString("--input"),"test1");
	}
	@Test
	public void testGetStringNumericValue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input 1234567890");
		assertEquals(parser.getString("input"),"1234567890");
	}
	@Test
	public void testGetStringSignValue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input !@#");
		assertEquals(parser.getString("input"),"!@#");
	}
	@Test
	public void testGetStringLongValue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input re1231239083093210892310892310892310892318093120891238093210892");
		assertEquals(parser.getString("input"),"re1231239083093210892310892310892310892318093120891238093210892");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetIntegerNotExistOptionDash() {
		parser.getInteger("--notExist");
	}
	@Test(expected = RuntimeException.class)
	public void testGetIntegerNotExistShortcutDash() {
		parser.getInteger("-e");
	}
	@Test 
	public void testGetIntegerInitValue() {
		parser.addOption(new Option("input", Type.STRING));
		assertEquals(parser.getInteger("input"),0);
	}
	@Test 
	public void testGetIntegerNormalValue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input test1");
		assertEquals(parser.getInteger("input"),0);
	}
	@Test
	public void testGetIntegerSignValue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input !@#");
		assertEquals(parser.getInteger("input"),0);
	}
	@Test
	public void testGetIntegerBooleanTypeT() {
		parser.addOption(new Option("input", Type.BOOLEAN));
		parser.parse("--input true");
		assertEquals(parser.getInteger("input"),1);
	}
	@Test
	public void testGetIntegerBooleanTypeF() {
		parser.addOption(new Option("input", Type.BOOLEAN));
		parser.parse("--input false");
		assertEquals(parser.getInteger("input"),0);
	}
	@Test
	public void testGetIntegerCharType() {
		parser.addOption(new Option("input", Type.CHARACTER));
		parser.parse("--input a");
		assertEquals(parser.getInteger("input"),97);
	}
	@Test
	public void testGetIntegerIntType() {
		parser.addOption(new Option("input", Type.INTEGER));
		parser.parse("--input 1");
		assertEquals(parser.getInteger("input"),1);
	}
	@Test
	public void testGetBooleanInit() {
		parser.addOption(new Option("input", Type.BOOLEAN));
		parser.parse("--input");
		assertEquals(parser.getBoolean("input"),false);
	}
	@Test
	public void testGetBooleanFalse() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input false");
		assertEquals(parser.getBoolean("input"),false);
	}
	@Test
	public void testGetBooleanTrue() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input true");
		assertEquals(parser.getBoolean("input"),true);
	}
	@Test
	public void testGetBoolean0() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input 0");
		assertEquals(parser.getBoolean("input"),false);
	}
	@Test
	public void testGetBooleanNon0Int() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input 11");
		assertEquals(parser.getBoolean("input"),true);
	}
	@Test
	public void testGetBooleanEmpty() {
		parser.addOption(new Option("input", Type.STRING));
		parser.parse("--input=\"\"");
		assertEquals(parser.getBoolean("input"),false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignOptionNameNoShort() {	
		parser.addOption(new Option("input!&", Type.STRING));
		assertTrue(parser.optionExists("input!&"));
		assertEquals(0,parser.parse("--input!&"));
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
	public void testParseEmpty() {
		assertEquals(parser.parse(""),-2);
	}
	@Test
	public void testParseNull() {
		assertEquals(parser.parse(null),-1);
	}
	@Test
	public void testParseBoolean() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("--input false");
		assertEquals(parser.getBoolean("input"),false);
	}
	@Test(expected = RuntimeException.class)
	public void testParseNoOptioName() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse(" =false");
	}
	@Test
	public void testParseNoValue() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("input= ");
		assertEquals(parser.getString("input"),"");
	}
	@Test
	public void testParseBoolean0() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("--input 0");
		assertEquals(parser.getBoolean("input"),false);
	}
	@Test
	//bug #4
	public void testLongShortCut() {	
		parser.addOption(new Option("input", Type.STRING), "i12323rerererer2123213213112321331qweqeqwedfsfsdfs312312312312321312213123");
		assertTrue(parser.optionOrShortcutExists("i12323rerererer2123213213112321331qweqeqwedfsfsdfs312312312312321312213123"));
	}
	@Test
	//bug 5
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
		Option opt = new Option("re1231239083093210892310892310892310892318093120891238093210892"
				, Type.STRING);
		parser.addOption(opt,"i");
		//assertEquals(0,parser.parse("--re1231239083093210892310892310892310892318093120891238093210892"));
		assertTrue(parser.optionExists("re1231239083093210892310892310892310892318093120891238093210892"));
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
	public void testParseDoubleQuoteSIGN() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\"!@#$%^&*()_+-=[]{},<>?\"");
		assertEquals(parser.getString("input"),"!@#$%^&*()_+-=[]{},<>?");
	}
	@Test 
	public void testParseDoubleQuoteNormal() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\"123\"");
		assertEquals(parser.getString("input"),"123");
	}
	@Test 
	public void testParseSingleQuoteNormal() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\'123\'");
		assertEquals(parser.getString("input"),"123");
	}
	@Test 
	public void testParseDiffQuote() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\"123\'");
		assertEquals(parser.getString("input"),"\"123\'");
	}
	@Test 
	public void testParseDiffQuoteVice() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input=\'123\"");
		assertEquals(parser.getString("input"),"\'123\"");
	}
	@Test 
	//bug #20
	public void testParseWithEqualAndSpace() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.parse("--input = OldText");
		assertEquals(parser.getString("input"),"=");
	}
	@Test
	public void testSetShortcut() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.setShortcut("input", "o");
		assertTrue(parser.shortcutExists("o"));
	}
	@Test
	public void testSetShortcutNullOption() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.setShortcut(null, "o");
		assertFalse(parser.shortcutExists("o"));
	}
	@Test 
	public void testReplaceDashOption() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("--input --onput", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	@Test 
	public void testReplaceShortcutOption() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("i o", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	@Test 
	public void testReplaceShortcutOptionDash() {
		parser.addOption(new Option ("input", Type.STRING),"i");
		parser.addOption(new Option ("onput", Type.STRING),"o");
		parser.parse("--input=OldText --onput=OldTe");
		parser.replace("-i -o", "Old", "=");
		assertEquals(parser.getString("input"),"=Text");
	}
	@Test 
	public void testsetValueWithOptionNameNull() {
		OptionMap optionMap=new OptionMap();
		optionMap.setValueWithOptionName("123", "123");
	}


	
}
