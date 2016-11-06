package org.devathon.contest2016.instructions;

public class Parameter {

	private ParameterType type;
	private String value;
	
	public Parameter(ParameterType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public ParameterType getType() {
		return this.type;
	}
	
	public String getValue() {
		return this.value;
	}
}
