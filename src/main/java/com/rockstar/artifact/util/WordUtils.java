package com.rockstar.artifact.util;

import java.util.List;

import org.jsonschema2pojo.util.Inflector;
import org.springframework.util.StringUtils;

public class WordUtils {
	
	public static final String UNDERSCORE_CHARACTER = "_";
	public static final String PATH_SEPARATOR = "/";
	
	private static Inflector inflector = Inflector.getInstance();
	
	public static String singleQuote(String word) {
		return String.format("\'%s\'", word);
	}
	
	public static String uncapitalizeSingular(String word) {
		return inflector.singularize(StringUtils.uncapitalize(word));
	}
	
	public static String uncapitalizePlural(String word) {
		return inflector.pluralize(StringUtils.uncapitalize(word));
	}

	public static String capitalizeSingular(String word) {
		return inflector.singularize(StringUtils.capitalize(word));
	}
	
	public static String capitalizePlural(String word) {
		return inflector.pluralize(StringUtils.capitalize(word));
	}
	
	public static String camelCaseToUpperSnakeCase(String word) {
		String[] words = org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase(word);
		return org.apache.commons.lang3.StringUtils.join(words, UNDERSCORE_CHARACTER).toUpperCase();
	}
	
	public static String snakecaseToCamelcase(String word) {
		String[] words = org.apache.commons.lang3.StringUtils.split(word, UNDERSCORE_CHARACTER);
		String[] newWords = null;
		
		if (words != null && words.length > 0) {
			newWords = new String[words.length];
		
			for (int wordcount = 0; wordcount < words.length; wordcount++) {
				newWords[wordcount] = StringUtils.capitalize(words[wordcount]);
			}
		}
		return org.apache.commons.lang3.StringUtils.join(newWords);
	}
	
	public static String camelcase(String text) {
		CheckUtils.checkArgumentNotNullOrEmpty(text);
		String uncapitalizeCamelCaseString = null;
		StringBuilder camelcaseStringBuilder = new StringBuilder();
		String[] words = StringUtils.delimitedListToStringArray(text, UNDERSCORE_CHARACTER);
		for (String currentWord : words) {
			camelcaseStringBuilder.append(capitalizeSingular(currentWord));
		}
		uncapitalizeCamelCaseString = StringUtils.uncapitalize(camelcaseStringBuilder.toString());
		return uncapitalizeCamelCaseString;
	}
	
	public static String fromPathCamelcase(String path) {
		String word = org.apache.commons.lang3.StringUtils.substringAfterLast(path, PATH_SEPARATOR);
		if (!StringUtils.isEmpty(word)) {
			word = capitalizeSingular(word);	
		}
		return word;
	}
	
	public static String linkName(String uriString) {
    	String[] uritokens = StringUtils.split(uriString, "/");
    	return ( uritokens != null && uritokens.length > 0 ? uritokens[0]: "" );
    }
	
	public static String joinByAnd(List<String> words) {
		return org.apache.commons.lang3.StringUtils.join(words, "And");
	}
    

}
