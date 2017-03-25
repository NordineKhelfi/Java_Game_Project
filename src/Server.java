import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public Socket client;
	public ServerSocket serverSock;
	public int port;
	DataInputStream dis;
	DataOutputStream dos;
	public boolean OK = true;
	Scanner sc = new Scanner(System.in);

	public Server(int port) {
		this.port = port;
	}

	//Cette méthode a surtout servi dans un premier temps pour le debug (échange via la console)
	//Elle n'est maintenant plus appelée
	public void listen() {
		try {
			while (OK) {
				String str;
				str = (String) dis.readUTF();
				System.out.println("message= " + str);
				if (str.equals("message= exit"))
					OK = false;
			}
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

	}

	
	public String listen1() {
		String str = "none";
		try {
			str = (String) dis.readUTF();
			if (str.equals("exit")) {
				OK = false;
				client.close();
				System.exit(0);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return str;

	}

	public void write(String str) {
		try {
			dos.writeUTF(str);
			dos.flush();
			System.out.println(str);

			if (str.equals("exit")) {
				client.close();
				OK = false;
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	//Cette méthode a surtout servi dans un premier temps pour le debug (échange via la console)
	//Elle n'est maintenant plus appelée
	public void write() {
		String str = sc.nextLine();
		try {
			dos.writeUTF(str);
			dos.flush();

			if (str.equals("exit")) {
				client.close();
				OK = false;
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
