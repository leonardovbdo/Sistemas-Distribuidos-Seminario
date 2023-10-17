package cliente;

import servidor.Servidor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o servidor (1 para Servidor Atualizado, 2 para Servidor Replica): ");
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
            System.out.println("Arquivo baixado!!");
            for (Map.Entry<String, Double> entry : Servidor.lerAcoes("arquivo_recebido.txt").entrySet()) {
                String acao = entry.getKey();
                double preco = entry.getValue();
                System.out.println("Ação: " + acao + ", Preço (USD): " + preco);
            }
            System.out.println();

        } catch (IOException e) {
            System.err.println("Erro ao comunicar com o servidor: " + e.getMessage());
        }
        
    }
}
