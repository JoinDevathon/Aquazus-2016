package org.devathon.contest2016.instructions;

public enum Instructions {

	MOVE_FORWARD(new MoveForward());
	
	private Instruction instruction;
	
	Instructions(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public Instruction getInstruction() {
		return this.instruction;
	}
}
