package servidor_udp;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Servidor_UDP {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9870);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            System.out.println("SERVIDOR EM AÇÃO!!!");

            // Estrutura para armazenar a relação de preços
            Map<String, Double> priceList = new HashMap<>();

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("RECEIVE: " + request);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String response;

                // Implemente a lógica para verificar o desvio numérico e atualizar a relação de preços aqui.
                // Para simplificar, usarei uma relação de preços fixa.
                priceList.put("AAPL", 150.0);
                priceList.put("GOOGL", 2800.0);

                if (request.equals("get latest")) {
                    response = getPriceListAsString(priceList);
                } else if (request.equals("get outdated")) {
                    // Simula uma versão desatualizada da relação de preços
                    Map<String, Double> outdatedPriceList = new HashMap<>(priceList);
                    outdatedPriceList.put("AAPL", 145.0);
                    response = getPriceListAsString(outdatedPriceList);
                } else {
                    response = "Comando desconhecido.";
                }

                sendData = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                receiveData = new byte[1024];

                if (request.equals("fim")) {
                    serverSocket.close();
                    break;
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    private static String getPriceListAsString(Map<String, Double> priceList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Preços do Mercado de Ações:\n");
        for (Map.Entry<String, Double> entry : priceList.entrySet()) {
            sb.append(entry.getKey()).append(": R$ ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
