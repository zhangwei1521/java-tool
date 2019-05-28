package com.zhangwei.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetDemo1 {
    public static void main(String[] args) {
        testInetAddress();
    }

    private static void testInetAddress(){
        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println(localAddress);
            InetAddress address1 = InetAddress.getByName("www.baidu.com");
            System.out.println(address1);
            InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
            int i = 0;
            for(InetAddress address : addresses){
                if(address.equals(address1)){
                    continue;
                }
                System.out.println("address"+ i++ + ": " +address);
            }

            byte[] address1s = address1.getAddress();
            StringBuilder adBuilder = new StringBuilder();
            for(byte ab : address1s){
                int a = ab;
                if(a<0){
                    a = 255+a;
                }
                adBuilder.append(a).append(".");
            }
            System.out.println(adBuilder.substring(0,adBuilder.length()-1));
            System.out.println(address1.getHostName());
            System.out.println(address1.getHostAddress());
            System.out.println(address1.isMulticastAddress());
            System.out.println(address1.getCanonicalHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
