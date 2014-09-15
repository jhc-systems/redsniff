package jhc.redsniff.internal.util;

public final class StringHolder {
	private String string;

	public StringHolder(String string) {
		this.string = string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String toString() {
		return string;
	}
}