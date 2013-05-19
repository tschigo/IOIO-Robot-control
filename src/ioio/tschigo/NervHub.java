package ioio.tschigo;

public class NervHub {

	private CaptureBall cb = new CaptureBall();

	private final static NervHub instance = new NervHub();

	public static NervHub getInstance() {
		return instance;
	}

	public CaptureBall.State getCb() {
		return cb.getState();
	}

	public void setCb(CaptureBall.State cb) {
		this.cb.setState(cb);
	}

}
