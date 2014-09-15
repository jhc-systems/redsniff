package jhc.redsniff.html.tables;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class IndexedWebElement  {
	private final int index;
	private final WebElement webElement;
	public IndexedWebElement(int index, WebElement webElement) {
		this.index = index;
		this.webElement = webElement;
	}
	public int index() {
		return index;
	}
	public WebElement webElement() {
		new IndexedWebElement(0,webElement);
		return webElement;
	}
	public static List<IndexedWebElement> indexedElements(List<WebElement> webElements){
		int i=0;
		List<IndexedWebElement> indexedList = new ArrayList<IndexedWebElement>();
		for(WebElement webElement:webElements)
			indexedList.add(new IndexedWebElement(i++, webElement));
		return indexedList;
	}
}
