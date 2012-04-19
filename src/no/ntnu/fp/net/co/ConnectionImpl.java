/*
 * Created on Oct 27, 2004
 */
package src.no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebjï¿½rn Birkeland and Stein Jakob Nordbï¿½
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		super();
		this.myPort = myPort; //setter port og myaddress, eneste som ikke var satt i abstractconnection constructoren
		myAddress = getIPv4Address();
		usedPorts.put(myPort, true);
	}    

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

    /**
     * Establish a connection to a remote location.
     * 
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote portnumber to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
	public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
	SocketTimeoutException {

		if (state != State.CLOSED) {
			throw new ConnectException("Socket is not closed");
		}
		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;
		try{
			state = State.SYN_SENT;
			KtnDatagram retur = sendPackage(constructInternalPacket(Flag.SYN));
			sendAck(retur, false);
			state = State.ESTABLISHED;
		}
		catch (Exception E) {
			state = State.CLOSED;
			throw new IOException("Error contacting Host: " + E);
		}


	}
	
	//Sends the input package and waits for ack
	public KtnDatagram sendPackage(KtnDatagram sendPackage) throws ConnectException, IOException{
		int attempts = 40;
		KtnDatagram answer = null;
		while(attempts-- > 0 && !isValid(answer)){
			try {
				simplySendPacket(sendPackage);
				answer = receiveAck();

			} catch (Exception e) {
				// intet svar
			}

		}
		if(!isValid(answer)){
			state = State.CLOSED;
			throw new IOException("Could not send packet");
		}
		return answer;
	}

    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
    	if (state != State.CLOSED && state != State.LISTEN) {
    		throw new ConnectException("Socket is not closed");
    	}
    	
    	state = State.LISTEN;
        KtnDatagram received = null;
        while (!isValid(received)) {
        	received = receivePacket(true); //venter pï¿½ ï¿½ motta syn-pakke
        }
        
        ConnectionImpl newConnection = new ConnectionImpl(createPort()); //ny connection lages
        newConnection.prepareConnection(received); //avslutter three-way handshake
        return newConnection;
    }
    
    private void prepareConnection(KtnDatagram packet) throws IOException {
    	lastValidPacketReceived = packet;
    	state = State.SYN_RCVD;
    	remoteAddress = packet.getSrc_addr();
    	remotePort = packet.getSrc_port(); //synkroniserer seg mot tilkobleren
    	System.out.println("New socket at port " + myPort);
    	sendAck(packet, true);
    	KtnDatagram retur = receiveAck();
    	if (!isValid(retur)) {
    		state = State.CLOSED;
    		throw new IOException("Error during connection");
    	}
    	state = State.ESTABLISHED;
    }
    
    private int createPort() {
    	int port = (int)Math.random()*100+10000;
    	while(usedPorts.containsValue(port)) {
    		port = (int)Math.random()*100+10000;
    	}
    	return port;
    }

    /**
     * Send a message from the application.
     * 
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
    	if(state != State.ESTABLISHED)
			throw new ConnectException("Error sending, not connected");

		sendPackage(constructDataPacket(msg));

    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    protected boolean isValid(KtnDatagram packet) {
    	//hvis pakken er null; return false
    	if (packet == null){
    	return false;
    	}
    	//hvis pakken har feil checksum; return false
    	if(packet.getChecksum() != packet.calculateChecksum()){
    	return false;
    	}
    	//hvis det er en Fin pakke, og payload ikke er null; return false
    	if (packet.getFlag() == Flag.FIN && packet.getPayload() != null){
    	return false;
    	}
    	//hvis det er en ack pakke, og den ikke acker siste pakke som er sendt; return false
    	if ((packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.SYN_ACK) && packet.getAck() != lastDataPacketSent.getSeq_nr()) {
    	return false;
    	}
    	//hvis state er LISTEN, mŒ det v¾re en SYN pakke
    	if (state == State.LISTEN){
    	return (packet.getFlag() == Flag.SYN);
    	}
    	//for alle andre states mŒ remotePort og remoteAddress v¾re satt. Hvis pakken ikke er fra riktig host; return false
    	if (packet.getSrc_addr() != remoteAddress || packet.getSrc_port() != remotePort){
    	return false;
    	}
    	//hvis state er SYN_SENT, mŒ det v¾re en SYN_ACK pakke
    	if (state == State.SYN_SENT){
    	return (packet.getFlag() == Flag.SYN_ACK);
    	}
    	//hvis state er SYN_RCVD mŒ pakken v¾re en ACK
    	if (state == State.SYN_RCVD){
    	return (packet.getFlag() == Flag.ACK);
    	}
    	//hvis state er FIN_WAIT_1 eller FIN_WAIT_2, mŒ pakken v¾re fin eller ack
    	if(state == State.FIN_WAIT_1 || state == State.FIN_WAIT_2){
    	return (packet.getFlag() == Flag.FIN || packet.getFlag() == Flag.ACK);
    	}
    	//hvis state er CLOSE_WAIT, mŒ set v¾re en fin pakke
    	if(state == State.CLOSE_WAIT){
    	return (packet.getFlag() == Flag.FIN);
    	}


    	return true;
    	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	public String receive() throws ConnectException, IOException {
		throw new NotImplementedException();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		throw new NotImplementedException();
	}
}
