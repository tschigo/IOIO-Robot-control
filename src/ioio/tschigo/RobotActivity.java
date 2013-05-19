package ioio.tschigo;

import java.text.DecimalFormat;

import ioio.tschigo.R;
import ioio.tschigo.CaptureBall.State;

import ioio.lib.api.PwmOutput;
import ioio.lib.api.TwiMaster;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RobotActivity extends IOIOActivity {

	private NervHub data;

	private Button button_0;
	private Button button_1;
	private Button button_2;
	private Button button_3;
	private Button button_4;
	private Button button_5;
	private Button button_6;
	private Button button_7;
	private TextView tv_1;
	private TextView tv_2;
	private TextView tv_3;
	private TextView tv_4;
	private TextView tv_5;
	private TextView tv_6;

	// looper sensor info
	String analog[] = new String[9];
	short xPos;
	short yPos;
	short anglePos;

	public RobotActivity() {
		data = NervHub.getInstance();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Play song :D
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.haisoundtrack);
		mediaPlayer.start();

		tv_1 = (TextView) findViewById(R.id.textView1);
		tv_1.setText(data.getCb() + "");
		tv_2 = (TextView) findViewById(R.id.textView2);
		tv_3 = (TextView) findViewById(R.id.textView3);
		tv_4 = (TextView) findViewById(R.id.textView4);
		tv_5 = (TextView) findViewById(R.id.textView5);
		tv_6 = (TextView) findViewById(R.id.textView6);

		button_0 = (Button) findViewById(R.id.button1);
		button_0.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Advance);
				tv_1.setText(data.getCb() + "");
				update();

			}
		});
		button_1 = (Button) findViewById(R.id.button2);
		button_1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Rotate);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});
		button_2 = (Button) findViewById(R.id.button3);
		button_2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Capture);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});
		button_3 = (Button) findViewById(R.id.button4);
		button_3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Free);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});
		button_4 = (Button) findViewById(R.id.button5);
		button_4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.End);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});

		button_5 = (Button) findViewById(R.id.button6);
		button_5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Back);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});
		button_6 = (Button) findViewById(R.id.button7);
		button_6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Test);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});
		button_7 = (Button) findViewById(R.id.button8);
		button_7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.setCb(State.Test);
				tv_1.setText(data.getCb() + "");
				update();
			}
		});
	}

	public void update() {
		tv_2.setText("[left wheel] " + analog[8] + " - [right wheel] "
				+ analog[7]);
		tv_3.setText("xPos " + xPos + " - yPos " + yPos + " - anglePos "
				+ anglePos);
		tv_4.setText("[1] " + analog[1] + " - [2] " + analog[2]);
		tv_5.setText("[5] " + analog[5] + " - [6] " + analog[6]);
		tv_6.setText("[0] " + analog[0] + " - [3] " + analog[3] + " - [4] "
				+ analog[4]);

	}

	class Looper extends BaseIOIOLooper {
		private TwiMaster twi;
		private NervHub data;
		private PwmOutput servo_;

		@Override
		protected void setup() throws ConnectionLostException {
			twi = ioio_.openTwiMaster(1, TwiMaster.Rate.RATE_100KHz, false);
			servo_ = ioio_.openPwmOutput(10, 50);
			data = NervHub.getInstance();
		}

		protected void robotForward(int fwd) {
			byte[] request = new byte[2];
			byte[] response = new byte[1];

			request[0] = 0x1C;
			request[1] = (byte) fwd;
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, 0);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		protected void robotForward(double fwd) {
			robotForward((int) fwd);
		}

		protected void robotRotate(int grad) {
			byte[] request = new byte[2];
			byte[] response = new byte[1];
			request[0] = 0x1D; // cmd
			request[1] = (byte) grad;
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, 0);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		protected void robotRotate(double grad) {
			robotRotate((int) grad);
		}

		protected void robotMove(int leftMotorSpeed, int rightMotorSpeed) {
			byte[] request = new byte[3];
			byte[] response = new byte[1];
			request[0] = 0x1A;
			request[1] = (byte) rightMotorSpeed;
			request[2] = (byte) leftMotorSpeed;
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, 0);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		protected void robotMove(int speed) {
			robotMove(speed, speed);
		}

		protected void robotLED(int blueLED, int redLED) {
			byte[] request = new byte[3];
			byte[] response = new byte[1];
			request[0] = 0x20;
			request[1] = (byte) redLED;// set between 0 and 100
			request[2] = (byte) blueLED;// set between 0 and 100
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, 0);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		protected void robotLED(int intensity) {
			robotLED(intensity, intensity);
		}

		protected void gripper(boolean b) {
			try {
				servo_.setDutyCycle(0.0528f);
			} catch (ConnectionLostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		protected void robotReadSensor() {
			byte[] request = new byte[] { 0x10 };
			byte[] response = new byte[8];
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, response.length);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int l = 0; l < 7; l++) {
				int i = 0xFF & response[l + 1];
				if (l != 0)
					analog[l] = i + "cm";
				else
					analog[l] = new DecimalFormat("#.#").format(i / 10.0) + "V";
			}

			request[0] = 0x1A; // get velocity
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, 2);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			analog[7] = response[0] + "";
			analog[8] = response[1] + "";

			/* get position */
			request[0] = 0x1B; // get position
			synchronized (twi) {
				try {
					twi.writeRead(0x69, false, request, request.length,
							response, 6);
				} catch (ConnectionLostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			xPos = (short) (((response[1] & 0xFF) << 8) | (response[0] & 0xFF));
			yPos = (short) (((response[3] & 0xFF) << 8) | (response[2] & 0xFF));
			anglePos = (short) (((response[5] & 0xFF) << 8) | (response[4] & 0xFF));

		}

		@Override
		public void loop() throws ConnectionLostException {
			switch (data.getCb()) {
			case Scan:
			case Verify:
				break;
			case Advance:
				robotLED(100, 0);
				robotMove(14, 14);
				break;
			case Rotate:
				robotLED(50, 50);
				robotMove(14, 7);
				break;
			case Capture:
				gripper(true);
				break;
			case Free:
				gripper(false);
				break;
			case End:
				robotMove(0);
				robotLED(90);
				break;
			case Back:
				robotLED(0, 100);
				robotMove(-14);
				break;
			default:
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			robotReadSensor();
		}
	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}

}