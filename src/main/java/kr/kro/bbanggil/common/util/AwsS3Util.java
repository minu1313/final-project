package kr.kro.bbanggil.common.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.user.bakery.dto.request.FileRequestDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AwsS3Util {
	private final AmazonS3 amazonS3;
	private final String CLOUDFRONT_DOMAIN = "https://d2cbn2na1idn6c.cloudfront.net";
	
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    

    public void saveFile(MultipartFile multipartFile,FileRequestDTO fileDTO) throws IOException {
    	try {
    		String originalFilename = multipartFile.getOriginalFilename();
    		String changeName = UUID.randomUUID().toString() + "." + getFileExtension(originalFilename);
    		int urlLength = amazonS3.getUrl(bucket, changeName).toString().length();
    		int changeLength = changeName.length();
    		int result = urlLength - changeLength-1;
    		
    		ObjectMetadata metadata = new ObjectMetadata();
    		metadata.setContentLength(multipartFile.getSize());
    		metadata.setContentType(multipartFile.getContentType());
    		
    		amazonS3.putObject(bucket, changeName, multipartFile.getInputStream(), metadata);
    		
    		fileDTO.setChangeName(changeName);
    		fileDTO.setExtension(getFileExtension(originalFilename));
    		fileDTO.setOriginalName(originalFilename);
    		fileDTO.setSize(multipartFile.getSize());
    		fileDTO.setLocalPath("성공!!!");
    		fileDTO.setFolderNamePath("성공?");
    		fileDTO.setLocalResourcePath(CLOUDFRONT_DOMAIN);
    	} catch(Exception e) {
    		throw new BbanggilException("S3 업로드 실패","common/error",HttpStatus.METHOD_NOT_ALLOWED);
    	}
    		
    }
    
    public void deleteImage(String changeName)  {
        amazonS3.deleteObject(bucket, changeName);
    }
    private String getFileExtension(String fileName) {
        // fileName : 제목없음.png
        // dotIndex : 4
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex+1);
     }

}
