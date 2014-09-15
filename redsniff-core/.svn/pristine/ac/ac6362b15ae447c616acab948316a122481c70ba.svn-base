package jhc.redsniff.internal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import difflib.Chunk;
import difflib.Delta;
import difflib.Patch;
import difflib.myers.MyersDiff;

//TODO - tidy up. fix working for multi-line. increase sensitivity and adjust tests
public class StringDiffFormatter {

	private int sensitivity = 3;

	public String formatDifference(String expected, String actual, String format) {
		List<String> expectedLines=Splitter.on("|||").splitToList(expected);
		List<String> actualLines=Splitter.on("|||").splitToList(actual);
		
		MyersDiff myersDiff = new MyersDiff();
		Patch diff = myersDiff.diff(expectedLines, actualLines);
		
		
	    List<String> expectedDifferences = new ArrayList<>(expectedLines.size());
	    expectedDifferences.addAll(expectedLines);
	    List<String> actualDifferences = new ArrayList<>(actualLines.size());
	    actualDifferences.addAll(actualLines);
	    
	    for (Delta delta : diff.getDeltas()) {
	    		
	    	  List<String> orig = (List<String>) delta.getOriginal().getLines();
	          List<String> rev = (List<String>) delta.getRevised().getLines();
	          
	         String origString = Joiner.on("\n").join(orig);
	         List<String> origCharList = asStringList(origString);
	         String revString = Joiner.on("\n").join(rev);
	         List<String> revCharList = asStringList(revString);
	
	         List<Delta> inlineDeltas = myersDiff.diff(origCharList,revCharList).getDeltas();
	         
			if(inlineDeltas.size() < sensitivity){
	         Collections.reverse(inlineDeltas);
		         for (Delta inlineDelta : inlineDeltas) {
		             Chunk inlineOrig = inlineDelta.getOriginal();
		             Chunk inlineRev = inlineDelta.getRevised();
	                 origString = highlight(origString, inlineOrig.getPosition(), inlineOrig
	                         .getPosition()
	                         + inlineOrig.size() );
	                 revString = highlight(revString, inlineRev.getPosition(), inlineRev.getPosition()
	                         + inlineRev.size() );
		        	 }
		         } else {
		        	 Delta firstDelta = inlineDeltas.get(0);
		        	 Delta lastDelta = inlineDeltas.get(inlineDeltas.size()-1);
					origString = highlight(origString, firstDelta.getOriginal().getPosition(),lastDelta.getOriginal().getPosition()+lastDelta.getOriginal().size() );
					revString = highlight(revString, firstDelta.getRevised().getPosition(),lastDelta.getRevised().getPosition()+lastDelta.getRevised().size() );
					
			}
	             
	         
	         expectedDifferences.set(delta.getOriginal().getPosition(), origString);
	         actualDifferences.set(delta.getRevised().getPosition(), revString);
	
	    }
	    String formattedExpected = "";
	    for(int i=0;i<expectedDifferences.size();i++){
	    	String diffLine = expectedDifferences.get(i);
			String line = diffLine!=null?diffLine:expectedLines.get(i);
			formattedExpected+=line;
	    }
	    String formattedActual="";
	    for(int i=0;i<actualDifferences.size();i++){
	    	String diffLine = actualDifferences.get(i);
			String line = diffLine!=null?diffLine:actualLines.get(i);
			formattedActual+=line;
	    }
	    return String.format(format,formattedActual, formattedExpected);
	}

	private List<String> asStringList(String revString) {
		List<String> strList = new ArrayList<String>(revString.length());
		for(Character c:revString.toCharArray())
			strList.add(c.toString());
		return strList;
	}

	private String highlight(String string, int start, int end) {
		return string.substring(0,start)+"["+string.substring(start,end)+"]"+string.substring(end);
	}
	
}