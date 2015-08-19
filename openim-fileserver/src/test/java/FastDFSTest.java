import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * Created by shihc on 2015/8/17.
 */
public class FastDFSTest {

    private static final String confFile = "client.conf";
    private static final String localFile = "test.txt";

    @Test
    public void uploadFile(){

        try {
            String path = this.getClass().getResource(confFile).getPath();
            ClientGlobal.init(path);
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            long startTime;
            String group_name;
            String remote_filename;
            ServerInfo[] servers;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();

            StorageServer storageServer = null;

  		/*
  		storageServer = tracker.getStoreStorage(trackerServer);
  		if (storageServer == null)
  		{
  			System.out.println("getStoreStorage fail, error code: " + tracker.getErrorCode());
  			return;
  		}
  		*/

            StorageClient client = new StorageClient(trackerServer, storageServer);
            //byte[] file_buff;
            NameValuePair[] meta_list;
            String[] results;
            String master_filename;
            String prefix_name;
            String file_ext_name;
            String generated_slave_filename;
            int errno;

            meta_list = new NameValuePair[4];
            meta_list[0] = new NameValuePair("width", "800");
            meta_list[1] = new NameValuePair("heigth", "600");
            meta_list[2] = new NameValuePair("bgcolor", "#FFFFFF");
            meta_list[3] = new NameValuePair("author", "Mike");

            //file_buff = "this is a test".getBytes(ClientGlobal.g_charset);
            //System.out.println("file length: " + file_buff.length);

            group_name = null;
            StorageServer[] storageServers = tracker.getStoreStorages(trackerServer, group_name);
            if (storageServers == null) {
                System.err.println("get store storage servers fail, error code: " + tracker.getErrorCode());
            } else {
                System.err.println("store storage servers count: " + storageServers.length);
                for (int k = 0; k < storageServers.length; k++) {
                    System.err.println((k + 1) + ". " + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
                }
                System.err.println("");
            }

            startTime = System.currentTimeMillis();
            //results = client.upload_file(file_buff, "txt", meta_list);

            String filePath = this.getClass().getResource(localFile).getPath();
            results = client.upload_file(filePath, "txt", meta_list);
            System.out.println("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

  		/*
  		group_name = "";
  		results = client.upload_file(group_name, file_buff, "txt", meta_list);
  		*/
            if (results == null) {
                System.err.println("upload file fail, error code: " + client.getErrorCode());
                return;
            } else {
                group_name = results[0];
                remote_filename = results[1];
                System.err.println("group_name: " + group_name + ", remote_filename: " + remote_filename);
                System.err.println(client.get_file_info(group_name, remote_filename));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void downloadFile(){
        try {
            String path = this.getClass().getResource(confFile).getPath();
            ClientGlobal.init(path);
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            long startTime;
            String group_name;
            String remote_filename;
            ServerInfo[] servers;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();

            StorageServer storageServer = null;

  		/*
  		storageServer = tracker.getStoreStorage(trackerServer);
  		if (storageServer == null)
  		{
  			System.out.println("getStoreStorage fail, error code: " + tracker.getErrorCode());
  			return;
  		}
  		*/

            StorageClient client = new StorageClient(trackerServer, storageServer);
            byte[] bytes = client.download_file("group1", "M00/00/00/wKhVQFXT-omADuKkAAAAEotaRcY795.txt");

            IOUtils.write(bytes, new FileOutputStream("C:\\down.txt"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
