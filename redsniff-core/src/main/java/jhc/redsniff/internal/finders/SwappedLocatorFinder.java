package jhc.redsniff.internal.finders;

import jhc.redsniff.core.Locator;

public  class SwappedLocatorFinder<E, C> // extends LocatorFinder<E, C> 
{


	public SwappedLocatorFinder(Locator<E, C> newLocator, LocatorFinder<E, C> originalFinder) {
	//	super(newLocator);
	//	addSwappedMatchers(newLocator, originalFinder.locator, originalFinder.matcherListFilter().matchers());
	}

//	private void addSwappedMatchers(Locator<E, C> newLocator, Locator<E,C> originalLocator, List<Matcher<? super E>> originalMatchers) {
//		add(locatorAsMatcher(originalLocator));
//		for(Matcher<? super E> matcher:originalMatchers)
//			if(matcher!=newLocator)
//			   add(matcher);
//	}
//
//	@SuppressWarnings("unchecked")
//	private Matcher<? super E> locatorAsMatcher(Locator<E, C> locator) {
//		return (Matcher<? super E>) locator;
//	}
//
//	@Override
//	protected boolean rootProviderCouldBeSwapped() {
//		return false;
//	}
//
//
//	@Override
//	public CollectionOf<E> findAndMatchFrom(C context,
//			Description notFoundDescription) {
//		CollectionOf<E> found = findBaseFrom(context);
////		if(found.isEmpty())
////			return originalFinder.findAndMatchFrom(context, notFoundDescription);
////		else
//			return filterResultsForMatchers(found, notFoundDescription);
//	}
//	
//	public static <E, C> SwappedLocatorFinder<E, C> swap(Locator<E, C> newLocator, LocatorFinder<E, C> originalFinder){
//		return new SwappedLocatorFinder<E, C>(newLocator, originalFinder);
//	}
}
