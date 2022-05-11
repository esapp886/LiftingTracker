package sample;

import java.io.*;
import java.net.Socket;

public class Client {
    Client(){

    }
    public static void main(String[] args) throws Exception {

        Socket soc = new Socket("127.0.0.1", 5215);
        var t = new TransferfileClient(soc);
        t.displayMenu();

    }

    // Transfers file for client
    static class TransferfileClient {
        Socket ClientSoc;
        DataInputStream din;
        DataOutputStream dout;
        BufferedReader br;

        TransferfileClient(Socket var1) {
            try {
                this.ClientSoc = var1;
                this.din = new DataInputStream(this.ClientSoc.getInputStream());
                this.dout = new DataOutputStream(this.ClientSoc.getOutputStream());
                this.br = new BufferedReader(new InputStreamReader(System.in)); //used to provide efficient reading of characters .. etc
            } catch (Exception var3) {
            }

        }
        // Send File
        void SendFile() throws Exception {
            System.out.print("Enter File Name :");
            String var1 = this.br.readLine(); //read data becomes var1
            File var2 = new File(var1); //var2 becomes the var which should be file name
            if (!var2.exists()) { //checks it file exists
                System.out.println("File not Exists...");
                this.dout.writeUTF("File not found");
            } else {
                this.dout.writeUTF(var1);
                String var3 = this.din.readUTF(); //Data enables to be read
                if (var3.compareTo("File Already Exists") == 0) {
                    System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                    String var4 = this.br.readLine(); //reads data
                    if (var4 != "Y") {
                        this.dout.writeUTF("N"); //check no option
                        return;
                    }

                    this.dout.writeUTF("Y"); // checks yes option
                }

                System.out.println("Sending File ...");
                FileInputStream var6 = new FileInputStream(var2);

                int var5;
                do {
                    var5 = var6.read();
                    this.dout.writeUTF(String.valueOf(var5));
                } while(var5 != -1);

                var6.close();
                System.out.println(this.din.readUTF());
            }
        }
        // receive file
        void ReceiveFile() throws Exception {
            System.out.print("Enter File Name :");
            String var1 = this.br.readLine(); //read data
            this.dout.writeUTF(var1);
            String var2 = this.din.readUTF();
            if (var2.compareTo("File Not Found") == 0) {
                System.out.println("File not found on Server ...");
            } else {
                if (var2.compareTo("READY") == 0) {
                    System.out.println("Receiving File ...");
                    File var3 = new File(var1);
                    if (var3.exists()) {
                        System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                        String var4 = this.br.readLine();//read data
                        if (var4 == "N") {
                            this.dout.flush();
                            return;
                        }
                    }

                    FileOutputStream var7 = new FileOutputStream(var3);

                    int var5;
                    do {
                        String var6 = this.din.readUTF();
                        var5 = Integer.parseInt(var6);
                        if (var5 != -1) {
                            var7.write(var5);
                        }
                    } while(var5 != -1);

                    var7.close();
                    System.out.println(this.din.readUTF());
                }

            }
        }
        // Display menu
        public void displayMenu() throws Exception {
            while(true) { // set to true for options to show
                System.out.println("[ MENU ]");
                System.out.println("1. Send File");
                System.out.println("2. Receive File");
                System.out.println("3. Exit");
                System.out.print("\nEnter Choice :");
                int var1 = Integer.parseInt(this.br.readLine());
                if (var1 == 1) {
                    this.dout.writeUTF("SEND"); //send file
                    this.SendFile();
                } else if (var1 == 2) {
                    this.dout.writeUTF("GET"); //receive file
                    this.ReceiveFile();
                } else {
                    this.dout.writeUTF("DISCONNECT");
                    System.exit(1); //exit of program
                }
            }
        }
    }
}

