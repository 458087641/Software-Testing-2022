package st;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private OptionMap optionMap;
	
	public Parser() {
		optionMap = new OptionMap();
	}
	
	public void addOption(Option option, String shortcut) {
		optionMap.store(option, shortcut);
	}
	
	public void addOption(Option option) {
		optionMap.store(option, "");
	}
	
	public boolean optionExists(String key) {
		return optionMap.optionExists(key);
	}
	
	public boolean shortcutExists(String key) {
		return optionMap.shortcutExists(key);
	}
	
	public boolean optionOrShortcutExists(String key) {
		return optionMap.optionOrShortcutExists(key);
	}
	
	public int getInteger(String optionName) {
		String value = getString(optionName);
		Type type = getType(optionName);
		int result;
		switch (type) {
			case STRING:
			case INTEGER:
				try {
					result = Integer.parseInt(value);
				} catch (Exception e) {
			        try {
			            new BigInteger(value);
			        } catch (Exception e1) {
			        }
			        result = 0;
			    }
				break;
			case BOOLEAN:
				result = getBoolean(optionName) ? 1 : 0;
				break;
			case CHARACTER:
				result = (int) getCharacter(optionName);
				break;
			default:
				result = 0;
		}
		return result;
	}
	
	public boolean getBoolean(String optionName) {
		String value = getString(optionName);
		return !(value.toLowerCase().equals("false") 
				|| value.equals("0") 
				|| value.equals(""));
	}
	
	public String getString(String optionName) {
		return optionMap.getValue(optionName);
	}
	
	public char getCharacter(String optionName) {
		String value = getString(optionName);
		return value.equals("") ? '\0' :  value.charAt(0);
	}
	
	public void setShortcut(String optionName, String shortcutName) {
		optionMap.setShortcut(optionName, shortcutName);
	}
	
	public void replace(String variables, String pattern, String value) {
			
		variables = variables.replaceAll("\\s+", " ");
		
		String[] varsArray = variables.split(" ");
		
		for (int i = 0; i < varsArray.length; ++i) {
			String varName = varsArray[i];
			String var = (getString(varName));
			var = var.replace(pattern, value);
			if(varName.startsWith("--")) {
				String varNameNoDash = varName.substring(2);
				if (optionMap.optionExists(varNameNoDash)) {
					optionMap.setValueWithOptionName(varNameNoDash, var);
				}
			} else if (varName.startsWith("-")) {
				String varNameNoDash = varName.substring(1);
				if (optionMap.shortcutExists(varNameNoDash)) {
					optionMap.setValueWithOptionShortcut(varNameNoDash, var);
				} 
			} else {
				if (optionMap.optionExists(varName)) {
					optionMap.setValueWithOptionName(varName, var);
				}
				if (optionMap.shortcutExists(varName)) {
					optionMap.setValueWithOptionShortcut(varName, var);
				} 
			}

		}
	}
	
	private List<CustomPair> findMatches(String text, String regex) {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(text);
	    // Check all occurrences
	    List<CustomPair> pairs = new ArrayList<CustomPair>();
	    while (matcher.find()) {
	    	CustomPair pair = new CustomPair(matcher.start(), matcher.end());
	    	pairs.add(pair);
	    }
	    return pairs;
	}
	
	
	public int parse(String commandLineOptions) {
		if (commandLineOptions == null) {
			return -1;
		}
		int length = commandLineOptions.length();
		if (length == 0) {
			return -2;
		}	
		
		List<CustomPair> singleQuotePairs = findMatches(commandLineOptions, "(?<=\')(.*?)(?=\')");
		List<CustomPair> doubleQuote = findMatches(commandLineOptions, "(?<=\")(.*?)(?=\")");
		List<CustomPair> assignPairs = findMatches(commandLineOptions, "(?<=\\=)(.*?)(?=[\\s]|$)");
		
		
		for (CustomPair pair : singleQuotePairs) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\"", "{D_QUOTE}").
					  replaceAll(" ", "{SPACE}").
					  replaceAll("-", "{DASH}").
					  replaceAll("=", "{EQUALS}");
	    	
	    	commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(),pair.getY()), cmd);
		}
		
		for (CustomPair pair : doubleQuote) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\'", "{S_QUOTE}").
					  replaceAll(" ", "{SPACE}").
					  replaceAll("-", "{DASH}").
					  replaceAll("=", "{EQUALS}");
			
	    	commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(),pair.getY()), cmd);	
		}
		
		for (CustomPair pair : assignPairs) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\"", "{D_QUOTE}").
					  replaceAll("\'", "{S_QUOTE}").
					  replaceAll("-", "{DASH}");
	    	commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(),pair.getY()), cmd);	
		}

		commandLineOptions = commandLineOptions.replaceAll("--", "-+").replaceAll("\\s+", " ");


		String[] elements = commandLineOptions.split("-");
		
		
		for (int i = 0; i < elements.length; ++i) {
			String entry = elements[i];
			
			if(entry.isBlank()) {
				continue;
			}

			String[] entrySplit = entry.split("[\\s=]", 2);
			
			boolean isKeyOption = entry.startsWith("+");
			String key = entrySplit[0];
			key = isKeyOption ? key.substring(1) : key;
			String value = "";
			
			if(entrySplit.length > 1 && !entrySplit[1].isBlank()) {
				String valueWithNoise = entrySplit[1].trim();
				value = valueWithNoise.split(" ")[0];
			}
			
			// Explicitly convert boolean.
			if (getType(key) == Type.BOOLEAN && (value.toLowerCase().equals("false") || value.equals("0"))) {
				value = "";
			}
			
			value = value.replace("{S_QUOTE}", "\'").
						  replace("{D_QUOTE}", "\"").
						  replace("{SPACE}", " ").
						  replace("{DASH}", "-").
						  replace("{EQUALS}", "=");
			
			
			boolean isUnescapedValueInQuotes = (value.startsWith("\'") && value.endsWith("\'")) ||
					(value.startsWith("\"") && value.endsWith("\""));
			
			value = value.length() > 1 && isUnescapedValueInQuotes ? value.substring(1, value.length() - 1) : value;
			
			if(isKeyOption) {
				optionMap.setValueWithOptionName(key, value);
			} else {
				optionMap.setValueWithOptionShortcut(key, value);
				
			}			
		}

		return 0;
		
	}

	public void addAll(String optName,String shortcutName,String types){
		boolean isGroupOption = false;
		boolean isGroupShoortcut =false;
		if (optName==null){
			return;
		}
		if(shortcutName==null){
			shortcutName="";
		}
		if (types==null){
			throw new IllegalArgumentException("Illegal argument provided in types of options");
		}
		ArrayList<String> splitedOption =new ArrayList<String> (Arrays.asList(optName.split("\\s+")));

		ArrayList<String>splitedShortcut = new ArrayList<String> (Arrays.asList(shortcutName.split("\\s+")));

		ArrayList<String> splitedTypes = new ArrayList<String> (Arrays.asList(types.split("\\s+")));
		for (String i:splitedTypes){
			String iup=i.toUpperCase();
			if(!(iup.equals("INTEGER")||iup.equals("STRING")||iup.equals("BOOLEAN")||iup.equals("CHARACTER"))){
				throw new IllegalArgumentException("Illegal argument provided in types of options");
			}
		}
		//expand the list that store types, matches length of options, in this case, each option have a corresponding type
		ArrayList<String> expandedTypes =new ArrayList<String>();
		expandedTypes.addAll(splitedTypes);
		while (expandedTypes.size()<splitedOption.size()){
			String expand = splitedTypes.get(splitedTypes.size()-1);
			expandedTypes.add(expand);
		}

		//expand the shortcut if it has groups
		ArrayList<String> expandedShotcut =new ArrayList<String>();
		ArrayList<String> tempList;
		for(String i :splitedShortcut) {
			if (!checkShortcutValid(i)) {
				if (validGroup(i)) {
					tempList = expandGroups(i);
					expandedShotcut.addAll(tempList);
				}
			}else {expandedShotcut.add(i);}
		}


		//check the validity of the options
		ArrayList<String> tempSplitedOpt = new ArrayList<>();
		ArrayList<String> tempExpandedTypes=new ArrayList<>();
		ArrayList<String> tempRemoveShorcut = new ArrayList<>();
		//if there is an invalid option or option group, remove it and its corresponding type and shortcut
		for(int i =0; i<splitedOption.size();i++){
			if(!checkOptionValid(splitedOption.get(i))){
				if(validGroup(splitedOption.get(i))){
					tempSplitedOpt.add(splitedOption.get(i));
					tempExpandedTypes.add(expandedTypes.get(i));
				}else{
					try {
					tempRemoveShorcut.add(expandedShotcut.get(i));
					}catch(Exception e) {}
				}
			}else{
				tempSplitedOpt.add(splitedOption.get(i));
				tempExpandedTypes.add(expandedTypes.get(i));

			}
		}
		splitedOption=tempSplitedOpt;
		expandedShotcut.removeAll(tempRemoveShorcut);
		expandedTypes=tempExpandedTypes;

		//expand option groups and store

		ArrayList<String> tempListOptions;
		Integer shortcutUsed=0;
		for (int i =0; i<splitedOption.size();i++){
			if(validGroup(splitedOption.get(i))) {
				tempListOptions=expandGroups(splitedOption.get(i));
				Integer count =i;
				for(String j:tempListOptions){
					if(shortcutUsed<expandedShotcut.size()){
						addOption(new Option(j,parseStringToType(expandedTypes.get(i))),expandedShotcut.get(shortcutUsed));
						shortcutUsed+=1;
					}else{
						addOption(new Option(j,parseStringToType(expandedTypes.get(i))),"");
					}
				}
			}
			else{
				if(shortcutUsed<expandedShotcut.size()){
					Type x =parseStringToType(expandedTypes.get(i));
					addOption(new Option(splitedOption.get(i),x),expandedShotcut.get(shortcutUsed));
					shortcutUsed+=1;
				}
				else {
					addOption(new Option(splitedOption.get(i),parseStringToType(expandedTypes.get(i))),"");
				}
			}
			}
	}

	// the method span the groups in a way discribed i the specification
	// opt1-3 will return opt1,opt2,opt3; opt3-1 will return opt3,opt2,opt1, same with alphabet
	// if the group expand with alphabet, the first char after dash will be considered, others omitted
	// e.g. opta-cdf will only give opta optb optc.
	private ArrayList<String> expandGroups(String group){
		ArrayList<String> result = new ArrayList<String>();
		String[] splited = group.split("-");
		boolean isInt = true;
		Integer endInt =null;
		Integer startInt =null;
		try{
			endInt = Integer.parseInt(splited[1]);
		}catch (Exception e){
			endInt =null;
		}
		try{
			startInt =Integer.parseInt(splited[0].substring(splited[0].length()-1));
		}catch (Exception e){
			startInt=null;
		}
		if(!(startInt==null && endInt==null)){
			String temp = null;
			if(endInt>startInt){
				for(int i = startInt; i<=endInt;i++){
					temp = splited[0].substring(0,splited[0].length()-1).concat(Integer.toString(i));
					result.add(temp);
				}
			}
			if(endInt<startInt){
				for(int i = startInt; i>=endInt;i--){
					temp = splited[0].substring(0,splited[0].length()-1).concat(Integer.toString(i));
					result.add(temp);
				}
			}
			if (endInt==startInt){
				temp = splited[0].substring(0,splited[0].length()-1).concat(String.valueOf(endInt));
				result.add(temp);
			}
		}else{
			char startChar=splited[0].charAt(splited[0].length()-1);
			char endChar=splited[1].charAt(0);
			String temp = null;
			if (endChar>startChar){
				for (char i = startChar; i<=endChar;i++){
					temp = splited[0].substring(0,splited[0].length()-1).concat(String.valueOf(i));
					result.add(temp);
				}
			}
			if (endChar<startChar){
				for (char i =startChar ; i>=endChar;i--){
					temp = splited[0].substring(0,splited[0].length()-1).concat(String.valueOf(i));
					result.add(temp);
				}
			}
			if (endChar==startChar){
				temp = splited[0].substring(0,splited[0].length()-1).concat(String.valueOf(endChar));
				result.add(temp);
			}
		}
		return result;
	}


	public void addAll(String optName,String types){
		addAll(optName, "", types);
	}
	//regex rewirted to not allow option name start with number
	private boolean validGroup(String group){
		if (!group.matches("[a-zA-Z_](([A-Za-z0-9_])*(([A-Z]-[A-Z]+)|[a-z]-[a-z]+|[0-9]-[0-9]+))")) {
			return false;
		}else{
			return true;
		}
	}
	private boolean checkOptionValid(String name) {
		if (name == null) {
			return false;
		}
		if (name.isEmpty()) {
			return false;
		}
		if (!name.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
			return false;
		}
		return true;
	}
	private boolean checkShortcutValid(String shortcut){
		if (shortcut == null || !shortcut.isEmpty() && !shortcut.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
			return false;
		}
		return true;
	}
	public Type parseStringToType(String t){
		Type result = null;
		switch (t.toUpperCase()){
			case "STRING":
				result=Type.STRING;
				break;
			case "INTEGER":
				result=Type.INTEGER;
				break;
			case "CHARACTER":
				result=Type.CHARACTER;
				break;
			case "BOOLEAN":
				result=Type.BOOLEAN;
				break;

		}
		return result;
	}
	// for checking an option matches its shortcut
	public boolean optionShortcutMatch(String shortcut,String optionName){
		return optionMap.getShortcut(shortcut).getName().equals(optionName);
	}

	public Type getType(String option) {
		Type type = optionMap.getType(option);
		return type;
	}
	
	@Override
	public String toString() {
		return optionMap.toString();
	}

	
	private class CustomPair {
		
		CustomPair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	    private int x;
	    private int y;
	    
	    public int getX() {
	    	return this.x;
	    }
	    
	    public int getY() {
	    	return this.y;
	    }
	}
}
