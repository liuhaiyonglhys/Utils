package com.lhy.utils;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * 文件操作类
 * Created by lhy on 2016/4/14.
 */
public class FileUtils {
    /**
     * 写入文件
     * @param path
     * @param key
     * @param datas
     */
    public static void writeObject(String path,String key,Object datas){
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = path + "/" + key;
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outStream);
            objectOutputStream.writeObject(datas);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     * @param path
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObject(String path,String key,Class<T> clazz){
        T datas = null;
        try{
            FileInputStream freader;
            String filePath = path + "/" + key;
            File file = new File(filePath);
            if(file.exists()){
                datas = clazz.newInstance();
                freader = new FileInputStream(filePath);
                ObjectInputStream objectInputStream = new ObjectInputStream(freader);
                datas = (T) objectInputStream.readObject();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return datas;
    }

    /**
     * 文件缓存写入
     * @param datas
     * @param context
     * @param uri
     */
    public static void writeObject(Object datas, Context context, String uri) {
        File dir = new File(PackageUtils.getApplicationDataDirectory(context) + "/cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(PackageUtils.getApplicationDataDirectory(context) + "/cache/"+uri+".txt");
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream outStream = new FileOutputStream(
                    PackageUtils.getApplicationDataDirectory(context) + "/cache/"+uri+".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outStream);

            objectOutputStream.writeObject(datas);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件缓存读取
     * @param context
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObject(Context context, String uri, Class<T> clazz) {
        FileInputStream freader;
        T datas = null;
        try {
            datas = clazz.newInstance();
            freader = new FileInputStream(
                    PackageUtils.getApplicationDataDirectory(context) + "/cache/"+uri+".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            datas = (T) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 解压缩功能.
     * 将zipFile文件解压到folderPath目录下.
     * @throws Exception
     */
    public static void upZipFile(File zipFile, String folderPath)throws ZipException,IOException {
        //public static void upZipFile() throws Exception{
        ZipFile zfile=new ZipFile(zipFile);
        Enumeration zList=zfile.entries();
        ZipEntry ze=null;
        byte[] buf=new byte[1024];
        while(zList.hasMoreElements()){
            ze=(ZipEntry)zList.nextElement();
            if(ze.isDirectory()){
                String dirstr = folderPath + ze.getName();
                //dirstr.trim();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                File f=new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os=new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is=new BufferedInputStream(zfile.getInputStream(ze));
            int readLen=0;
            while ((readLen=is.read(buf, 0, 1024))!=-1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param baseDir 指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName){
        String[] dirs=absFileName.split("/");
        File ret=new File(baseDir);
        String substr = null;
        if(dirs.length>1){
            for (int i = 0; i < dirs.length-1;i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ret=new File(ret, substr);

            }
            if(!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length-1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ret=new File(ret, substr);
            return ret;
        }
        return ret;
    }
}
