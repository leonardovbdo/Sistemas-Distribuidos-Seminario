package servidor_udp;

import java.io.*;
import java.net.*;

public class Servidor_UDP {
    public static void main(String[] args) {
        int port = 12345;
        String stockPriceFile = "prices.txt"; // Nome do arquivo de preços das ações

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor está esperando por conexões...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Lógica para verificar desvio de R$ 0,02 e atualizar a réplica, se necessário
                // Aqui, assumimos uma classe PriceUpdater que implementa essa lógica.

                // Verifica desvio e atualiza a réplica, se necessário
                boolean updateRequired = PriceUpdater.checkAndUpdate(stockPriceFile);

                // Envia a versão apropriada do arquivo para o cliente
                String response = updateRequired ? "mutualmente consistente" : "desatualizada";
                sendFileToClient(clientSocket, stockPriceFile, response);

                clientSocket.close();
                System.out.println("Cliente desconectado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFileToClient(Socket clientSocket, String fileName, String response) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Envia a resposta ao cliente
            out.println(response);

            // Envia o conteúdo do arquivo
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.println(line);
            }
        }
    }
}

class PriceUpdater {
    public static boolean checkAndUpdate(String fileName) {
        // Lógica para verificar se é necessário atualizar a réplica com base no desvio de R$ 0,02.
        // Retorna true se a atualização for necessária, caso contrário, retorna false.
        // Implemente sua lógica de verificação aqui.

        // Neste exemplo simples, assumimos que a atualização é sempre necessária para fins de demonstração.
        return true;
    }
}
