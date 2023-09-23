package cliente_udp;

import java.net.*;
import java.io.*;

public class Cliente_UDP {
    public static void main(String[] args) throws Exception {

        BufferedReader userInput = new BufferedReader(
                new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String input;

        while (true) {
            System.out.println("Digite 'get latest' para obter a versão mais recente ou 'get outdated' para obter a versão desatualizada:");
            input = userInput.readLine();
            sendData = input.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9870);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RESPOSTA DO SERVIDOR:\n" + response);

            if (input.equals("fim")) {
                clientSocket.close();
                break;
            }
        }
    }
}

