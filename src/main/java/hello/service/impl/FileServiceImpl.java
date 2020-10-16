package hello.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import hello.common.Result;
import hello.service.FileService;
import hello.utils.MediaTypeUtils;

@Service
public class FileServiceImpl implements FileService {
    
    @Value("${uploadFolder}")
    private String uploadFolder;
    
    @Autowired
    private ServletContext servletContext;

    @Override
    public Result uploadFile(MultipartFile file) {
        Result result = new Result();
        if (file.isEmpty()) {
            result.failRes("Please select a file!");
            return result;
        }

        try {
            String fileName = saveUploadedFile(file);
            Map<String, Object> data = new HashMap<>(1);
            data.put("fileName", fileName);
            result.successRes(data);
        } catch (IOException e) {
            result.failRes(e.getMessage());
        }

        return result;
    }

    // save file
    // return: file name
    private String saveUploadedFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        
        System.out.println("name: " + file.getOriginalFilename());

        File uploadFolderDir = new File(uploadFolder);
        if (!uploadFolderDir.exists()) {
            uploadFolderDir.mkdirs();
        }
        
        String originalName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalName);
        String filename = FilenameUtils.getBaseName(originalName) + "_" + System.currentTimeMillis() + "." + extension;
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadFolder + File.separator + filename);
        Files.write(path, bytes);

        return filename;
    }

    // Ref: https://o7planning.org/vi/11765/vi-du-download-file-voi-spring-boot
    @Override
    public ResponseEntity<ByteArrayResource> getFile(String name) {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, name);
 
        Path path = Paths.get(uploadFolder + "/" + name);
        byte[] data;
        try {
            data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);
            
            return ResponseEntity.ok()
                    // Content-Disposition
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                    // Content-Type
                    .contentType(mediaType) //
                    // Content-Lengh
                    .contentLength(data.length) //
                    .body(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
