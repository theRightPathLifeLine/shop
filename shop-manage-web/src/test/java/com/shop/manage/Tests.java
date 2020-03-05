package com.shop.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.stream.Stream;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class Tests {

    @Test
    public void test() throws IOException, MyException {

    }

    public static void main(String[] args) throws IOException, MyException {
        String file = Tests.class.getResource("/tracker.conf").getFile();
        ClientGlobal.init(file);
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename="D://testImage/358835245.png";
        String[] upload_file = storageClient.upload_file(orginalFilename, "png", null);
        Stream.of(upload_file).forEach(System.out::println);
//        for (int i = 0; i < upload_file.length; i++) {
//            String s = upload_file[i];
//            System.out.println("s = " + s);
//        }
    }
}
