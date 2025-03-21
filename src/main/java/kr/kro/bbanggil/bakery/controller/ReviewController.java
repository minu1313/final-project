package kr.kro.bbanggil.bakery.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.bakery.dto.request.ReviewRequestDto;
import kr.kro.bbanggil.bakery.dto.response.ReviewResponseDto;
import kr.kro.bbanggil.bakery.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

	private static final Logger logger = LogManager.getLogger(ReviewController.class);
	private final ReviewServiceImpl reviewService;

	/*
	 * 리뷰 작성 insert
	 */
	@PostMapping("/write")
	public ResponseEntity<String> writeReview(@RequestPart("reviewDto") ReviewRequestDto reviewDto, // JSON 데이터 받기
			@RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage, // 이미지 받기
	HttpSession session) {
		
		String userId =(String) session.getAttribute("userId");
		Integer userNo = (Integer) session.getAttribute("userNum");
		
		reviewDto.setUserNo(userNo);
		reviewDto.setUserId(userId);
		
		reviewDto.setReviewImage(reviewImage);

		reviewService.writeReview(reviewDto); // 리뷰 저장

		return ResponseEntity.ok("리뷰 등록 완료");

	}

	@PostMapping("/edit")
	public String editReview(@ModelAttribute ReviewRequestDto reviewRequestDto,
			@RequestParam(value = "reviewImage", required = false) List<MultipartFile> files,
			RedirectAttributes redirectAttributes) {

		System.out.println("받은 reviewNo: " + reviewRequestDto.getReviewNo());
		System.out.println(reviewRequestDto.getReviewNo());

		ReviewResponseDto existingReview = reviewService.getReviewId(reviewRequestDto.getReviewNo());

		int result = reviewService.editReview(reviewRequestDto, files);

		return "redirect:/bakery/detail?bakeryNo=" + reviewRequestDto.getReviewNo();

	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteReview(@RequestParam("reviewNo") int reviewNo,
			@RequestParam(value = "fileName", defaultValue = "none") String fileName) {
		int userNo = 1;

		
		int result = reviewService.deleteReview(reviewNo, userNo, fileName);
		
		if (result > 0) {
			return ResponseEntity.ok("리뷰 삭제 성공");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 삭제 실패");
		}

	}
	
	
	
	
	
	
}
