package kr.kro.bbanggil.admin.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminResponseDto {
	
	private String userNo;
	private String userName;
	private String userId;
	private String userPhoneNum;
	private String userEmail;
	private String userType;
	private String agreeEmail;
	private String bakeryNo;
	private String bakeryName;
	private String bakeryAddress;
	private String amenity;
	private String insideInfo;
	private String outsideInfo;
	private String agree;
	private String submissionDate;
	private String acceptDate;
	
	private List<BakeryImgResponseDto> bakeryImgPath;

}
