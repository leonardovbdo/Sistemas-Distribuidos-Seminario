package servidor_replica;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorReplica {
    public static void main(String[] args) {

        String diretorioAtual = System.getProperty("user.dir");

        String caminhoDoArquivo = diretorioAtual + "/assets/generated/valores_replica.txt";

        try {
            ServerSocket serverSocket = new ServerSocket(54321); // Porta do novo servidor

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket clientSocket = serverSocket.accept(); // Aguarda a conexão do cliente
                System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

                // Código para enviar o arquivo ao cliente
                File fileToSend = new File(caminhoDoArquivo);
                FileInputStream fis = new FileInputStream(fileToSend);
                OutputStream os = clientSocket.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                fis.close();

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
