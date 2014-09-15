package jhc.redsniff.internal.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Ignore;
import org.junit.Test;

public class StringDiffFormatterTest {

	
	
	private static final String FORMAT = "<<'%1$s'>>\ninstead of:\n<<'%2$s'>>";

	@Test
	public void compactBlankBlank(){
		assertResult(compactedDifference("", ""),"","");
	}
	
	private void assertResult(String result, String compactedExpected,String compactedActual) {
		assertThat(result, is(String.format(FORMAT, compactedActual, compactedExpected)));
	}


	@Test
	public void compactEqualEqual(){
		assertResult(compactedDifference("abc", "abc"),"abc","abc");
	}
	
	@Test
	public void compact1CharDifferenceAtStart(){
		assertResult(compactedDifference("Aabc", "abc"),"[A]abc","[]abc");
	}
	
	@Test
	public void compact1CharDifferenceAtEnd(){
		assertResult(compactedDifference("abcA", "abc"),"abc[A]","abc[]");
	}
	
	@Test
	public void completelyDifferent(){
		assertResult(compactedDifference("abcde", "xyz"),"[abcde]","[xyz]");
	}
	
	
	@Test
	public void compact1CharDifferenceInside(){
		assertResult(compactedDifference("aAbc", "abc"),"a[A]bc","a[]bc");
	}
	
	@Test
	public void compact2SeparateDifferences(){
		assertResult(compactedDifference("aABCd", "ABC"),"[a]ABC[d]","[]ABC[]");
	}
	
	@Test
	@Ignore("not yet working")
	public void compact100CharDifferenceInside(){
		assertResult(compactedDifference("a1234567890123456789012345678901234567890b123456789012345678901234567890123456789012345678901234567890c", "abc"),
										"a[1234567890123456789012345678901234567890b123456789012345678901234567890123456789012345678901234567890]c","a[b]c");
	}
	
	@Test
	@Ignore("not yet working")
	public void compact100CharPrefixInside(){
			assertResult(compactedDifference("a1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890bc", 
											"a1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890bd"),
										"...2345678901234567890b[c]",
										"...2345678901234567890b[d]");
	}
	
	//TODO - formatter. move the 20 char limit to the formatter
	//consider enhancing to show *all* differences  

	
	private String compactedDifference(String originalExpected, String originalActual) {
		return new StringDiffFormatter().formatDifference(originalExpected, originalActual, FORMAT);
//		int indexOfCommonPrefix = StringUtils.indexOfDifference(originalExpected, originalActual);
//		if(indexOfCommonPrefix==StringUtils.INDEX_NOT_FOUND)//equal strings
//			return new StringDifference(originalExpected, originalActual);
//		else {
//			String commonPrefix = originalExpected.substring(0,indexOfCommonPrefix);
//
//			int indexFromEndOfCommonSuffix = StringUtils.indexOfDifference(StringUtils.reverse(originalExpected), StringUtils.reverse(originalActual));
//			int indexFromStartOfCommonSuffixInExpected = originalExpected.length() - indexFromEndOfCommonSuffix;
//			int indexFromStartOfCommonSuffixInActual = originalActual.length() - indexFromEndOfCommonSuffix;
//			
//			String commonSuffix = originalExpected.substring(indexFromStartOfCommonSuffixInExpected);
//			String expectedDifferentBit=originalExpected.substring(indexOfCommonPrefix,indexFromStartOfCommonSuffixInExpected);
//			String actualDifferentBit=originalActual.substring(indexOfCommonPrefix,indexFromStartOfCommonSuffixInActual);
//			
//			if(commonPrefix.length()> 20)
//				commonPrefix = "..."+StringUtils.right(commonPrefix, 20);
//			if(commonSuffix.length()>20)
//				commonSuffix = StringUtils.left(commonSuffix, 20) + "...";
//			
//			String compactedExpected = commonPrefix+"["+expectedDifferentBit+"]" + commonSuffix;
//			String compactedActual = commonPrefix +"["+actualDifferentBit+"]"+commonSuffix;
//			return new StringDifference(compactedExpected, compactedActual);
//		}
	}
	
}
