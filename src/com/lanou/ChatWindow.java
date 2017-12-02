package com.lanou;

import sun.security.x509.IPAddressName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dllo on 17/11/14.
 */
public class ChatWindow extends Frame {
    private Label ipLabel;
    private TextField ipField;//ip地址输入框
    private Label portLabel;
    private TextField portFiled;//端口号输入框
    private Button clientBtn;//客户端按钮
    private Button serverBtn;//服务端按钮
    private TextArea showArea;//显示文本区域
    private Label inputLabel;
    private TextField inputField;//输入内容输入框
    private Button sendBtn;//发送消息按钮
    private String ip;
    private int port;
    private ClientThread client;
    private ServerThread server;
    //是否为服务器,默认为false
    private boolean isServer =false;


    /*
        Java  AWT:Java的视图级API
         */
    public ChatWindow() {
        //生成窗口
        creatWindow();
        //客户端和服务端的按钮事件
        pressBtn();
    }

    //客户端和服务端点击 Button响应的代码
    private void pressBtn() {
        //客户端按钮
        clientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //通过ip地址输入框获取ip地址
                ip = ipField.getText();
                //获取端口号
                String postStr = portFiled.getText();
                port = Integer.valueOf(postStr);

                //启动客户端
                client = new ClientThread(ip, port,ChatWindow.this);
                client.start();
                clientBtn.setEnabled(false);

            }
        });

        //服务端按钮
        serverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击服务器按钮,改变窗口的状态为true.
                isServer =true;
                //获取端口号
                String postStr = portFiled.getText();
                port = Integer.valueOf(postStr);
                //启动服务端
                server = new ServerThread(port,ChatWindow.this);
                server.start();
                serverBtn.setEnabled(false);
            }
        });

        //发送按钮
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发送消息
                sendMsg();
            }
        });
        //点击回车发送
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    sendMsg();
                }
            }
        });


    }
    //发送聊天信息
    private void sendMsg(){
        //从输入框获取 输入的内容
        String content =inputField.getText();
        if (content.length()==0){
            //无内容输入
            //弹出提示
            JOptionPane.showConfirmDialog(null,"达摩祖师");
        }else{
            //自身显示
            showMsg("-->"+content);
            //有内容输入
            //判断是谁输出的(服务.客户端使用不同的输出流)
            if (isServer){
                server.output(content);
            }else{
                client.output(content);
            }
            //清空输入框
            inputField.setText("");

        }
    }


    private void creatWindow() {

        //设置画板
        setLayout(new FlowLayout());
        //添加内容在这中间,添加窗口内容


        ipLabel = new Label("ip地址:");
        Font font = new Font("", Font.BOLD, 16);
        ipLabel.setFont(font);
        ipLabel.setForeground(Color.CYAN);
        ipLabel.setBackground(Color.orange);
        ipField = new TextField("172.16.16.208", 14);

        portLabel = new Label("端口号:");
        portFiled = new TextField("8080", 4);
        clientBtn = new Button("客户端");
        serverBtn = new Button("服务端");
        showArea = new TextArea("", 15, 56, TextArea.SCROLLBARS_BOTH);

        inputLabel = new Label("输入内容");
        inputField = new TextField("", 24);
        sendBtn = new Button("发送");

        add(ipLabel);
        add(ipField);
        add(portLabel);
        add(portFiled);
        add(clientBtn);
        add(serverBtn);
        add(showArea);
        add(inputLabel);
        add(inputField);
        add(sendBtn);


        //设置大小,位置并显示
        setSize(500, 400);
        setLocation(0, 0);//显示在桌面的位置
        setResizable(false);
        setVisible(true);//设置显示模式为true
        //添加关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    //自定义方法:对外开放,用来设置显示内容
    public void showMsg(String msg){
        showArea.append(msg+"\n");
    }


}
