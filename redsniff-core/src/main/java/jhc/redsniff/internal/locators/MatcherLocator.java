package jhc.redsniff.internal.locators;

import jhc.redsniff.core.Locator;

import org.hamcrest.Matcher;

public interface MatcherLocator<E, C> extends Matcher<E>, Locator<E, C> {

    public boolean canBehaveAsLocator();
    public int specifity();
}
