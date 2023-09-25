package servidor_udp;

import java.io.*;
import java.net.*;
import java.lang.Integer;

// Server class
public class Servidor_UDP {
    private static long resultado = 0;
    public static void main(String[] args) {
        ServerSocket server = null;

        try {
            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            int counter = 0;

            // running infinite loop for getting
            // client request
            while (true) {
                counter++;

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("Novo cliente conectado no endereço: "
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client, counter);

                // This thread will handle the client
                // separately
                Thread t = new Thread(clientSock);

                t.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private int counter;

        // Constructor
        public ClientHandler(Socket socket, int counter) {
            this.clientSocket = socket;
            this.counter = counter;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                out.println(25*counter);

                String line = in.readLine();

                int r = Integer.parseInt(line);
                resultado += r;
                System.out.println(resultado);

            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}