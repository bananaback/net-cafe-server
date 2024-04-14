package dev.bananaftmeo.netcafeserver.services.fileservices;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;

public interface IFileService {
    String uploadFile(File file, String fileName) throws IOException; // used to upload a file

    File convertToFile(MultipartFile multipartFile, String fileName) throws IOException; // used to convert
                                                                                         // MultipartFile to File

    String getExtension(String fileName); // used to get extension of a uploaded file

    String upload(MultipartFile multipartFile) throws IOException;

    Blob download(String fileName) throws IOException;

    List<String> getAllImageNames(int page, int pageSize) throws IOException;

    boolean delete(String fileName) throws IOException;

}