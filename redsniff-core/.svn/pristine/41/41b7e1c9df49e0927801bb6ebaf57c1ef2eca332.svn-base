package jhc.redsniff.internal.util;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.StringDescription;

public class IndentingDescription extends StringDescription{

	private int indentLevel;
	
	@Override
	protected void append(String str) {
		if(indentLevel>0 && str!=null){
			String indented = str.replaceAll("\\n", indentedNewLine());
			super.append(indented);
		}
		else
			super.append(str);
	}

	private String indentedNewLine() {
		return "\n"+StringUtils.leftPad("",indentLevel,"\t");
	}

	@Override
	protected void append(char c) {
		if(c=='\n')
			super.append(indentedNewLine());
		else
			super.append(c);
	}

	public void increaseIndentLevel() {
		indentLevel++;
	}
	
	public void decreaseIndentLevel() {
		indentLevel--;
	}
	
}
