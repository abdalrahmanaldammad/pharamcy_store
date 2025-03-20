package com.example.pharmacystore.media;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {

  private final Cloudinary cloudinary;

  public ImageResponseDto uploadImage(MultipartFile file) throws IOException {
    Map resulte = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    String url = resulte.get("url").toString();
    String publicId = resulte.get("public_id").toString();
    return new ImageResponseDto(url, publicId);
  }
}
