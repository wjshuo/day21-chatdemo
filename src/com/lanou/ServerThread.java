package com.lanou;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dllo on 17/11/14.
 */
public class ServerThread extends Thread{
    private int port;
    private ChatWindow window;//定义聊天窗口
    //输入/输出字符流
    private  BufferedReader reader=null;
    private BufferedWriter writer=null;
    private String readStr;//读取结果字符串

    public ServerThread(int port,ChatWindow window) {
        this.port = port;
        this.window=window;
    }

    @Override
    public void run() {

        try {
            super.run();
            //显示等待连接...
            //聊天窗口系那是内容
            window.showMsg("等待连接...");
            //创建Socket
            ServerSocket ss =new ServerSocket(port);
            Socket socket=ss.accept();
            window.showMsg(socket.getInetAddress()+"连接成功");
            //输入输出流 字符流
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true){
                readStr=reader.readLine();
                //显示聊天信息
                window.showMsg(readStr);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //对外使用的输出方法
    public void output(String content){
        try {
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
