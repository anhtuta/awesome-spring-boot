package hello.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import hello.common.Result;

public interface FileService {

    public Result uploadFile(MultipartFile file);

    public ResponseEntity<ByteArrayResource> getFile(String name);

    public ResponseEntity<byte[]> getFile2(String name);

}
