import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public boolean OK = true;
	public Socket clientSock;
	DataInputStream dis;
	DataOutputStream dos;
	Scanner sc = new Scanner(System.in);

	public Client(String ipServer, int port) {
		try {
			clientSock = new Socket(ipServer, port);
			dis = new DataInputStream(clientSock.getInputStream());
			dos = new DataOutputStream(clientSock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//Cette méthode a surtout servi dans un premier temps pour le debug (échange via la console)
	//Elle n'est maintenant plus appelée
	public void listen() {
		try {
			String str = dis.readUTF();
			System.out.println("Serv: " + str);
			if (str.equals("exit")) {
				OK = false;
				clientSock.close();
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String listen1() {
		String str = "none";
		try {
			str = dis.readUTF();
			System.out.println("Serv: " + str);
			if (str.equals("exit")) {
				OK = false;
				clientSock.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		return str;

	}

	
	//Cette méthode a surtout servi dans un premier temps pour le debug (échange via la console)
	//Elle n'est maintenant plus appelée
	public void write() {
		String str = sc.nextLine();
		try {
			dos.writeUTF(str);
			dos.flush();
			if (str.equals("exit")) {
				OK = false;
				clientSock.close();
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(String str) {
		try {
			dos.writeUTF(str);
			dos.flush();
			if (str.equals("exit")) {
				OK = false;
				clientSock.close();
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
