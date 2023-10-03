package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Servidor {
    public static void main(String[] args) {

        String diretorioAtual = System.getProperty("user.dir");

        String caminhoDoArquivoServidor = diretorioAtual + "/assets/generated/valores_servidor.txt";
        String caminhoDoArquivoReplica = diretorioAtual + "/assets/generated/valores_replica.txt";


        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Porta do servidor

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket clientSocket = serverSocket.accept(); // Aguarda a conexão do cliente
                System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

                Map<String, Double> acoesOriginais = lerAcoes(caminhoDoArquivoReplica);
                Map<String, Double> acoesAtualizadas = lerAcoes(caminhoDoArquivoServidor);

                // Codigo para atualizar o arquivo
                for (String acao : acoesAtualizadas.keySet()) {
                    double precoOriginal = acoesOriginais.getOrDefault(acao, 0.0);
                    double precoAtualizado = acoesAtualizadas.get(acao);

                    if (Math.abs(precoAtualizado - precoOriginal) > 0.02) {
                        acoesOriginais.put(acao, precoAtualizado);
                        salvarAcoes(caminhoDoArquivoReplica, acoesOriginais);
                        System.out.println("Valores atualizados com sucesso.");
                        break;
                    }
                }

                // Código para enviar o arquivo ao cliente
                File fileToSend = new File(caminhoDoArquivoReplica);
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

    public static Map<String, Double> lerAcoes(String nomeArquivo) throws IOException {
        Map<String, Double> acoes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            String acao = null;
            double preco = 0.0;
            Pattern acaoPattern = Pattern.compile("Ação: (.+)");
            Pattern precoPattern = Pattern.compile("Preço \\(USD\\): (\\d+\\.\\d+)");

            while ((linha = br.readLine()) != null) {
                Matcher acaoMatcher = acaoPattern.matcher(linha);
                Matcher precoMatcher = precoPattern.matcher(linha);

                if (acaoMatcher.find()) {
                    acao = acaoMatcher.group(1);
                } else if (precoMatcher.find()) {
                    preco = Double.parseDouble(precoMatcher.group(1));
                    acoes.put(acao, preco);
                }
            }
        }

        return acoes;
    }

    public static void salvarAcoes(String nomeArquivo, Map<String, Double> acoes) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Map.Entry<String, Double> entry : acoes.entrySet()) {
                bw.write("Ação: " + entry.getKey());
                bw.newLine();
                bw.write("Preço (USD): " + entry.getValue());
                bw.newLine();
                bw.newLine();
            }
        }
    }
}
