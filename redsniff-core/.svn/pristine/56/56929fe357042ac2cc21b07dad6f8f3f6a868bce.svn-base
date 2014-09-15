package jhc.redsniff.internal.describe;

import java.util.Collection;
import java.util.LinkedHashMap;

import jhc.redsniff.internal.core.Item;

import org.hamcrest.SelfDescribing;

public class DescribaliserRegistry {

	LinkedHashMap<Class<?>, Describaliser<?>> describaliserMaps = new LinkedHashMap<>();

	public DescribaliserRegistry() {
		addDefaultEntries();
	}

	private void addDefaultEntries() {
		register(SelfDescribing.class,
				new SelfDescribingDescribaliser());

	}

	public <T> void register(Class<T> clazz, Describaliser<T> describaliser) {
		describaliserMaps.put(clazz, describaliser);
	}

	public Describaliser describaliserFor(Object object) {
		if (object instanceof Item)
			return new ItemDescribaliser(
					describaliserFor(((Item<?>) object).get()));
		if (object instanceof Collection) {
			Collection<?> items = (Collection<?>) object;
			if (items != null && !items.isEmpty()) {
				Object firstItemForTypeInference = items.iterator().next();
				return new CollectionDescribaliser(
						describaliserFor(firstItemForTypeInference));
			}
		}
		for (Class<?> clazz : describaliserMaps.keySet()) {
			if (clazz.isAssignableFrom(object.getClass())) {
				return describaliserMaps.get(clazz);
			}
		}
		return new ObjectDescribaliser();
	}
}
