package kr.kro.bbanggil.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 빵집에 대한 정보
@Getter
@Setter
@NoArgsConstructor
public class BakeryInfoDTO {
	private int	bakeryNo;
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private String bakeryExp;
	private String bakeryAmenity;
	private String bakeryInsideInfo;
	private String bakeryOutsideInfo;
	private BakeryReviewDTO bakeryReviewDTO = new BakeryReviewDTO();
	private BakeryScheduleDTO bakeryScheduleDTO = new BakeryScheduleDTO();
	
}
