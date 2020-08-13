package me.zhengjie.utils;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;


/**
 * 文件服务器客户端
 * 
 * @author shijunbao
 *
 */
@Slf4j
public class FastDFSClient {

    /**
     * 文件上传
     * 
     * @param fileContent  文件的内容，字节数组
     * @param extName  文件扩展名，不包含（.）
     * @return
     */
    public static String uploadFile(byte[] fileContent, String extName) {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 文件上传
     * 
     * @param fileContent  文件的内容，字节数组
     * @param extName  文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     */
    public static String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) {
        log.info("FastDFS 文件上传开始");
        String conf = FastDFSClient.class.getResource("/").getPath() + "fastDFS-client.conf";
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        //利用fastdfs客户端，实现文件上传到fastdfs服务器上
        try {
            //1、加载配置文件
            ClientGlobal.init(conf);
            //2、创建一个tracker的客户端
            TrackerClient trackerClient = new TrackerClient();
            //3、通过trackerClient获取一个连接，连接到Tracker，得到一个TrackerServer
            trackerServer = trackerClient.getConnection();
            //4、通过trackerClient获取一个存储节点的StorageServer
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5、通过trackerServer和storageServer构造一个Storage客户端
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            // 文件上传到fastDFS
            String filePath = storageClient.upload_file1(fileContent, extName, metas);
            log.info("文件保存路径：" + filePath);
            log.info("FastDFS 文件上传结束");
            return filePath;
        } catch (IOException e) {
            log.error("FastDFS 文件上传异常");
            e.printStackTrace();
        } catch (MyException e) {
            log.error("FastDFS 文件上传异常");
            e.printStackTrace();
        } finally {
            try {
                //关闭服务，释放资源
                if (null != storageServer) {
                    storageServer.close();
                }
                if (null != trackerServer) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 文件上传
     * 
     * @param fileName 文件全路径
     * @return
     */
    public static String uploadFile(String fileName) {
        return uploadFile(fileName, null, null);
    }

    /**
     * 文件上传
     * 
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @return
     */
    public static String uploadFile(String fileName, String extName) {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 文件上传
     * 
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     */
    public static String uploadFile(String fileName, String extName, NameValuePair[] metas) {
        log.info("FastDFS 文件上传开始");
        String conf = FastDFSClient.class.getResource("/").getPath() + "fastDFS-client.conf";
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        //利用fastdfs客户端，实现文件上传到fastdfs服务器上
        try {
            //1、加载配置文件
            ClientGlobal.init(conf);
            //2、创建一个tracker的客户端
            TrackerClient trackerClient = new TrackerClient();
            //3、通过trackerClient获取一个连接，连接到Tracker，得到一个TrackerServer
            trackerServer = trackerClient.getConnection();
            //4、通过trackerClient获取一个存储节点的StorageServer
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5、通过trackerServer和storageServer构造一个Storage客户端
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            // 文件上传到fastDFS
            String filePath = storageClient.upload_file1(fileName, extName, metas);
            log.info("文件保存路径：" + filePath);
            log.info("FastDFS 文件上传结束");
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FastDFS 文件上传异常");
        } catch (MyException e) {
            e.printStackTrace();
            log.error("FastDFS 文件上传异常");
        } finally {
            try {
                //关闭服务，释放资源
                if (null != storageServer) {
                    storageServer.close();
                }
                if (null != trackerServer) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除文件
     * 
     * @param filePath
     * @return
     */
    public static int deleteFile(String filePath) {
        log.info("FastDFS 删除文件开始");
        String conf = FastDFSClient.class.getResource("/").getPath() + "fastDFS-client.conf";
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        //利用fastdfs客户端，实现文件上传到fastdfs服务器上
        try {
            //1、加载配置文件
            ClientGlobal.init(conf);
            //2、创建一个tracker的客户端
            TrackerClient trackerClient = new TrackerClient();
            //3、通过trackerClient获取一个连接，连接到Tracker，得到一个TrackerServer
            trackerServer = trackerClient.getConnection();
            //4、通过trackerClient获取一个存储节点的StorageServer
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5、通过trackerServer和storageServer构造一个Storage客户端
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            //fastdfs删除文件
            int result = storageClient.delete_file1(filePath);
            if (result == 0) {
                log.info("FastDFS 删除文件成功");
            } else {
                log.info("FastDFS 删除文件失败");
            }
            return result;
        } catch (IOException e) {
            log.error("FastDFS 删除文件异常");
            e.printStackTrace();
        } catch (MyException e) {
            log.error("FastDFS 删除文件异常");
            e.printStackTrace();
        } finally {
            try {
                //关闭服务，释放资源
                if (null != storageServer) {
                    storageServer.close();
                }
                if (null != trackerServer) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * 下载文件
     * 
     * @param filePath
     * @param localFilename 本地保存文件全路径
     * @return
     */
    public static int downloadFile(String filePath, String localFilename) {
        log.info("FastDFS 下载文件开始");
        String conf = FastDFSClient.class.getResource("/").getPath() + "fastDFS-client.conf";
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        //利用fastdfs客户端，实现文件上传到fastdfs服务器上
        try {
            //1、加载配置文件
            ClientGlobal.init(conf);
            //2、创建一个tracker的客户端
            TrackerClient trackerClient = new TrackerClient();
            //3、通过trackerClient获取一个连接，连接到Tracker，得到一个TrackerServer
            trackerServer = trackerClient.getConnection();
            //4、通过trackerClient获取一个存储节点的StorageServer
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5、通过trackerServer和storageServer构造一个Storage客户端
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            //fastdfs删除文件
            int result = storageClient.download_file1(filePath, localFilename);
            
            if (result == 0) {
                log.info("FastDFS 下载文件成功");
            } else {
                log.info("FastDFS 下载文件失败");
            }
            return result;
        } catch (IOException e) {
            log.error("FastDFS 下载文件异常");
            e.printStackTrace();
        } catch (MyException e) {
            log.error("FastDFS 下载文件异常");
            e.printStackTrace();
        } finally {
            try {
                //关闭服务，释放资源
                if (null != storageServer) {
                    storageServer.close();
                }
                if (null != trackerServer) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    
    
    
    /**
     * 
     * @discription 获取文件服务器上文件的字节数组
     * @author 张枭       
     * @created 2018年5月21日 下午4:50:42     
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static byte[] downloadByte(String groupName, String remoteFileName) {
    	log.info("FastDFS 下载文件开始");
        byte[] content = null;
        
        String conf = FastDFSClient.class.getResource("/").getPath() + "fastDFS-client.conf";
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        //利用fastdfs客户端，实现文件上传到fastdfs服务器上
        try {
            //1、加载配置文件
            ClientGlobal.init(conf);
            //2、创建一个tracker的客户端
            TrackerClient trackerClient = new TrackerClient();
            //3、通过trackerClient获取一个连接，连接到Tracker，得到一个TrackerServer
            trackerServer = trackerClient.getConnection();
            //4、通过trackerClient获取一个存储节点的StorageServer
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5、通过trackerServer和storageServer构造一个Storage客户端
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            content = storageClient.download_file(groupName, remoteFileName);
            
            log.info("FastDFS 下载文件结束"); 
        } catch (IOException e) {
            log.error("FastDFS 下载文件异常");
            e.printStackTrace();
        } catch (MyException e) {
            log.error("FastDFS 下载文件异常");
            e.printStackTrace();
        }finally {
            try {
                //关闭服务，释放资源
                if (null != storageServer) {
                    storageServer.close();
                }
                if (null != trackerServer) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 根据文件路径获取文件内容
     * @param filePath
     * @return
     */
    public static String getContext(String filePath){
		//filePath = fastUrl + filePath;//配置的上传路径
		String substr = filePath.substring(filePath.indexOf("group"));
		String group = substr.split("/")[0];
		String remoteFileName = substr.substring(substr.indexOf("/")+1);
		byte[] content=FastDFSClient.downloadByte(group, remoteFileName);
		return new String(content);
	}
    
    /**
     * @param args
     * @throws IOException 
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
       /* File file = new File("D:/upload/1.jpg");
        InputStream inputStream = new FileInputStream(file);
        byte[] fileContent = new byte[inputStream.available()];
        inputStream.read(fileContent);
        String filePath = FastDFSClient.uploadFile(fileContent, "jpg");
        //String filePath = FastDFSClient.uploadFile("E:/upload/g甘肃正确/WTS.jpg");
        FastDFSClient.downloadFile(filePath, "D:/upload/download.jpg");
        FastDFSClient.deleteFile(filePath);*/
        String conf = FastDFSClient.class.getResource("/").getPath() + "fastDFS-client.conf";
        log.info(conf);
    }
}
