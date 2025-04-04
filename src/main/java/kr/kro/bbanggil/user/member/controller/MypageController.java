package kr.kro.bbanggil.user.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.dto.request.PasswordRequestDto;
import kr.kro.bbanggil.user.member.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.user.member.dto.response.MypageUserResponseDto;
import kr.kro.bbanggil.user.member.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.user.member.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.user.member.service.MypageServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/mypage")
@AllArgsConstructor
public class MypageController {
	
	private final MypageServiceImpl mypageService;
	private final PaginationUtil mypagePagination;
	

	@GetMapping("") 
	public String myPage(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
						 @SessionAttribute(value="userNum",required=false)Integer userNo,
							@SessionAttribute(value="userName",required=false)String userName,
							MypageListResponseDto mypageDto,
							 HttpSession session,
							 Model model) {

		
		int listCount = mypageService.getTotalCount(userNo);
		int pageLimit = 5;
		int boardLimit = 5;
		
		System.out.println(listCount);
		Map<String, Object> result = mypageService.getMyList(mypagePagination, 
															currentPage,
															listCount,
															pageLimit,
															boardLimit,
															userNo);
		
		MypageListResponseDto userInfo = mypageService.getMyInfo(userNo);
		
		List<MypageListResponseDto> getBuyHistory = (List<MypageListResponseDto>) result.get("getBuyHistory");
		

		for(MypageListResponseDto item : getBuyHistory) {
			System.out.println(item.getReviewDto().getOrderNo());
		}
		model.addAttribute("getBuyHistory",getBuyHistory);
		 model.addAttribute("userName",userName);
		 model.addAttribute("userInfo",userInfo);

		 model.addAttribute("historySize",getBuyHistory.size());

		PageInfoDTO piResult = (PageInfoDTO) result.get("pi");
		


		
		if(session.getAttribute("role").equals("owner"))
			model.addAttribute("goOwnerPage",true);
	    	else
	    	model.addAttribute("goOwnerPage",false);
            model.addAttribute("pi", piResult);
            
           
            
             
		return "user/mypage";
		
	}
	
	//회원 정보 수정
	
	@GetMapping("/edit/form")
	public String editForm(  @SessionAttribute(value="userNum",required=false)Integer userNo,
			 		//		@RequestParam(value="userNo",defaultValue="1") int userNo,
			 				Model model) {
		
		MypageListResponseDto userInfo = mypageService.getMyInfo(userNo);
		model.addAttribute("userInfo",userInfo);
			
		return "user/edit";
		
	}
	
	@PostMapping("/edit")
	public String edit(@Valid MypageUserResponseDto mypageDto,
						@SessionAttribute(value="userNum",required=false)Integer userNo
					   ) {
		 mypageService.updateUser(mypageDto,userNo);
		return "redirect:/mypage";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@ModelAttribute("passwordDto") @Valid PasswordRequestDto passwordDto,
								@SessionAttribute(value="userNum",required=false)Integer userNo) {

		mypageService.updatePassword(userNo,passwordDto);
	
		return "redirect:/mypage";
	}


	@PostMapping("/updateAddress")
	public String updateAddress(@ModelAttribute MemberRequestSignupDto signupRequestDto,
								@SessionAttribute(value="userNum", required=false)Integer userNo) {
		mypageService.updateAddress(signupRequestDto, userNo);
		
		return "redirect:/mypage";
	}
	
	@PostMapping("/writeReview")
	public String writeReview(MypageListResponseDto mypageListDto,
								@SessionAttribute(value="userNum", required=false)Integer userNo) {
		mypageService.writeReview(mypageListDto, userNo);
		
		return "redirect:/mypage";
	}
	
	@PostMapping("/deleteReview")
	public String deleteReview(MypageListResponseDto mypageListDto) {
		
		mypageService.deleteReview(mypageListDto);
		return "redirect:/mypage";
	}
	
	@GetMapping("/owner")
	public String ownerMypage(@SessionAttribute("userNum") int userNum,
							  Model model) {
		List<OwnerMypageResponseDTO> result = mypageService.ownerMypage(userNum);
		OwnerInfoResponseDTO info = mypageService.ownerInfo(userNum);
		model.addAttribute("info",info);
		model.addAttribute("bakeries",result);
		model.addAttribute("goMyPage",true);
		
		return "owner/owner-mypage";

	}
	
}
