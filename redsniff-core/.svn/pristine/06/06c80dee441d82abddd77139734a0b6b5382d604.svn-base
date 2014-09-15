package jhc.redsniff.generation;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.generator.FactoryMethod;
import org.hamcrest.generator.ReflectiveFactoryReader;
/**
 * custom version for use in this library that:
 * 	 accepts a custom parent class (eg Finder, Matcher etc), so that only factory methods for that type will be found
 *   copes with return types having more than one parameterised type - original version in superclass only expects one (since Matcher only has one..)
 * largely copied from super class
 * @author InfanteN
 *
 */
public class FactoryMethodReader extends ReflectiveFactoryReader {

	private final Class<?> parentClass;
	private ClassLoader classLoader;
	private final Class<?> observedClass;
	public FactoryMethodReader(Class<?> observedClass,Class<?> parentClass) {
		super(observedClass);
		this.observedClass = observedClass;
		 this.classLoader = observedClass.getClassLoader();
		this.parentClass=parentClass;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean isFactoryMethod(Method javaMethod) {
		// TODO Auto-generated method stub
		// We dynamically load these classes, to avoid a compile time
		// dependency on org.hamcrest.{Factory,Matcher}. This gets around
		// a circular bootstrap issue (because generator is required to
		// compile core).
		Class factoryCls;
		
		try {
		    factoryCls = classLoader.loadClass("org.hamcrest.Factory");
		    
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot load hamcrest core", e);
		}
		return  javaMethod.getDeclaringClass().equals(observedClass)
				&& isStatic(javaMethod.getModifiers())
		        && isPublic(javaMethod.getModifiers())
		        && javaMethod.getAnnotation(factoryCls) != null
		        && parentClass.isAssignableFrom(javaMethod.getReturnType());
	}

	 @Override
	    public Iterator<FactoryMethod> iterator() {
	        return new Iterator<FactoryMethod>() {

	            private int currentMethod = -1;
	            private Method[] allMethods = observedClass.getMethods();

	            @Override
	            public boolean hasNext() {
	                while (true) {
	                    currentMethod++;
	                    if (currentMethod >= allMethods.length) {
	                        return false;
	                    } else if (isFactoryMethod(allMethods[currentMethod])) {
	                        return true;
	                    } // else carry on looping and try the next one.
	                }
	            }

	            @Override
	            public FactoryMethod next() {
	                if (outsideArrayBounds()) {
	                  throw new IllegalStateException("next() called without hasNext() check.");
	                }
	                return buildFactoryMethod(allMethods[currentMethod]);
	            }

	            @Override
	            public void remove() {
	                throw new UnsupportedOperationException();
	            }

	            private boolean outsideArrayBounds() {
	              return currentMethod < 0 || allMethods.length <= currentMethod;
	            }
	        };
	    }
	 private static FactoryMethod buildFactoryMethod(Method javaMethod) {
		 FactoryMethodPermittingMultipleGenericTypes result = new FactoryMethodPermittingMultipleGenericTypes(
	                classToString(javaMethod.getDeclaringClass()),
	                javaMethod.getName(), 
	                classToString(javaMethod.getReturnType()));

	        for (TypeVariable<Method> typeVariable : javaMethod.getTypeParameters()) {
	            boolean hasBound = false;
	            StringBuilder s = new StringBuilder(typeVariable.getName());
	            for (Type bound : typeVariable.getBounds()) {
	                if (bound != Object.class) {
	                    if (hasBound) {
	                        s.append(" & ");
	                    } else {
	                        s.append(" extends ");
	                        hasBound = true;
	                    }
	                    s.append(typeToString(bound));
	                }
	            }
	            result.addGenericTypeParameter(s.toString());
	        }
	        Type returnType = javaMethod.getGenericReturnType();
	        if (returnType instanceof ParameterizedType) {
	            ParameterizedType parameterizedType = (ParameterizedType) returnType;
	            List<String> generifiedTypes = new ArrayList<String>();
	            for(Type generifiedType:parameterizedType.getActualTypeArguments()){
	            	generifiedTypes.add(typeToString(generifiedType));
	            }
	            result.setGenerifiedTypes(generifiedTypes);
	        }

	        int paramNumber = 0;
	        for (Type paramType : javaMethod.getGenericParameterTypes()) {
	            String type = typeToString(paramType);
	            // Special case for var args methods.... String[] -> String...
	            if (javaMethod.isVarArgs()
	                    && paramNumber == javaMethod.getParameterTypes().length - 1) {
	                type = type.replaceFirst("\\[\\]$", "...");
	            }
	            result.addParameter(type, "param" + (++paramNumber));
	        }

	        for (Class<?> exception : javaMethod.getExceptionTypes()) {
	            result.addException(typeToString(exception));
	        }

	        return result;
	    }
	 
	 /*
	     * Get String representation of Type (e.g. java.lang.String or Map&lt;Stuff,? extends Cheese&gt;).
	     * <p/>
	     * Annoyingly this method wouldn't be needed if java.lang.reflect.Type.toString() behaved consistently
	     * across implementations. Rock on Liskov.
	     */
	    private static String typeToString(Type type) {
	        return type instanceof Class<?> ?  classToString((Class<?>) type): type.toString();
	    }

	    private static String classToString(Class<?> cls) {
	      final String name = cls.isArray() ? cls.getComponentType().getName() + "[]" : cls.getName();
	      return name.replace('$', '.');
	    }
}
