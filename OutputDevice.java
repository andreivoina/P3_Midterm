import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class OutputDevice {

    private OutputStream outputDevice;


    public OutputDevice(OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("OutputStream cannot be null");
        }
        this.outputDevice = outputStream;
    }

    // Method to write a message using the OutputStream
    public void writeMessage(String message) throws IOException {
        this.outputDevice.write(message.getBytes());
        this.outputDevice.write('\n');
    }
}