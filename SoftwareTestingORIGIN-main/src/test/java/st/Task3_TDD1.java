package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task3_TDD1 {

	private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
	}
	@Test
	public void testsAddAllOptionAdded() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		assertTrue(parser.optionExists("option1")&&parser.optionExists("option2")&&parser.optionExists("option3")
		&&parser.optionExists("option4"));
	}
	@Test
	public void testsAddAllNoShortcut() {
		parser.addAll("option1 option2 option3 option4" ,
				"String Integer Boolean Character" );
		assertTrue(parser.optionExists("option1")&&parser.optionExists("option2")&&parser.optionExists("option3")
				&&parser.optionExists("option4"));
	}
	@Test
	public void testsAddAllEmptyOptionName() {
		parser.addAll("" ,"o1 " ,
				"String Integer Boolean Character" );
		assertTrue(!parser.shortcutExists("o1"));
	}
	@Test
	public void testsAddAllNullOptionName() {
		parser.addAll(null ,"o1 " ,
				"String Integer Boolean Character" );
		assertTrue(!parser.shortcutExists("o1"));
	}
	@Test
	public void testsAddAllNullShortcut() {
		parser.addAll("option1 option2" ,null ,
				"String Integer Boolean Character" );
		assertTrue(parser.optionExists("option1"));
		assertTrue(parser.optionExists("option2"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testsAddAllNullType() {
		parser.addAll("option1 option2" ,"o1 o2",
				null );
		assertTrue(parser.optionExists("option1"));
		assertTrue(parser.optionExists("option2"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testsAddAllEmptyType() {
		parser.addAll("option1 option2" ,"o1 o2",
				"" );
		assertTrue(parser.optionExists("option1"));
		assertTrue(parser.optionExists("option2"));
	}
	@Test
	public void testsAddAllOptionAddedInvalidName() {
		parser.addAll("option1# option2 " ,"o1 o2" ,
				"String Integer " );
		assertTrue(!parser.optionExists("option1#")&&parser.optionExists("option2"));
	}
	@Test
	public void testsAddAllOptionAddedEmptyName() {
		parser.addAll("\"\" option2 " ,"o1 o2" ,
				"String Integer " );
		assertTrue(!parser.optionExists("")&&parser.optionExists("option2"));
	}
	@Test
	public void testsAddAllOptionAddedEmptySpaceName() {
		parser.addAll("\" \" option2 " ,"o1 o2" ,
				"String Integer " );
		assertTrue(!parser.optionExists(" ")&&parser.optionExists("option2"));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testsAddAllOptionAddedInvalidType() {
		parser.addAll("option1 option2 " ,"o1 o2" ,
				"String 714514" );
		assertTrue(!parser.optionExists("option1#")&&parser.optionExists("option2"));
	}
	@Test
	public void testsAddAllOptionAddedInvalidShortcut() {
		parser.addAll("option1 option2 " ,"o1 ##" ,
				"String Integer" );
		assertTrue(parser.shortcutExists("o1")&&!parser.shortcutExists("##"));
	}
	@Test
	public void testsAddAllOptionAddedEmptyShortcut() {
		parser.addAll("option1 option2 " ,"o1 \"\"",
				"String Integer" );
		assertTrue(parser.shortcutExists("o1")&&!parser.shortcutExists(""));
	}
	@Test
	public void testsAddAllOptionAddedEmptySpaceShortcut() {
		parser.addAll("option1 option2 " ,"o1 \" \"",
				"String Integer" );
		assertTrue(parser.shortcutExists("o1")&&!parser.shortcutExists(" "));
	}
	@Test
	public void testsAddAllOptionNoShortcut() {
		parser.addAll("option1 option2 option3 option4",
				"String Integer Boolean Character" );
		assertTrue(parser.optionExists("option1")&&parser.optionExists("option2")&&parser.optionExists("option3")
				&&parser.optionExists("option4"));
	}
	@Test
	public void testsAddAllOptionExtraSpaceAdded() {
		parser.addAll("option1  option2  option3  option4" ,"o1  o2  o3  o4" ,
				"String  Integer  Boolean  Character" );
		assertTrue(parser.optionExists("option1")&&parser.optionExists("option2")&&parser.optionExists("option3")
				&&parser.optionExists("option4"));
	}
	@Test
	public void testsAddAllOptionShortcutAdded() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		assertTrue(parser.shortcutExists("o1")&&parser.shortcutExists("o2")&&parser.shortcutExists("o3")
				&&parser.shortcutExists("o4"));
	}
	@Test
	public void testsAddAllOptionShortcutMatches1() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		parser.parse("-o1=123");
		assertEquals("123",parser.getString("option1"));
	}
	@Test
	public void testsAddAllOptionShortcutMatches2() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		parser.parse("-o2=123");
		assertEquals("123",parser.getString("option2"));
	}
	@Test
	public void testsAddAllOptionShortcutMatches3() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		parser.parse("-o3=123");
		assertEquals("123",parser.getString("option3"));
	}
	@Test
	public void testsAddAllOptionShortcutMatches4() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		parser.parse("-o4=123");
		assertEquals("123",parser.getString("option4"));
	}
	@Test
	public void testsAddAllOptionTypeMatch1() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		assertEquals(Type.STRING,parser.getType("option1"));
	}
	@Test
	public void testsAddAllOptionTypeMatch2() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		assertEquals(Type.INTEGER,parser.getType("option2"));
	}
	@Test
	public void testsAddAllOptionTypeMatch3() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		assertEquals(Type.BOOLEAN,parser.getType("option3"));
	}
	@Test
	public void testsAddAllOptionTypeMatch4() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Character" );
		assertEquals(Type.CHARACTER,parser.getType("option4"));
	}
	@Test
	public void testsAddAllOptionSameTypeMatch4() {
		parser.addAll("option1 option2 " ,"o1 o2 " ,
				"String String" );
		assertEquals(Type.STRING,parser.getType("option1"));
		assertEquals(Type.STRING,parser.getType("option2"));
	}
	@Test
	public void testsAddAllOptionTypeDiffCases() {
		parser.addAll("option1 option2" ,"o1 o2 " ,
				"string INTEGER" );
		assertEquals(Type.STRING,parser.getType("option1"));
		assertEquals(Type.INTEGER,parser.getType("option2"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testsAddAllWithNotype() {
		parser.addAll("option1 option2 option3 option4" ,"o1 o2 o3 o4" ,
				"String Integer Boolean Notype" );
	}
	@Test
	public void testsAddAllOptionLessType1() {
		parser.addAll("option1 option2 " ,"o1 o2" ,
				"String" );
		assertTrue(parser.getType("option1")==parser.getType("option2")&&parser.getType("option1")==Type.STRING);
	}
	@Test
	public void testsAddAllOptionLessType2() {
		parser.addAll("option1 option2 option3" ,"o1 o2 o3" ,
				"String Integer" );
		assertTrue(parser.getType("option2")==parser.getType("option3")
				&& parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.INTEGER);
	}
	@Test
	public void testsAddAllOptionMoreType() {
		parser.addAll("option1 option2 " ,"o1 o2" ,
				"String Integer Character" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.INTEGER);
	}
	@Test
	public void testsAddAllOptionLessShortcut() {
		parser.addAll("option1 option2 " ,"o1" ,
				"String Integer " );
		parser.parse("-o1=123");
		parser.parse("--option2=321");
		assertEquals("123",parser.getString("option1"));
		 assertEquals(321,parser.getInteger("option2"));
	}
	@Test
	public void testsAddAllOptionMoreShortcut() {
		parser.addAll("option1 option2 " ,"o1 o2 o3" ,
				"String Integer " );
		assertTrue(!parser.shortcutExists("o3"));
	}
	@Test
	public void testsAddAllOptionInvalidShortcut() {
		parser.addAll("option1 option2 " ,"o1 o2 o3" ,
				"String Integer " );
		assertTrue(!parser.shortcutExists("o3"));
	}
	@Test
	public void testsAddAllOptionGroupNameValidity() {
		parser.addAll("optiona-3 optionA-3 option1-a option1-A optiona-A !@#$%^&*()_+- 123321123 12option1-3" ,"o1 o2 o3" ,
				"String" );
		assertFalse(parser.shortcutExists("o1"));
		assertFalse(parser.shortcutExists("o2"));
		assertFalse(parser.shortcutExists("o3"));
	}

	@Test
	public void testsAddAllSingleOptionGroup() {
		parser.addAll("option1-3" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3"));
	}
	@Test
	public void testsAddAllSingleOptionGroupMoreType() {
		parser.addAll("option1-3" ,"o1 o2 o3" ,
				"String Integer" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testsAddAllSingleOptionGroupNoType() {
		parser.addAll("option1-3" ,"o1 o2 o3" ,
				"");
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3"));
	}

	@Test
	public void testsAddAllSingleOptionGroupWith_() {
		parser.addAll("option_1-3" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("option_1")==Type.STRING && parser.getType("option_2")==Type.STRING&&parser.getType("option_3")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option_1")&&parser.optionShortcutMatch("o2","option_2")
				&&parser.optionShortcutMatch("o3","option_3"));
	}
	@Test
	public void testsAddAllSingleOptionGroupRangeGraterTan10() {
		parser.addAll("option9-11" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("option9")==Type.STRING && parser.getType("option10")==Type.STRING&&parser.getType("option11")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option9")&&parser.optionShortcutMatch("o2","option10")
				&&parser.optionShortcutMatch("o3","option11"));
	}
	@Test
	public void testsAddAllSingleOptionGroupDecrease() {
		parser.addAll("option3-1" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option3")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option1"));
	}
	@Test
	public void testsAddAllAlphaOptionGroupDecrease() {
		parser.addAll("optionc-a" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("optiona")==Type.STRING && parser.getType("optionb")==Type.STRING&&parser.getType("optionc")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","optionc")&&parser.optionShortcutMatch("o2","optionb")
				&&parser.optionShortcutMatch("o3","optiona"));
	}
	@Test
	public void testsAddAllUpperAlphaOptionGroupDecrease() {
		parser.addAll("optionC-A" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("optionA")==Type.STRING && parser.getType("optionB")==Type.STRING&&parser.getType("optionC")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","optionC")&&parser.optionShortcutMatch("o2","optionB")
				&&parser.optionShortcutMatch("o3","optionA"));
	}
	@Test
	public void testsAddAllSingleOptionGroupNoShortcut() {
		parser.addAll("option1-3",
				"String" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING);
	}

	@Test
	public void testsAddAllSingleOptionGroupEqualRange() {
		parser.addAll("option9-9","o1","String");
		assertEquals(parser.getType("option9"), Type.STRING);
	}
	@Test
	public void testsAddAllSingleOptionGroupEqualLetter() {
		parser.addAll("optionb-b","o1","String");
		assertEquals(parser.getType("optionb"), Type.STRING);
	}
	@Test
	public void testsAddAllSingleOptionGroupEqualLetterUpper() {
		parser.addAll("optionB-B","o1","String");
		assertEquals(parser.getType("optionB"), Type.STRING);
	}
	@Test
	public void testsAddAllSingleOptionGroupAlpha() {
		parser.addAll("optiona-c" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("optiona")==Type.STRING && parser.getType("optionb")==Type.STRING&&parser.getType("optionc")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","optiona")&&parser.optionShortcutMatch("o2","optionb")
				&&parser.optionShortcutMatch("o3","optionc"));
	}
	@Test
	public void testsAddAllSingleOptionGroupAlphaUpper() {
		parser.addAll("optionA-C" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("optionA")==Type.STRING && parser.getType("optionB")==Type.STRING&&parser.getType("optionC")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","optionA")&&parser.optionShortcutMatch("o2","optionB")
				&&parser.optionShortcutMatch("o3","optionC"));
	}
	//this case only the first letter after dash will be considered
	@Test
	public void testsAddAllSingleOptionGroupAlphaMoreLetter() {
		parser.addAll("optionA-CBB" ,"o1 o2 o3" ,
				"String" );
		assertTrue(parser.getType("optionA")==Type.STRING && parser.getType("optionB")==Type.STRING&&parser.getType("optionC")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","optionA")&&parser.optionShortcutMatch("o2","optionB")
				&&parser.optionShortcutMatch("o3","optionC"));
		assertTrue(!parser.optionExists("optionCBB"));
	}
	@Test
	public void testsAddAllSingleOptionGroupEqualShortcutGroup() {
		parser.addAll("option1-5" ,"o1-1" ,
				"String" );
		assertTrue(parser.optionShortcutMatch("o1","option1"));
	}
	
	@Test
	public void testsAddAllSingleOptionGroupEqualShortcutGroupa() {
		parser.addAll("option1-5" ,"oa-a" ,
				"String" );
		assertTrue(parser.optionShortcutMatch("oa","option1"));
	}
	@Test
	public void testsAddAllSingleOptionGroupEqualShortcutGroupA() {
		parser.addAll("option1-5" ,"oA-A" ,
				"String" );
		assertTrue(parser.optionShortcutMatch("oA","option1"));
	}
	@Test
	public void testsAddAllSingleOptionGroupShortcutGroup() {
		parser.addAll("option1-5" ,"o1-5" ,
				"String" );
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","option4")
				&&parser.optionShortcutMatch("o5","option5"));
	}
	@Test
	public void testsAddAllSingleOptionGroupLessShortcut() {
		parser.addAll("option1-5" ,"o1 o2" ,
				"String");
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2"));
				
	}
	@Test
	public void testsAddAllSingleOptionGroupLessShortcutGroup() {
		parser.addAll("option1-5" ,"o1-2" ,
				"String");
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2"));
				
	}
	@Test
	public void testsAddAllSingleOptionGroupMoreshortcut() {
		parser.addAll("option1-5" ,"o1-5" ,
				"String" );
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","option4")
				&&parser.optionShortcutMatch("o5","option5"));
	}
	@Test
	public void testsAddAllMultipleOptionGroup() {
		parser.addAll("option1-3 opt1-2" ,"o1 o2 o3 o4 o5" ,
				"String Integer" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING
		&&parser.getType("opt1")==Type.INTEGER&&parser.getType("opt2")==Type.INTEGER);
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","opt1")
		&&parser.optionShortcutMatch("o5","opt2"));
	}
	@Test
	public void testsAddAllMultipleOptionGroupMoreType() {
		parser.addAll("option1-3 opt1-2" ,"o1 o2 o3 o4 o5" ,
				"String Integer Character" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING
				&&parser.getType("opt1")==Type.INTEGER&&parser.getType("opt2")==Type.INTEGER);
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","opt1")
				&&parser.optionShortcutMatch("o5","opt2"));
	}
	@Test
	public void testsAddAllMultipleOptionGroupLessType() {
		parser.addAll("option1-3 opt1-2" ,"o1 o2 o3 o4 o5" ,
				"String" );
		assertTrue(parser.getType("option1")==Type.STRING && parser.getType("option2")==Type.STRING&&parser.getType("option3")==Type.STRING
				&&parser.getType("opt1")==Type.STRING&&parser.getType("opt2")==Type.STRING);
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","opt1")
				&&parser.optionShortcutMatch("o5","opt2"));
	}
	@Test
	public void testsAddAllMultipleOptionGroupLessShortcut() {
		parser.addAll("option1-3 opt1-2" ,"o1 o2 o3 o4" ,
				"String Integer" );
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","opt1"));
	}
	@Test
	public void testsAddAllMultipleOptionGroupMoreShortcut() {
		parser.addAll("option1-3 opt1-2" ,"o1 o2 o3 o4 o5 o6 o7" ,
				"String Integer" );
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","opt1")
				&&parser.optionShortcutMatch("o5","opt2"));
		assertTrue(!parser.shortcutExists("o6"));
		assertTrue(!parser.shortcutExists("o7"));
	}
	@Test
	public void testsAddAllMultipleOptionGroupShortcutGroup() {
		parser.addAll("option1-3 opt1-2" ,"o1-3 t1-2" ,
				"String Integer" );
		assertTrue(parser.optionShortcutMatch("o1","option1")&&parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("t1","opt1")
				&&parser.optionShortcutMatch("t2","opt2"));
	}
	@Test
	public void testsAddAllSingleInvalidOptionGroup() {
		parser.addAll("option1-# option2-5" ,"o1-5" ,
				"String Integer" );
		assertTrue(parser.optionShortcutMatch("o2","option2")
				&&parser.optionShortcutMatch("o3","option3")&&parser.optionShortcutMatch("o4","option4")
				&&parser.optionShortcutMatch("o5","option5"));
		assertTrue(parser.getType("option2")==Type.INTEGER && parser.getType("option3")==Type.INTEGER&&parser.getType("option4")==Type.INTEGER
				&&parser.getType("option5")==Type.INTEGER);
	}
	//this case if there is an invalid option group, it will be removed, along with its type String, but its shortcut will only be consider as o1
	// not the group o1-5, according to piazza question @119
	@Test
	public void testsAddAllMultipleInvalidOptionGroup() {
		parser.addAll("option1-# opta-A option3-5" ,"o1-5" ,
				"String Character Integer" );
		assertTrue(parser.optionShortcutMatch("o3","option3")
				&&parser.optionShortcutMatch("o4","option4")&&parser.optionShortcutMatch("o5","option5"));
		assertTrue(parser.getType("option3")==Type.INTEGER && parser.getType("option4")==Type.INTEGER&&parser.getType("option5")==Type.INTEGER);
	}
	@Test
	public void testsAddAllMultipleInvalidOptionGroupLessType() {
		parser.addAll("option1-# opta-A option3-5" ,"o1-5" ,
				"String Integer" );
		assertTrue(parser.optionShortcutMatch("o3","option3")
				&&parser.optionShortcutMatch("o4","option4")&&parser.optionShortcutMatch("o5","option5"));
		assertTrue(parser.getType("option3")==Type.INTEGER && parser.getType("option4")==Type.INTEGER&&parser.getType("option5")==Type.INTEGER);
	}
	@Test
	public void testsAddAllMultipleInvalidOptionGroupLessTypeMid() {
		parser.addAll("option1-# option3-5 opta-A" ,"o1-5" ,
				"String Integer" );
		assertTrue(parser.optionShortcutMatch("o3","option3")
				&&parser.optionShortcutMatch("o4","option4")&&parser.optionShortcutMatch("o5","option5"));
		assertTrue(parser.getType("option3")==Type.INTEGER && parser.getType("option4")==Type.INTEGER&&parser.getType("option5")==Type.INTEGER);
	}
	@Test
	public void testsAddAllsingleInvalidShortcutGroup() {
		parser.addAll("optA-C option3-4" ,"oa-C o1-5" ,
				"String Character" );
		assertTrue(parser.optionShortcutMatch("o1","optA")
				&&parser.optionShortcutMatch("o2","optB")&&parser.optionShortcutMatch("o3","optC")
				&&parser.optionShortcutMatch("o4","option3")&&parser.optionShortcutMatch("o5","option4"));
		assertTrue(!parser.shortcutExists("oa"));
	}
	@Test
	public void testsAddAllMultipleInvalidShortcutGroup() {
		parser.addAll("optA-C option3-4" ,"oa-C o1-5 oA-#" ,
				"String Character" );
		assertTrue(parser.optionShortcutMatch("o1","optA")
				&&parser.optionShortcutMatch("o2","optB")&&parser.optionShortcutMatch("o3","optC")
				&&parser.optionShortcutMatch("o4","option3")&&parser.optionShortcutMatch("o5","option4"));
		assertTrue(!parser.shortcutExists("oa"));
		assertTrue(!parser.shortcutExists("oA"));
	}

	@Test
	public void testsAddAllSingleInvalidShortcutOptionGroup() {
		parser.addAll("optA-c option3-4" ,"o1-5 oA-#" ,
				"String Character" );
		assertTrue( parser.optionShortcutMatch("o2","option3")&&parser.optionShortcutMatch("o3","option4"));
		assertTrue(!parser.shortcutExists("oA"));
	}
	@Test
	public void testsAddAllMultipleInvalidShortcutOptionGroup() {
		parser.addAll("optA-c option3-4 option#" ,"o1-5 oA-# oA-c" ,
				"String Character" );
		assertTrue( parser.optionShortcutMatch("o2","option3")&&parser.optionShortcutMatch("o3","option4"));
		assertTrue(!parser.shortcutExists("oA"));
		assertTrue(!parser.shortcutExists("oc"));
	}
}
