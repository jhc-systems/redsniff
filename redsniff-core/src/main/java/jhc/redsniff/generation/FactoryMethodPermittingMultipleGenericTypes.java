/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
