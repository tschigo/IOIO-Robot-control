package ioio.tschigo;

public class CaptureBall {
	private State state;

	public CaptureBall() {
		this.state = State.Scan;
	}

	public enum State {
		Scan {
		// scanning
		// found -> advance
		// not found -> rotate
		},
		Advance {
		// go to the goal
		},
		Rotate {
		// rotate 20
		},
		Capture {
		// hand down
		},
		Verify {
		// got it?
		},
		End {
		// I'm home, fine
		},
		Free {
		// hand up
		},
		Back {

		},
		Test
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
