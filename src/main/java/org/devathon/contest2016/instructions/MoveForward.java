package org.devathon.contest2016.instructions;

import java.util.Arrays;

public class MoveForward extends Instruction {
	
	public MoveForward(int blocks) {
		this.parameters = Arrays.asList(new Parameter(ParameterType.NUMBER, ""+blocks));
	}
	
	@Override
	public void execute() {
		
	}
}
