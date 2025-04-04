package kr.kro.bbanggil.user.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.user.member.dto.response.MypageUserResponseDto;
import kr.kro.bbanggil.user.member.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.user.member.dto.response.OwnerMypageResponseDTO;


@Mapper
public interface MypageMapper {
	
	public int getTotalCount(@Param("userNo")int userNo);
	
	//회원정보
	public List<MypageListResponseDto> getUserInfo(@Param("userNo")int userNo);
	
	//구매내역 정보
	public List<MypageListResponseDto> getBuyHistory(@Param("userNo")int userNo,
													 @Param("pi")PageInfoDTO pi);
	
	// 회원정보수정
	public int updateUser(@Param("userDto")MypageUserResponseDto userDto,
						 @Param("userNo")int userNo);
	
	//현재 비밀번호 가져오기
	public String getPassword(@Param("userNo") int userNo);
	
	//비밀번호수정
	public int updatePassword(@Param("userNo")int userNo,
							  @Param("userPassword")String userPassword);
	
	//주소 수정
	public int updateAddress(@Param("signupRequestDto")MemberRequestSignupDto signupRequestDto,
							@Param("userNo")int userNo);

	
	public int writeReview(@Param("mypageListDto")MypageListResponseDto mypageListDto,
							@Param("userNo")int userNo);
	
	public int deleteReview(@Param("mypageListDto")MypageListResponseDto mypageListDto);


	public List<OwnerMypageResponseDTO> ownerMypage(int userNum);

	public OwnerInfoResponseDTO ownerInfo(int userNum);

}
