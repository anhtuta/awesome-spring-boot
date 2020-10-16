package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import hello.common.Result;
import hello.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {
    
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @GetMapping
    public ResponseEntity<ByteArrayResource> getFile(@RequestParam("name") String name) {
        return fileService.getFile(name);
    }
}
