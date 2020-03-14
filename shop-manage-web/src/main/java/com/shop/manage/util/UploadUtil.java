package com.shop.manage.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

public class UploadUtil {


    public static String upload(MultipartFile multipartFile) {
        String url = "http://192.168.0.106";
        try {
            String file = UploadUtil.class.getResource("/tracker.conf").getFile();
            ClientGlobal.init(file);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            byte[] bytes = multipartFile.getBytes();
            String orginalFilename = multipartFile.getOriginalFilename();
            int i = orginalFilename.lastIndexOf(".");
            String name = orginalFilename.substring(i + 1);
            String[] upload_file = storageClient.upload_file(bytes, name, null);
            for (int j = 0; j < upload_file.length; j++) {
                url += "/" + upload_file[j];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
