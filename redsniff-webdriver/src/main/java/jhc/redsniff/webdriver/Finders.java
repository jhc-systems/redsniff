// Generated source.
package jhc.redsniff.webdriver;

import static jhc.redsniff.core.DescribedAs.describedAs;
import static jhc.redsniff.webdriver.Transformers.toAttribute;
import static jhc.redsniff.webdriver.Transformers.toJavascriptLocationLink;
import jhc.redsniff.action.ActionableFinder;
import jhc.redsniff.core.DescribedAs;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableItem;
import jhc.redsniff.html.tables.TableRow;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.finders.AsActionableFinder;
import jhc.redsniff.internal.finders.OnlyFinder;
import jhc.redsniff.internal.finders.OrdinalFinder;
import jhc.redsniff.internal.finders.TransformingContextSFinder;
import jhc.redsniff.webdriver.finders.ByFinder;
import jhc.redsniff.webdriver.finders.HtmlTagFinders;
import jhc.redsniff.webdriver.finders.TitleFinder;
import jhc.redsniff.webdriver.table.ElementOfTableItemFinder;
import jhc.redsniff.webdriver.table.TableCellInRowFinder;
import jhc.redsniff.webdriver.table.TableFinder;
import jhc.redsniff.webdriver.table.TableRowInTableFinder;

