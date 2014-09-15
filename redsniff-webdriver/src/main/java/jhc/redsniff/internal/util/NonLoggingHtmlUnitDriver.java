package jhc.redsniff.internal.util;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHtml;

//Temporary override of HtmlUnitDriver to get rid of pesky System.err.println() on every interaction
    public class NonLoggingHtmlUnitDriver extends HtmlUnitDriver{
        

        public NonLoggingHtmlUnitDriver(BrowserVersion version) {
            super(version);
        }

        @Override
        protected void assertElementNotStale(HtmlElement element) {
            SgmlPage elementPage = element.getPage();
            Page currentPage = lastPage();
            
            if (!currentPage.equals(elementPage)) {
              throw new StaleElementReferenceException(
                  "Element appears to be stale. Did you navigate away from the page that contained it? "
                  + " And is the current window focussed the same as the one holding this element?");
            }
            
            // We need to walk the DOM to determine if the element is actually attached
            DomNode parentElement = element;
            while (parentElement != null && !(parentElement instanceof HtmlHtml)) {
              parentElement = parentElement.getParentNode();
            }
//            ///REMOVED LINE below vvv
//            System.err.println("" + element + " -> " + parentElement);
            if (parentElement == null) {
              throw new StaleElementReferenceException(
                  "The element seems to be disconnected from the DOM. "
                  + " This means that a user cannot interact with it.");
            }
        }
        
    }