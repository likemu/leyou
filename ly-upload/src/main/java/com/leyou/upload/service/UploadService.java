package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;

import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by lidatu on 2019/01/09 11:34
 */

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

	@Autowired
	private FastFileStorageClient storageClient;
	@Autowired
	private UploadProperties prop;
	//private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/png","bmp","image/jpg");
	public String uploadImage(MultipartFile file) {
		try {
			//校验文件
			String type = file.getContentType();
			if(!prop.getAllowTypes().contains(type)){ //如果文件类型不匹配 抛出异常
				throw new LyException(ExceptionEnum.INVALID_FILE_ERROR);
			}
			//检验文件内容
			BufferedImage image = ImageIO.read(file.getInputStream());
			if(image == null){
				throw new LyException(ExceptionEnum.INVALID_FILE_ERROR);
			}
			//目标路径
			//this.getClass().getClassLoader().getResource("").getFile();
			//保存文件到本地
			//file.transferTo(new File("D:\\IDEA\\code\\upload",file.getOriginalFilename()));
			//上传到fastFDS
			String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");//从 .后面开始截取
			System.out.println(extension);
			StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
			//返回路径 拼接图片地址
			System.out.println(storePath);
			return prop.getBaseUrl() + storePath.getFullPath(); //http://image.leyou.com/...
		} catch (IOException e) {
			//上传失败
			log.error("[文件上传] 上传文件失败", e);
			throw  new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
		}

	}
}
