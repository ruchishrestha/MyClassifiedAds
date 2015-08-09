package com.example.home_pc.myclassifiedads.classified_api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.home_pc.myclassifiedads.R;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Home-PC on 8/7/2015.
 */
public class ImageLoaderAPI {

    public static final String storageConnectionString =
                      "DefaultEndpointsProtocol=https;"
                    + "AccountName=classifiedimagestorage;"
                    + "AccountKey=hTeFYaGi2hEmF3jF0vK860iIIq6IPUFe5k+aIk+H3vzdZnMQI0Ry19RQyOVUCxYGgUquTuChjvBDH1fQV9jQwg==";

    public static ArrayList<String> AzureImageUploader(ArrayList<Bitmap> uploadImages,String adID){
        ArrayList<String> imagePaths = new ArrayList<String>();
        int photoCount=uploadImages.size();

        try {

            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            // Container name must be lower case.
            CloudBlobContainer container = serviceClient.getContainerReference("gallery");
            container.createIfNotExists();

            for(int i=0;i<photoCount;i++) {
                String imageName=adID+"_"+i;
                // Upload an image file.
                CloudBlockBlob blob = container.getBlockBlobReference(imageName);
                ByteArrayOutputStream uploadImageOS = new ByteArrayOutputStream();
                uploadImages.get(i).compress(Bitmap.CompressFormat.PNG, 0, uploadImageOS);
                byte[] uploadImageData = uploadImageOS.toByteArray();
                uploadImageOS.flush();
                blob.upload(new ByteArrayInputStream(uploadImageData), uploadImageData.length);
                imagePaths.add(blob.getUri().toString());
            }

           return imagePaths;

        }
        catch (FileNotFoundException fileNotFoundException) {

            System.out.print("FileNotFoundException encountered: ");
            System.out.println(fileNotFoundException.getMessage());

        }
        catch (StorageException storageException) {

            System.out.print("StorageException encountered: ");
            System.out.println(storageException.getMessage());

        }
        catch (Exception e) {

            System.out.print("Exception encountered: ");
            System.out.println(e.getMessage());

        }

        return null;
    }

    public static String AzureImageUploader(Bitmap uploadProfilePic,String userName){


        try {

            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            // Container name must be lower case.
            CloudBlobContainer container = serviceClient.getContainerReference("gallery");
            container.createIfNotExists();


            // Upload an image file.
            CloudBlockBlob blob = container.getBlockBlobReference(userName);
            ByteArrayOutputStream uploadImageOS = new ByteArrayOutputStream();
            uploadProfilePic.compress(Bitmap.CompressFormat.PNG, 0, uploadImageOS);
            byte[] uploadImageData = uploadImageOS.toByteArray();
            uploadImageOS.flush();
            blob.upload(new ByteArrayInputStream(uploadImageData), uploadImageData.length);


            return blob.getUri().toString();

        }
        catch (FileNotFoundException fileNotFoundException) {

            System.out.print("FileNotFoundException encountered: ");
            System.out.println(fileNotFoundException.getMessage());

        }
        catch (StorageException storageException) {

            System.out.print("StorageException encountered: ");
            System.out.println(storageException.getMessage());

        }
        catch (Exception e) {

            System.out.print("Exception encountered: ");
            System.out.println(e.getMessage());

        }

        return null;
    }

    public static ArrayList<Bitmap> AzureImageDownloader (ArrayList<String> imagePaths){

        ArrayList<Bitmap> downloadedImages=new ArrayList<Bitmap>();
        int urlCount=imagePaths.size();
        int current;

        for(int i=0;i<urlCount;i++) {
            try {

                URL imageUrl = new URL(imagePaths.get(i));
                URLConnection ucon = imageUrl.openConnection();
                InputStream imageIS = ucon.getInputStream();
                BufferedInputStream imageBIS = new BufferedInputStream(imageIS);
                ByteArrayBuffer imageBAB = new ByteArrayBuffer(1000);

                while ((current = imageBIS.read()) != -1) {
                    imageBAB.append((byte) current);
                }

                downloadedImages.add(BitmapFactory.decodeByteArray(imageBAB.toByteArray(), 0, imageBAB.toByteArray().length));
                imageBAB.clear();
                imageBIS.close();
                imageIS.close();

            } catch (Exception e) {

                System.out.print("Exception encountered: ");
                System.out.println(e.getMessage());

                return null;
            }
        }

        return downloadedImages;
    }

    public static Bitmap AzureImageDownloader (String imagePath){

        Bitmap downloadedImage;
        int current;

        try {

                URL imageUrl = new URL(imagePath);
                URLConnection ucon = imageUrl.openConnection();
                InputStream imageIS = ucon.getInputStream();

                BufferedInputStream imageBIS = new BufferedInputStream(imageIS);
                ByteArrayBuffer imageBAB = new ByteArrayBuffer(1000);

                while ((current = imageBIS.read()) != -1) {
                    imageBAB.append((byte) current);
                }

                downloadedImage = BitmapFactory.decodeByteArray( imageBAB.toByteArray(),0,imageBAB.toByteArray().length);
                imageBAB.clear();
                imageBIS.close();
                imageIS.close();

                return downloadedImage;

            } catch (Exception e) {

                System.out.print("Exception encountered: ");
                System.out.println(e.getMessage());

            }

        return null;
    }

}