import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class Finders {

    private Finders() {
    }

    public static MFinder<WebElement, SearchContext> tag(String tagName) {
        return elementFound(By.tagName(tagName));
    }
    
    public static  MFinder<WebElement, SearchContext> $(String cssSelector) {
        return describedAs("$(\""+cssSelector +"\")", ByFinder.foundBy(By.cssSelector(cssSelector)));
    }

    public static MFinder<WebElement, SearchContext> elementFound(By by) {
        return ByFinder.foundBy(by);
    }


    /**
     * Creates a finder for finding the nth item found by the wrapped finder. If
     * less than n elements are found that is included in the diagnostics
     * 
     * @param n
     * @param finder
     *            - the finder to get the nth E element from context of type C
     * @return
     */
    public static <E, C> SFinder<E, C> nth(int n, MFinder<E, C> finder) {
        return OrdinalFinder.<E, C> nth(n, finder);
    }
    
    /**
     * Creates a finder for finding the first item found by the wrapped finder.
     * If no elements are found by the finder then that is included in the
     * diagnostics
     * 
     * @param finder
     *            - the finder to get the first element of
     * @return
     */
    public static <E, C> SFinder<E, C> first(MFinder<E, C> finder) {
        return OrdinalFinder.<E, C> first(finder);
    }
    
    /**
     * 
     * 
     * @see #nth(int, MFinder)
     * @param finder
     * @return
     */
    public static <E, C> SFinder<E, C> second(MFinder<E, C> finder) {
    	return OrdinalFinder.<E, C> second(finder);
    }

    /**
     * 
     * 
     * @see #nth(int, MFinder)
     * @param finder
     * @return
     */
    public static <E, C> SFinder<E, C> third(MFinder<E, C> finder) {
        return OrdinalFinder.<E, C> third(finder);
    }

    /**
     * 
     * 
     * @see #nth(int, MFinder)
     * @param finder
     * @return
     */
    public static <E, C> SFinder<E, C> fourth(MFinder<E, C> finder) {
        return OrdinalFinder.<E, C> fourth(finder);
    }

    /**
     * 
     * 
     * @see #nth(int, MFinder)
     * @param finder
     * @return
     */
    public static <E, C> SFinder<E, C> fifth(MFinder<E, C> finder) {
        return OrdinalFinder.<E, C> fifth(finder);
    }

    /**
     * 
     * 
     * @see #nth(int, MFinder)
     * @param finder
     * @return
     */
    public static <E, C> SFinder<E, C> last(MFinder<E, C> finder) {
        return OrdinalFinder.<E, C> last(finder);
    }

    public static MFinder<Table, SearchContext> table(
            MFinder<WebElement, SearchContext> tableElementFinder) {
        return TableFinder.table(tableElementFinder);
    }

    public static MFinder<TableCell, TableRow> cell() {
        return TableCellInRowFinder.cell();
    }

    public static MFinder<TableRow, Table> row() {
        return TableRowInTableFinder.row();
    }

    public static MFinder<WebElement, SearchContext> tableElement() {
        return HtmlTagFinders.tableElement();
    }

    public static MFinder<WebElement, SearchContext> dropDown() {
        return HtmlTagFinders.dropDown();
    }

    public static MFinder<WebElement, SearchContext> tableHeaderElement() {
        return HtmlTagFinders.tableHeaderElement();
    }

    public static MFinder<WebElement, SearchContext> dropDownOption() {
        return HtmlTagFinders.dropDownOption();
    }

    public static MFinder<WebElement, SearchContext> dropDownOption(String optionLabel) {
        return HtmlTagFinders.dropDownOption(optionLabel);
    }

    public static MFinder<WebElement, SearchContext> dropDownOption(
            Matcher<String> optionLabelMatcher) {
        return HtmlTagFinders.dropDownOption(optionLabelMatcher);
    }

    public static MFinder<WebElement, SearchContext> body() {
        return HtmlTagFinders.body();
    }

    public static MFinder<WebElement, SearchContext> div() {
        return HtmlTagFinders.div();
    }

    public static MFinder<WebElement, SearchContext> iFrame() {
        return HtmlTagFinders.iFrame();
    }

    public static MFinder<WebElement, SearchContext> listItem() {
        return HtmlTagFinders.listItem();
    }

    public static MFinder<WebElement, SearchContext> list() {
        return HtmlTagFinders.list();
    }

    public static MFinder<WebElement, SearchContext> span() {
        return HtmlTagFinders.span();
    }

    public static MFinder<WebElement, SearchContext> link(String anchorText) {
        return HtmlTagFinders.link(anchorText);
    }

    public static MFinder<WebElement, SearchContext> link() {
        return HtmlTagFinders.link();
    }

    public static MFinder<WebElement, SearchContext> textbox() {
        return HtmlTagFinders.textbox();
    }

    public static MFinder<WebElement, SearchContext> textArea() {
        return HtmlTagFinders.textarea();
    }

    public static MFinder<WebElement, SearchContext> inputFinderForType(
            org.hamcrest.Matcher<String> typeMatcher) {
        return HtmlTagFinders.inputThatHasType(typeMatcher);
    }

    public static MFinder<WebElement, SearchContext> inputFinderForType(String type) {
        return HtmlTagFinders.inputFinderForType(type);
    }

    public static MFinder<WebElement, SearchContext> passwordField() {
        return HtmlTagFinders.passwordField();
    }

    public static MFinder<WebElement, SearchContext> checkbox() {
        return HtmlTagFinders.checkbox();
    }

    public static MFinder<WebElement, SearchContext> imageButton(String label) {
        return HtmlTagFinders.imageButton(label);
    }

    public static MFinder<WebElement, SearchContext> imageButton() {
        return HtmlTagFinders.imageButton();
    }

    public static MFinder<WebElement, SearchContext> radioButton(String id) {
        return HtmlTagFinders.radioButton(id);
    }

    public static MFinder<WebElement, SearchContext> radioButton() {
        return HtmlTagFinders.radioButton();
    }

    public static MFinder<WebElement, SearchContext> submitButton() {
        return HtmlTagFinders.submitButton();
    }

    public static MFinder<WebElement, SearchContext> submitButton(String label) {
        return HtmlTagFinders.submitButton(label);
    }

    /**
     * Note this finds a *String* not a WebElement, since there is no way to get
     * a valid &lt;title&gt; WebElement except in HtmlUnit
     */
    public static MFinder<String, SearchContext> title() {
        return TitleFinder.title();
    }

    /**
     * Note this finds a *String* not a WebElement, since there is no way to get
     * a valid &lt;title&gt; WebElement except in HtmlUnit
     */
    public static MFinder<String, SearchContext> title(String title) {
        return TitleFinder.title(title);
    }

    public static MFinder<WebElement, SearchContext> button(String label) {
        return HtmlTagFinders.button(label);
    }

    public static MFinder<WebElement, SearchContext> button() {
        return HtmlTagFinders.button();
    }

    public static MFinder<WebElement, SearchContext> image() {
        return HtmlTagFinders.image();
    }

    public static MFinder<WebElement, SearchContext> form() {
        return HtmlTagFinders.form();
    }

    public static MFinder<WebElement, SearchContext> textElement() {
        return HtmlTagFinders.textElement();
    }

    public static <E, Q extends Quantity<E>, C> SFinder<E, C> only(Finder<E, Q, C> finder) {
        return OnlyFinder.only(finder);
    }

    public static <E, C> ActionableFinder<E, CollectionOf<E>, C> each(
    		MFinder<E, C> finder) {
    	return AsActionableFinder.each(finder);
    }
    
    public static <T extends TableItem, C> MFinder<WebElement, C> elementOf(
            MFinder<T, C> tableItemFinder) {
        return ElementOfTableItemFinder.elementOf(tableItemFinder);
    }
    
    public static SFinder<String, WebElement> attribute(String attribute){
        return new TransformingContextSFinder<String, WebElement>(toAttribute(attribute));
    }
    
    public static SFinder<String, WebElement> javascriptRedirectOnAttribute(String attribute){
        return new TransformingContextSFinder<String, WebElement>(toJavascriptLocationLink(attribute));
    }
    
    public static SFinder<String, SearchContext> downloadLinkFromAttribute(MFinder<WebElement, SearchContext> elementFinder, String attribute){
        return attribute(attribute).within(only(elementFinder));
    }
    
    public static SFinder<String, SearchContext> downloadLinkFromJavascriptOnClick(MFinder<WebElement, SearchContext> elementFinder){
       return javascriptRedirectOnAttribute("onclick").within(only(elementFinder));
    }
}
