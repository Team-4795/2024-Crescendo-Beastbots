package frc.robot.subsystems.leds;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private final int LED_length = 27;
    private final int PORT = 0;

    private AddressableLED led;
    private AddressableLEDBuffer buffer;

    private boolean test = true;

    public static LED instance;

    public static LED getInstance() {
        if (instance == null) {
            instance = new LED();
        }
        return instance;
    }

    private LED() {
        led = new AddressableLED(PORT);
        buffer = new AddressableLEDBuffer(LED_length);
        led.setLength(buffer.getLength());

        setDefaultCommand(run(() -> {
            if (test) {
                setTestColors();
            } else {
                setBeastBotsColors();
            }
        }).ignoringDisable(true));
    };

    private void setBeastBotsColors() {
        // Beastbotscolors???
    }

    private void setTestColors() {
        setColor(0, 255, 0, false, 0, LED_length);
    }

    private void setColorNoOutput(int a0, int a1, int a2, boolean colorModel, int start, int end) /* false: RGB; true: HSV */ {
        start = MathUtil.clamp(start, 0, LED_length);
        end = MathUtil.clamp(end, start, LED_length);

        for (int i = start; i < end; i++) {
            if (colorModel) buffer.setHSV(i, a0, a1, a2);
            else buffer.setRGB(i, a0, a1, a2);
        }

        led.setData(buffer);
        led.start();
    }

    private void setColor(int a0, int a1, int a2, boolean colorModel, int start, int end) {
        setColorNoOutput(a0, a1, a2, colorModel, start, end);
        setOutput();
    }

    public void setOutput() {
        led.setData(buffer);
        led.start();
    }
}

