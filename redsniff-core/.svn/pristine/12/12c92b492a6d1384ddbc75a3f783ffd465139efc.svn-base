package jhc.redsniff.generation;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.generator.FactoryMethod;


public class FactoryMethodPermittingMultipleGenericTypes extends FactoryMethod {

	private List<String> generifiedTypes;
	
	public FactoryMethodPermittingMultipleGenericTypes(String matcherClass,
			String factoryMethod, String returnType) {
		super(matcherClass, factoryMethod, returnType);
	}

	@Override
	public void setGenerifiedType(String generifiedType) {
		List<String> generifiedTypes = new ArrayList<String>();
		generifiedTypes.add(generifiedType);
		this.setGenerifiedTypes(generifiedTypes);
	}

	@Override
	public String getGenerifiedType() {
		if(generifiedTypes!=null)
			return generifiedTypes.get(0);
		else
			return null;
	}

	public void setGenerifiedTypes(List<String> generifiedTypes) {
		this.generifiedTypes = generifiedTypes;
		super.setGenerifiedType(this.getGenerifiedType());
	}
	
	public List<String> getGenerifiedTypes(){
		return generifiedTypes;
	}
}
