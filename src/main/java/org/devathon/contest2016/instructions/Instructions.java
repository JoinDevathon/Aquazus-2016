package org.devathon.contest2016.instructions;

public enum Instructions {

	MOVE_FORWARD(new MoveForward()),
	BREAK_BLOCK(new BreakBlock());
	
	private Instruction instruction;
	
	Instructions(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public Instruction getInstruction() {
		return this.instruction;
	}
}
