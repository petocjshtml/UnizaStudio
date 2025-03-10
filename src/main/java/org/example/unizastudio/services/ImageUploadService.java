package org.example.unizastudio.services;

import com.cloudinary.Cloudinary;
import org.example.unizastudio.config.CloudinaryProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public ImageUploadService(CloudinaryProperties properties) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", properties.getCloudName());
        config.put("api_key", properties.getApiKey());
        config.put("api_secret", properties.getApiSecret());
        this.cloudinary = new Cloudinary(config);
    }

    @SuppressWarnings("unchecked")
    public String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()))
                .replace(" ", "_");
        Map<String, Object> options = new HashMap<>();
        options.put("public_id", originalFilename);
        options.put("folder", "unizaStudio/users");
        Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), options);
        return uploadResult.get("secure_url").toString();
    }
}
