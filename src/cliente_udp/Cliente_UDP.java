package cliente_udp;

import java.io.*;
import java.net.*;

public class Cliente_UDP {
    public static void main(String[] args) {
        String serverAddress = "127.0.1.1"; // Endereço IP ou nome de host do servidor
        int serverPort = 12345; // Porta do servidor

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Solicitação para baixar a versão mutualmente consistente do arquivo
            out.println("mutualmente consistente");

            // Recebe a resposta do servidor (mutualmente consistente ou desatualizada)
            String response = in.readLine();
            System.out.println("Servidor respondeu com: " + response);

            // Recebe o conteúdo do arquivo, linha por linha
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
