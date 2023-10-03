package cliente_udp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente_UDP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o servidor (1 para Servidor 1, 2 para Servidor 2): ");
        int escolha = scanner.nextInt();

        String enderecoDoServidor;
        int portaDoServidor;

        if (escolha == 1) {
            enderecoDoServidor = "192.168.0.107"; // Substitua pelo endereço real do Servidor 1
            portaDoServidor = 12345;
        } else if (escolha == 2) {
            enderecoDoServidor = "192.168.0.107"; // Substitua pelo endereço real do Servidor 2
            portaDoServidor = 54321;
        } else {
            System.out.println("Escolha inválida. Saindo.");
            return;
        }

        try {
            Socket clientSocket = new Socket(enderecoDoServidor, portaDoServidor);

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
