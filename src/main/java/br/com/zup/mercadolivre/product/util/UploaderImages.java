package br.com.zup.mercadolivre.product.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UploaderImages {

    private final Path root = Paths.get("uploads");

    public List<String> save(List<MultipartFile> files) {
        List<String> urls = new ArrayList<>();
        files.forEach(file -> {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            int i = originalFilename.indexOf(".");
            String extension = originalFilename.substring(i);
            String filename = UUID.randomUUID().toString() + extension;
            try {
                Files.copy(file.getInputStream(), root.resolve(filename));
            } catch (FileAlreadyExistsException e) {
                throw new RuntimeException("File already exists - " + filename);
            } catch (IOException e) {
                throw new RuntimeException("Error to save file");
            }
            urls.add(root.resolve(filename).toString());
        });
        return urls;
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
