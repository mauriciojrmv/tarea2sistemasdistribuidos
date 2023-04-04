/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    
    public static void main(String[] args){

    String host = "127.0.0.1";
    int port = 32000;
    try (Socket socket = new Socket(host, port)){
        PrintWriter out = new PrintWriter (socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        String line = null;
        while(!"exit".equalsIgnoreCase(line)){
            line = scanner.nextLine();
            out.println(line);
            out.flush();
            System.out.println("Server contesto " + in.readLine());
        }
        scanner.close();
       } catch(IOException e){
            e.printStackTrace();
}
}
}
