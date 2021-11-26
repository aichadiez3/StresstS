package serverApp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerClient implements Runnable{

	Socket socket;
	
	
	public ServerClient(Socket socket) {
		super();
		this.socket = socket;
	}



	@Override
	public void run() {
		InputStream inputStream = null;
		
		try {
			inputStream = socket.getInputStream();
			//inputStream.read();
			
		} catch (IOException e) {
			Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            releaseResourcesClient(inputStream, socket);
        }
	}

	
	private static void releaseResourcesClient(InputStream inputStream, Socket socket) {
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
