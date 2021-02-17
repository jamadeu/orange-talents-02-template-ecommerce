package br.com.zup.mercadolivre.product.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UploaderFake {

    public List<String> send(List<MultipartFile> images) {
        return images.stream().map(img -> "http://bucket.io/" + img.getOriginalFilename()).collect(Collectors.toList());
    }
}
