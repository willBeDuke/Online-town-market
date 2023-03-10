package com.example.townmarket.common.domain.s3.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

  String upload(MultipartFile multipartFile, String bucket, String dirName) throws IOException;

  String putS3(File uploadFile, String bucket, String fileName);

  void removeFile(File targetFile);

  Optional<File> convert(MultipartFile multipartFile) throws IOException;

  String createStoreFileName(String originalFilename);

  String extractExt(String originalFilename);

}
