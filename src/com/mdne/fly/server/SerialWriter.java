package com.mdne.fly.server;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;


public class SerialWriter {
    private SerialPort serialPort;
    private OutputStream outStream;

    public void connect(String portName) throws IOException {
        try {
            // Obtain a CommPortIdentifier object for the port you want to open
            CommPortIdentifier portId =
                    CommPortIdentifier.getPortIdentifier(portName);

            // Get the port's ownership
            serialPort =
                    (SerialPort) portId.open("SerialWriter", 5000);

            // Set the parameters of the connection.
            setSerialPortParameters();

            // Open the input and output streams for the connection. If they won't
            // open, close the port before throwing an exception.
            outStream = serialPort.getOutputStream();
        } catch (NoSuchPortException e) {
            throw new IOException(e.getMessage());
        } catch (PortInUseException e) {
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            serialPort.close();
            throw e;
        }
    }
    
    public SerialPort closePort(){
		return serialPort;    	
    }

    public OutputStream getSerialOutputStream() {
        return outStream;
    }

    /**
     * Sets the serial port parameters
     */
    private void setSerialPortParameters() throws IOException {
        int baudRate = 115200;

        try {
            serialPort.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
}
