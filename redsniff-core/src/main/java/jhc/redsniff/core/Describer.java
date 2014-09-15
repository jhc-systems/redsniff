package jhc.redsniff.core;


import jhc.redsniff.internal.describe.Describaliser;
import jhc.redsniff.internal.describe.DescribaliserRegistry;
import jhc.redsniff.internal.util.IndentingDescription;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;


public final class Describer {

    private Describer(){}
    
    public static Description indent(Description description) {
    	if(description instanceof IndentingDescription) {
    		((IndentingDescription) description).increaseIndentLevel();
    		description.appendText("\n");
    	}
    	else
    		description.appendText("\n\t");
    	return description;
    }

    public static Description outdent(Description description) {
    	if(description instanceof IndentingDescription) 
    		((IndentingDescription) description).decreaseIndentLevel();
    	return description;
    }

    public static Description concat(Description descriptionA, Description descriptionB){ //should combine indents ??
    	descriptionA.appendText(descriptionB.toString());
    	return descriptionA;
    }
    
   public static Description newDescription(){
    	return new IndentingDescription();
    }
    

    public static String asString(Object object){
    	return StringDescription.asString(describable(object));
    }
    
  static DescribaliserRegistry describaliserRegistry = new DescribaliserRegistry();
    	
  public static <T> void registerDescribaliser(Class<T> clazz, Describaliser<T> describaliser){
	  describaliserRegistry.register(clazz, describaliser);
  }
    
	@SuppressWarnings("unchecked")
	public static SelfDescribing describable(final Object object){
		Describaliser<Object> describing = describaliserRegistry.describaliserFor(object);
		return describing.describable(object);
    }   

	
}
