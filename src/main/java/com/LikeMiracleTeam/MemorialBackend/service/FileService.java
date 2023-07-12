package com.LikeMiracleTeam.MemorialBackend.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveFile(MultipartFile file) throws IOException;
    ResponseEntity<Resource> downloadFile(Long postNo) throws IOException;
    Boolean deleteFile(String fileName) throws IOException;
}
