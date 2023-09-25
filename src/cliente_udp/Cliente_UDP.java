package cliente_udp;

import java.io.*;
import java.net.*;

public class Cliente_UDP {

    // driver code
    public static void main(String[] args) {
        long fibonacci = 0;
        // establish a connection by providing host and port
        // number
        try {

            Socket socket = new Socket("127.0.1.1", 1234);

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            String line = in.readLine();

            int number = Integer.parseInt(line);

            for (int i = number - 25; i < number; i++) {
                fibonacci = Cliente_UDP.fibo(i);
                System.out.print("(" + i + "):" + Cliente_UDP.fibo(i) + "\n");
            }
            System.out.println("fim");
            System.out.println(fibonacci);

            out.println(fibonacci);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static long fibo(int n) {
        if (n < 2) {
            return n;
        } else {
            return fibo(n - 1) + fibo(n - 2);
        }
    }
}