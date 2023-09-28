package cliente_udp;

import java.io.*;
import java.net.Socket;

public class Cliente_UDP {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("endereco_do_servidor", 12345); // Substitua pelo endereço real do servidor

            // Código para receber o arquivo do servidor
            InputStream is = clientSocket.getInputStream();
            FileOutputStream fos = new FileOutputStream("arquivo_recebido.txt");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            is.close();

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
