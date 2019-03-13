package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by lidatu on 2019/01/10 21:05
 */

@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {
	private String baseUrl;
	private List<String> allowTypes;

}
