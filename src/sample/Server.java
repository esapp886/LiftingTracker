package sample;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String args[]) throws Exception {
        ServerSocket soc = new ServerSocket(5215);
        System.out.println("Server Started on Port Number 5215");
        while (true) {
            System.out.println("Waiting for Connection ...");
            TransferfileServer t = new TransferfileServer(soc.accept());

        }
    }

    static class TransferfileServer extends Thread {
        Socket ClientSoc;
        DataInputStream din;
        DataOutputStream dout;

        TransferfileServer(Socket var1) {
            try {
                this.ClientSoc = var1;
                this.din = new DataInputStream(this.ClientSoc.getInputStream());
                this.dout = new DataOutputStream(this.ClientSoc.getOutputStream());
                System.out.println("Client Connected ...");
                this.start();
            } catch (Exception var3) {
            }

        }

        void SendFile() throws Exception {
            String var1 = this.din.readUTF(); //reads data in
            File var2 = new File(var1);
            if (!var2.exists()) {
                this.dout.writeUTF("File Not Found");// checks if file matches
            } else {
                this.dout.writeUTF("READY");
                FileInputStream var3 = new FileInputStream(var2);

                int var4;
                do {
                    var4 = var3.read();
                    this.dout.writeUTF(String.valueOf(var4)); // write file to new variable
                } while(var4 != -1);

                var3.close();
                this.dout.writeUTF("File Receive Successfully");
            }
        }

        void ReceiveFile() throws Exception {
            String var1 = this.din.readUTF();
            if (var1.compareTo("File not found") != 0) {
                File var2 = new File(var1);
                String var3;
                if (var2.exists()) {
                    this.dout.writeUTF("File Already Exists");
                    var3 = this.din.readUTF();
                } else {
                    this.dout.writeUTF("SendFile");
                    var3 = "Y";
                }

                if (var3.compareTo("Y") == 0) {
                    FileOutputStream var4 = new FileOutputStream(var2);

                    int var5;
                    do {
                        String var6 = this.din.readUTF();
                        var5 = Integer.parseInt(var6);
                        if (var5 != -1) {
                            var4.write(var5);
                        }
                    } while(var5 != -1);

                    var4.close();
                    this.dout.writeUTF("File Send Successfully");
                }
            }
        }

        public void run() {
            while(true) {
                while(true) {
                    while(true) {
                        try {
                            System.out.println("Waiting for Command ...");
                            String var1 = this.din.readUTF();
                            if (var1.compareTo("GET") == 0) {
                                System.out.println("\tGET Command Received ...");
                                this.SendFile();
                            } else if (var1.compareTo("SEND") == 0) {
                                System.out.println("\tSEND Command Received ...");
                                this.ReceiveFile();
                            } else if (var1.compareTo("DISCONNECT") == 0) {
                                System.out.println("\tDisconnect Command Received ...");
                                System.exit(1);
                            }
                        } catch (Exception var2) {
                        }
                    }
                }
            }
        }
    }
}


