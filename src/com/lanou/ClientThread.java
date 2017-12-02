package com.lanou;

import java.io.*;
import java.net.Socket;

/**
 * Created by dllo on 17/11/14.
 * 客户端线程
 */
public class ClientThread extends Thread {

    private String ip;
    private int port;
    private ChatWindow window;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private String readStr;

    public ClientThread(String ip, int port, ChatWindow window) {
        this.ip = ip;
        this.port = port;
        this.window = window;
    }

    @Override
    public void run() {

        try {
            super.run();
            window.showMsg("开始连接服务器");
            Socket socket = new Socket(ip, port);
            window.showMsg("连接成功...");
            //输入输出字符流
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                readStr = reader.readLine();
                window.showMsg(readStr);
            }


        } catch (IOException e) {
            e.printStackTrace();
            window.showMsg("连接失败" + e.getMessage());
        } finally {
            window.showMsg("今朝有酒今朝醉");
        }


    }

    public void output(String content) {
        try {
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
