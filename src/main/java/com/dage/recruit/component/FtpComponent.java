package com.dage.recruit.component;

import lombok.RequiredArgsConstructor;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class FtpComponent {

    @Value("${ftp.server}")
    private String ftpServer;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.user}")
    private String ftpUser;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.remote.dir}")
    private String remoteDir;

    public void uploadFileFTP(String localFileName , File files) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("EUC-KR");
        FileInputStream fis = null;
        try {
            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
            ftpClient.connect(ftpServer, ftpPort);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.out.println("FTP server refused connection.");
                return;
            }

            boolean login = ftpClient.login(ftpUser, ftpPassword);
            if (!login) {
                System.out.println("Failed to login to the FTP server.");
                return;
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFilePath = remoteDir + localFileName.substring(0, localFileName.lastIndexOf('/'));

            // 디렉토리가 없으면 생성
            createRemoteDirectory(ftpClient, remoteFilePath);
            String tempFileName = remoteFilePath + "/" + localFileName.substring(localFileName.lastIndexOf('/') + 1);

            System.out.println(" **********remoteFilePath : ****" +  remoteFilePath);
            System.out.println(" **********tempFileName : ****" + tempFileName);

            fis = new FileInputStream(files);

            boolean done = ftpClient.storeFile(tempFileName, fis);
            if (done) {
                System.out.println("The file is uploaded successfully to " + remoteFilePath);
            } else {
                System.out.println("Failed to upload the file to " + remoteFilePath);
                int replyCode = ftpClient.getReplyCode();
                String replyString = ftpClient.getReplyString();
                System.out.println("FTP reply code: " + replyCode);
                System.out.println("FTP reply string: " + replyString);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public void deleteFileFTP(String localFileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("EUC-KR");
        try {
            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
            ftpClient.connect(ftpServer, ftpPort);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.out.println("FTP server refused connection.");
                return;
            }

            boolean login = ftpClient.login(ftpUser, ftpPassword);
            if (!login) {
                System.out.println("Failed to login to the FTP server.");
                return;
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFilePath = remoteDir + localFileName.substring(0, localFileName.lastIndexOf('/'));
            String tempFileName = remoteFilePath + "/" + localFileName.substring(localFileName.lastIndexOf('/') + 1);

            boolean deleted = ftpClient.deleteFile(tempFileName);
            if (deleted) {
                System.out.println("The file is deleted successfully from " + remoteFilePath);
            } else {
                System.out.println("Failed to delete the file from " + remoteFilePath);
                int replyCode = ftpClient.getReplyCode();
                String replyString = ftpClient.getReplyString();
                System.out.println("FTP reply code: " + replyCode);
                System.out.println("FTP reply string: " + replyString);
            }

//            String remoteFilePath = remoteDir + "/" + localFilePath.substring(localFilePath.lastIndexOf("static") + 7).replace("\\", "/");
//            String tempFileName = new String(remoteFilePath.getBytes("utf-8"), "utf-8");
//
//            boolean deleted = ftpClient.deleteFile(tempFileName);
//            if (deleted) {
//                System.out.println("The file is deleted successfully from " + localFilePath);
//            } else {
//                System.out.println("Failed to delete the file from " + localFilePath);
//                int replyCode = ftpClient.getReplyCode();
//                String replyString = ftpClient.getReplyString();
//                System.out.println("FTP reply code: " + replyCode);
//                System.out.println("FTP reply string: " + replyString);
//            }
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public void deleteDirectoryFTP(String localFileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("EUC-KR");
        try {
            ftpClient.connect(ftpServer, ftpPort);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.out.println("FTP server refused connection.");
                return;
            }

            boolean login = ftpClient.login(ftpUser, ftpPassword);
            if (!login) {
                System.out.println("Failed to login to the FTP server.");
                return;
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFilePath = remoteDir + localFileName.substring(0, localFileName.lastIndexOf('/'));
            String tempFileName = remoteFilePath + "/" + localFileName.substring(localFileName.lastIndexOf('/') + 1);

            System.out.println("remoteFilePath DIR **** : " + remoteFilePath);
            System.out.println("tempFileName DIR **** : " + tempFileName);
            // 폴더 및 하위 파일 삭제
            FTPFile[] files = ftpClient.listFiles(tempFileName);
            if (files != null) {
                for (FTPFile file : files) {
                    String filePath = tempFileName + "/" + file.getName();
                    System.out.println("Deleting file: " + filePath);

                        ftpClient.deleteFile(filePath); // 파일 삭제

                }
            }
            // 폴더 삭제
            ftpClient.removeDirectory(tempFileName);
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

//    public void deleteFTP(String localFilePath) throws IOException {
//        FTPClient ftpClient = new FTPClient();
//        ftpClient.setControlEncoding("EUC-KR");
//        try {
//            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
//            ftpClient.connect(ftpServer, ftpPort);
//            int reply = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftpClient.disconnect();
//                System.out.println("FTP server refused connection.");
//                return;
//            }
//
//            boolean login = ftpClient.login(ftpUser, ftpPassword);
//            if (!login) {
//                System.out.println("Failed to login to the FTP server.");
//                return;
//            }
//
//            ftpClient.enterLocalPassiveMode();
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//
//            String remoteFilePath = remoteDir + "\\" + localFilePath.substring(localFilePath.lastIndexOf("static") + 7).replace("\\", "/");
//            String tempFileName = new String(remoteFilePath.getBytes("utf-8"), "utf-8");
//
//            // 폴더 및 하위 파일 삭제
//            FTPFile[] files = ftpClient.listFiles(tempFileName);
//            if (files != null && files.length > 0) {
//                System.out.println("Deleting directory at: " + tempFileName);
//                for (FTPFile file : files) {
//                    String filePath = remoteFilePath + "/" + file.getName();
//                    System.out.println("Deleting file: " + filePath);
//                    if (file.isDirectory()) {
//                        deleteFTP(filePath); // 재귀적으로 디렉토리 삭제
//                    } else {
//                        ftpClient.deleteFile(filePath); // 파일 삭제
//                    }
//                }
//                ftpClient.removeDirectory(tempFileName); // 디렉토리 삭제
//            } else {
//                boolean deleted = ftpClient.deleteFile(tempFileName);
//                if (deleted) {
//                    System.out.println("The file is deleted successfully from " + localFilePath);
//                } else {
//                    System.out.println("Failed to delete the file from " + localFilePath);
//                    int replyCode = ftpClient.getReplyCode();
//                    String replyString = ftpClient.getReplyString();
//                    System.out.println("FTP reply code: " + replyCode);
//                    System.out.println("FTP reply string: " + replyString);
//                }
//            }
//        } finally {
//            ftpClient.logout();
//            ftpClient.disconnect();
//        }
//    }

    private void createRemoteDirectory(FTPClient ftpClient, String remoteDirPath) throws IOException {
        String[] directories = remoteDirPath.split("/");
        String currentPath = "";
        for (String dir : directories) {
            if (dir.isEmpty()) continue;
            currentPath += "/" + dir;
            if (!ftpClient.changeWorkingDirectory(currentPath)) {
                if (ftpClient.makeDirectory(currentPath)) {
                    System.out.println("Created directory: " + currentPath);
                } else {
                    System.out.println("Failed to create directory: " + currentPath);
                }
            }
        }
    }
}
