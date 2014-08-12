package gov.va.escreening.variableresolver;

import java.util.List;

public class ContentUtilty {
	
	public static String buildParagraph(List<String> sentenceList) {
		
		if(sentenceList == null || sentenceList.size() == 0) {
			return "The Veteran did not answer the survey questions required to generate this section of the Clinical Note.";
		}
		
		StringBuilder paragraph = new StringBuilder();
		for(String sentence : sentenceList) {
			if(paragraph.length() == 0)
				paragraph.append(sentence);
			else
				paragraph.append("  ").append(sentence);
		}
        return paragraph.toString();
	}
	
	public static String buildSentenceFragmentFromListWithAnd(List<String> segmentList) {
		
		StringBuilder fragment = new StringBuilder();
		int count = 0;
		for(String segment : segmentList) {
			if(count == 0)
				fragment.append(" ").append(segment);
			else {
				if( (segmentList.size() > 1) && (count == segmentList.size()-1) ) 
					fragment.append(", and ").append(segment);
				else
					fragment.append(", ").append(segment);
			}
			count = count + 1;
		}
		
		return fragment.toString();
	}
	
	public static String getFormattedName(String firstName, String lastName) {
		StringBuilder fullName = new StringBuilder("");
		
		if(firstName != null && !firstName.isEmpty())
			fullName.append(firstName);
		
		if(lastName != null && !lastName.isEmpty() ) {
			if(fullName.length() > 0)
				fullName.append(" ");
			
			fullName.append(lastName);
		}
		
		return fullName.toString();
	}

}
