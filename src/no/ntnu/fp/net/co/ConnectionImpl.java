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
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
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
		catch (Exception e) {
			state = State.CLOSED;
			throw new IOException("Error contacting Host: " + e);
		}


	}

	//Sends the input package and waits for ack
	public KtnDatagram sendPackage(KtnDatagram sendPackage) throws ConnectException, IOException{
		int attempts = 40;
		KtnDatagram answer = null;
		while(attempts-- > 0 && !isValid(answer)){
			try {
				simplySendPacket(sendPackage);
				lastDataPacketSent = sendPackage;
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
			received = receivePacket(true); //venter p� � motta syn-pakke
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
		//hvis state er LISTEN, m� det v�re en SYN pakke
		if (state == State.LISTEN){
			return (packet.getFlag() == Flag.SYN);
		}	
		//hvis state er SYN_SENT, m� det v�re en SYN_ACK pakke
		if (state == State.SYN_SENT){
			remotePort = packet.getSrc_port();
			return (packet.getFlag() == Flag.SYN_ACK && remoteAddress.equals(packet.getSrc_addr()));
		}
		//for alle andre states m� remotePort og remoteAddress v�re satt. Hvis pakken ikke er fra riktig host; return false
		if (packet.getSrc_addr() != remoteAddress && packet.getSrc_port() != remotePort){
			return false;
		}
	
		//hvis state er SYN_RCVD m� pakken v�re en ACK
		if (state == State.SYN_RCVD){
			return (packet.getFlag() == Flag.ACK);
		}
		//hvis state er FIN_WAIT_1 eller FIN_WAIT_2, m� pakken v�re fin eller ack
		if(state == State.FIN_WAIT_1 || state == State.FIN_WAIT_2){
			return (packet.getFlag() == Flag.FIN || packet.getFlag() == Flag.ACK);
		}
		//hvis state er CLOSE_WAIT, m� set v�re en fin pakke
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
		if(state != State.ESTABLISHED)
			throw new ConnectException("Can't receive without connection");
		
		KtnDatagram received = receivePacket(false);
        if (isValid(received) && received.getSeq_nr() > lastValidPacketReceived.getSeq_nr() && received.getSeq_nr() < (lastValidPacketReceived.getSeq_nr() + 30)) { //slik at samme pakke ikke oppfattes som mottatt to ganger. Bruker kun > (og ikke == +1) siden acker som ikke mottas kan lage uregelmessige hopp i sekvensnr. Satt en liten begrensning p� hvor store hopp som godtas, siden headerfeil kan ogs� lage hopp i sekvensnr OG DET GJENKJENNES IKKE I CHECKSUM
        	sendAck(received, false);
        	lastValidPacketReceived = received;
        	return received.toString();
        }
        else { //Ikke valid pakke
        	sendAck(lastValidPacketReceived, false); // Ack p� forrige gyldige, om ikke det er siste sendt m� ny bli sendt.
        	return null; 
        }
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		try {
			if(state != State.ESTABLISHED) //m� ha conecction � lukke
				throw new ConnectException("No connection to close");

			state = State.FIN_WAIT_1;
			KtnDatagram answer = null;
			KtnDatagram fin = constructInternalPacket(Flag.FIN);
			int timeUsed = 0;
			while(answer == null || answer.getFlag() == Flag.FIN && TIMEOUT > timeUsed++){ //venter p� ack
				if(disconnectRequest != null){
					sendAck(fin, false);
					state = State.FIN_WAIT_2;
				}
				answer = sendPackage(fin); //lagrer svar p� fin pakken
				if(answer.getFlag() == Flag.FIN)
					disconnectRequest = answer; //om fin melding blir mottat, sett som disconnectrequest

			}

			if (state == State.FIN_WAIT_1 && timeUsed < TIMEOUT) { 
				state = State.CLOSE_WAIT;
				timeUsed = 0;
				while (!isValid(disconnectRequest) && timeUsed++ < TIMEOUT) { 
					disconnectRequest = receivePacket(true); //lytter etter fin,
				}
				sendAck(disconnectRequest, false); //acker fin som mottas
			}


			/*
			 * send Fin, om fin mottat, gin_wait_2, send ack
			 * sjekke om disconnectRequest er sendt, i s� fall send ACK
			 * state = close_wait, om fin mottatt, send ack og close, 
			 * 
			 */

		} catch (Exception e) {
			System.out.println("Error in close " + e); //trolig timeout
		}	
		System.out.println("Connection closed");
		state = State.CLOSED;
	}
}
