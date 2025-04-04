package kr.kro.bbanggil.user.member.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.dto.request.PasswordRequestDto;
import kr.kro.bbanggil.user.member.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.user.member.dto.response.MypageUserResponseDto;
import kr.kro.bbanggil.user.member.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.user.member.dto.response.OwnerMypageResponseDTO;


public interface MypageService {
	
	Map<String, Object> getMyList(PaginationUtil mypagePagination, 
								  int currentPage, 
								  int postCount, 
								  int pageLimit,
								  int boardLimit,
								  int userNo);
	
	public int getTotalCount(int userNo);
	
	public MypageListResponseDto getMyInfo(int userNo);
	
	
	public int updateUser(MypageUserResponseDto userDto,int userNo);

	List<OwnerMypageResponseDTO> ownerMypage(int userNum);

	OwnerInfoResponseDTO ownerInfo(int userNum);

	
	int updatePassword(int userNo, PasswordRequestDto passwordDto);
	
	public int updateAddress(MemberRequestSignupDto signupRequestDto,int userNo);

	public int writeReview(MypageListResponseDto mypageListDto,int userNo);
	
	public int deleteReview(MypageListResponseDto mypageListDto);


	
}
	
