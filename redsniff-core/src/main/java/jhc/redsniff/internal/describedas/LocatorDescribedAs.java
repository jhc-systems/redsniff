package jhc.redsniff.internal.describedas;

import jhc.redsniff.core.Locator;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public class LocatorDescribedAs<E, C> implements Locator<E, C> {

    private String customDescriptionText;
    private Locator<E, C> locator;

    public LocatorDescribedAs(String customDescriptionText, Locator<E, C> locator) {
        this.customDescriptionText = customDescriptionText;
        this.locator = locator;
    }

    @Override
    public CollectionOf<E> findElementsFrom(C context) {
        return locator.findElementsFrom(context);
    }

    @Override
    public String nameOfAttributeUsed() {
        return locator.nameOfAttributeUsed();
    }

    @Override
    public void describeLocatorTo(Description description) {
        description.appendText(customDescriptionText);
    }
}