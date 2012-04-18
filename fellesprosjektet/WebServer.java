package WebServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer {
	
	public static void main(String argc[]) throws Exception {
		// Set port number
		int port = 6789;
		
		// Establish the listen socket
		ServerSocket welcomeSocket = new ServerSocket(6789);
		
		// Process HTTP service requests in an infinite loop
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			HttpRequest request = new HttpRequest(connectionSocket);
			Thread thread = new Thread(request);
			thread.start();
		}
		
	}
}

final class HttpRequest implements Runnable {
	
	final static String CRLF = "\r\n";
	Socket socket;
	
	public HttpRequest(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private void processRequest() throws Exception {
		InputStream is = this.socket.getInputStream();
		DataOutputStream os = new DataOutputStream(this.socket.getOutputStream());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		
		String requestLine = br.readLine();
		System.out.println();
		System.out.println(requestLine);
		
		String headerLine = null;
		while((headerLine = br.readLine()).length() != 0) {
			System.out.println(headerLine);
		}
		
		StringTokenizer tokens = new StringTokenizer(requestLine);
		tokens.nextToken();
		String fileName = tokens.nextToken();
		fileName = "." + fileName;
		
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		}
		
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		if(fileExists) {
			statusLine = "HTTP/1.1 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
		} else {
			statusLine = "HTTP/1.1 404 Not Found" + CRLF;
			contentTypeLine = "Content-type: " + CRLF;
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
					"<BODY>Not Found</BODY></HTML>";
		}
		
		os.writeBytes(statusLine);
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF);
		
		if(fileExists) {
			sendBytes(fis, os);
			fis.close();
		} else {
			os.writeBytes(entityBody);
		}
		
		os.close();
		br.close();
		socket.close();
	}
	
	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
		byte[] buffer = new byte[1024];
		int bytes = 0;
		
		while((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}
	
	private static String contentType(String fileName) {
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		return "application/octet-stream";
	}
}
