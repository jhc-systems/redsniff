package jhc.redsniff.wicket;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByXPath;
public final class ByWicketPath extends By {

	private final String xpathExpression;
	private final String wicketPath;

	/**
	 * Creates a new instance of {@link ByWicketPath}.
	 * @param wicketPath the wicket path (eg: "id1:id2:id3")
	 */
	private ByWicketPath(String wicketPath) {
		this.wicketPath = wicketPath;
		this.xpathExpression = convert(wicketPath);
	}

	/**
	 * Factory method to create this specific By.
	 * @param wicketXPath the wicket path (eg: "id1:id2:id3")
	 * @return By of type ByWicketPath
	 */
	public static By byWicketXPath(String wicketXPath) {
		return new ByWicketPath(wicketXPath);
	}

	private String convert(String wicketPath) {
		return wicketPath.replaceAll("\\w\\{(.*?)\\}", "*[@*[name()='wicket:id']='$1']");
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		return ((FindsByXPath) context).findElementsByXPath(xpathExpression);
	}

	@Override
	public WebElement findElement(SearchContext context) {
		return ((FindsByXPath) context).findElementByXPath(xpathExpression);
	}

	@Override
	public String toString() {
		return "By.wicketPath: " + wicketPath  ;
	}
	
	public static By byWicketPath(String wicketPath){
	    return byWicketXPath(convertToWicketXPath(wicketPath));
	}

    public static String convertToWicketXPath(String wicketFormatPath) {
       return addXpathW(increaseEachNumberByOne(convertDigitsToUseSquareBrackets(replaceColonsWithBraces(wicketFormatPath))));
    }

     private static String addXpathW(String string) {
         return string.replaceAll("\\{", "//w\\{");
     }

    private static String replaceColonsWithBraces(String wicketFormatPath) {
        return wicketFormatPath.replaceAll("([^:]+)", "\\{$1\\}").replaceAll("\\:","");
    }

    private static String increaseEachNumberByOne(String wicketFormatPath) {
        StringBuffer buff = new StringBuffer();
        boolean inBrackets = false;
        int currentNumber=0;
        int radix=-1;
        for(int i = 0;i<wicketFormatPath.length();i++){
            char c = wicketFormatPath.charAt(i);
            
            if(!inBrackets)
                buff.append(c);
            
            if(c=='[')
                inBrackets=true;
            
            else if(c==']'){
                inBrackets=false;
                buff.append(++currentNumber).append("]");
                currentNumber=0;
                radix=-1;
            }
            else if(inBrackets){
                int digit = c - '0';
                radix++;
                currentNumber = (int)(Math.pow(10d, (double)radix))*currentNumber + digit ;
            }
            
        }
        return buff.toString();
    }

     static String convertDigitsToUseSquareBrackets(String string) {
       return string.replaceAll("\\{(\\d+)\\}","\\[$1\\]");
    }

}

