/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        
        ServerSocket server = null;
        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            // El main hilo esta acceptando solo nuevas conexiones
            while (true){
                Socket client = server.accept();
                System.out.println("nuevo cliente conectado" + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client);
                
                // el hilo de fondo tomara cada cliente separadamente
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null){
                try {
                    server.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable{

        private final Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.printf("Enviado desde el cliente: %s\n", line);
                    out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    if (out != null){
                        out.close();
                    }
                    if (in != null)
                        in.close();
                    clientSocket.close();
                } catch (IOException e){
                    e.printStackTrace();
                    
                    }
                }
            }
        }
          
    }
