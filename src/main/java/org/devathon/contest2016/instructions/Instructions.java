package org.devathon.contest2016.instructions;

public enum Instructions {

	MOVE_FORWARD(new MoveForward()),
	BREAK_BLOCK(new BreakBlock()),
	HIT_ENTITY(new HitEntity()),
	LOOK_AT_LEFT(new LookAtLeft()),
	LOOK_AT_RIGHT(new LookAtRight());
	
	private Instruction instruction;
	
	Instructions(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public Instruction getInstruction() {
		return this.instruction;
	}
}
