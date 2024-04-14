package dev.bananaftmeo.netcafeserver.services.fileservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FileService implements IFileService {
    private String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/fir-resources-hosting.appspot.com/o/%s?alt=media";
    private final String BUCKET_NAME = "fir-resources-hosting.appspot.com";

    @Override
    public String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials;
        credentials = GoogleCredentials
                .fromStream(
                        new FileInputStream(
                                "src/main/resources/fir-resources-hosting-firebase-adminsdk-dxxok-2f8a829e7e.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    @Override
    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename(); // to get original file name
        fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName)); // to generated random string
                                                                                     // values for file name.

        File file = this.convertToFile(multipartFile, fileName); // to convert multipartFile to File
        String TEMP_URL = this.uploadFile(file, fileName); // to get uploaded file link
        file.delete(); // to delete the copy of uploaded file stored in the project folder
        return TEMP_URL;
    }

    @Override
    public Blob download(String fileName) throws IOException {
        // String destFileName =
        // UUID.randomUUID().toString().concat(this.getExtension(fileName)); // to set
        // random string
        // for destination file
        // name
        // String destFilePath = "D:\\netcafeserver-imgs\\" + destFileName; // to set
        // destination file path

        //////////////////////////////// Download
        //////////////////////////////// ////////////////////////////////////////////////////////////////////////
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(
                        "src/main/resources/fir-resources-hosting-firebase-adminsdk-dxxok-2f8a829e7e.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, fileName));
        // blob.downloadTo(Paths.get(destFilePath));
        return blob;
    }

    @Override
    public List<String> getAllImageNames(int page, int pageSize) throws IOException {
        // Load credentials from JSON file
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(
                        "src/main/resources/fir-resources-hosting-firebase-adminsdk-dxxok-2f8a829e7e.json"));

        // Create a storage instance with the loaded credentials
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        // Get all blobs in the bucket
        Iterable<Blob> blobs = storage.list(BUCKET_NAME).iterateAll();

        // Initialize a list to store image filenames
        List<String> imageNames = new ArrayList<>();

        // Iterate through blobs and add image filenames to the list
        for (Blob blob : blobs) {
            if (blob.getName().toLowerCase().endsWith(".jpg") || blob.getName().toLowerCase().endsWith(".png")) {
                String[] parts = blob.getName().split("/");
                String filename = parts[parts.length - 1];
                imageNames.add(filename);
            }
        }

        // Apply paging
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, imageNames.size());
        return imageNames.subList(startIndex, endIndex);
    }

    @Override
    public boolean delete(String fileName) throws IOException {
        // Load credentials from JSON file
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(
                        "src/main/resources/fir-resources-hosting-firebase-adminsdk-dxxok-2f8a829e7e.json"));
        // Create a storage instance with the loaded credentials
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        // Get the BlobId of the file to delete
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);

        // Check if the file exists
        if (storage.get(blobId) == null) {
            return false; // File not found, return false
        }

        // Delete the file
        storage.delete(blobId);

        return true; // File deleted successfully
    }
}
