package org.example.unizastudio.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {
    // Gettery a settery
    private String cloudName;
    private String apiKey;
    private String apiSecret;

}
