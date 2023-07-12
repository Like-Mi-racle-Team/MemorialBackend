package com.LikeMiracleTeam.MemorialBackend.service.impl;

import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.repository.PostRepository;
import com.LikeMiracleTeam.MemorialBackend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final PostRepository postRepository;

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\";

        String originalFilename = file.getOriginalFilename();
        String saveFileName = createSaveFileName(originalFilename);

        file.transferTo(new File(getFullPath(uploadPath, saveFileName)));

        return saveFileName;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(Long postNo) throws IOException{
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\";
        uploadPath += post.getFileName();

        File file = new File(uploadPath);

        InputStream targetStream =
                new DataInputStream(new FileInputStream(file));

        String encodedOriginalFileName = UriUtils.encode(post.getOriFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(new InputStreamResource(targetStream));
    }

    @Override
    public Boolean deleteFile(String fileName) throws IOException {
        String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\";
        File file = new File(uploadPath + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    private String createSaveFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자명 구하기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // fullPath 만들기
    private String getFullPath(String uploadPath, String filename) {
        return uploadPath + filename;
    }
}
