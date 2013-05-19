package ioio.tschigo;

public class CaptureBall {
	private State state;

	public CaptureBall() {
		this.state = State.Test;
	}

	public enum State {
		Forward, RotateLeft, RoateRight, Back, HandUp, HandDown, Stop, Test
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return state + "";
	}

}
