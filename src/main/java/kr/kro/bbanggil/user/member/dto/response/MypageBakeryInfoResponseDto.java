package kr.kro.bbanggil.user.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MypageBakeryInfoResponseDto {
	
	/**
	 * 빵집 정보 테이블
	 * 빵집 이름(상호명)
	 */
	
	private String bakeryName;
	private String bakeryList;
	private int bakeryNo;
	
}
