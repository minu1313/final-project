package kr.kro.bbanggil.admin.service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;
import kr.kro.bbanggil.admin.dto.response.NewlyResponseDTO;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.bakery.dto.InquiryEmailInfoDto;
import kr.kro.bbanggil.email.service.EmailServiceImpl;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final EmailServiceImpl emailServiceImpl;
	private final AdminMapper adminMapper;
	private final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

	@Override
	public List<AdminResponseDto> subList() {
		return adminMapper.subList();
	}
	
	@Override
	public List<AdminResponseDto> reportList(){
		List<AdminResponseDto> result = adminMapper.reportList();
		for(AdminResponseDto item : result) {
			int answer = adminMapper.getReportReplyCount(item.getReportNo());
			item.setAnswer(answer > 0 ? "Y" : "N" );
			item.setAdminId(answer == 0 ? "없음" : "없음");
		}
		return result;
	}
	@Override
	public AdminResponseDto reportDetail(int reportNo) {
		return adminMapper.reportDetail(reportNo);
	}
	
	@Override
	public void insertReport(ReportRequestDTO reportDTO,String userId, int reportNo) {
		String time = reportDTO.getReportResult();
		reportDTO.setReportNo(reportNo);
		if(time != "경고"){
			adminMapper.insertReport(reportDTO,userId);
		}
	}

	@Override
	public List<AdminResponseDto> bakeryList() {
		return adminMapper.bakeryList();
	}

	@Override
	public List<AdminResponseDto> userList() {
		return adminMapper.userList();
	}

	@Override
	public AdminResponseDto bakeryDetailList(int bakeryNo, int userNo) {
		return adminMapper.bakeryDetailList(bakeryNo, userNo);
	}
	
	@Override
	public AdminResponseDto userDetailList(int userNo) {
		return adminMapper.userDetailList(userNo);
	}
	
	@Override
	public AdminResponseDto acceptList(int bakeryNo, int userNo) {
		return adminMapper.acceptList(bakeryNo, userNo);
	}
	
	@Override
	public List<MenuResponseDto> menuList(int bakeryNo) {
		return adminMapper.menuList(bakeryNo);
	}
	
	@Override
	public void update(String action, int bakeryNo, String rejectReason) {
		adminMapper.update(action, bakeryNo, rejectReason);
	}

	@Override
	public void saveInquiry(InquiryRequestDto inquiryRequestDto) {

		
		Integer userNo = inquiryRequestDto.getUserNo();
		
		if (userNo == null || userNo == 0) {
	        adminMapper.insertInquiry(inquiryRequestDto);
	        return;
	    }
	

		String userType = adminMapper.getUserType(userNo);
		
		if (userType == null || (!userType.equals("user") && !userType.equals("owner"))) {
			throw new IllegalArgumentException("일반 사용자 또는 사업자만 문의할 수 있습니다.");
		}

		adminMapper.insertInquiry(inquiryRequestDto);

	}
	@Override
	public List<InquiryResponseDto> getInquiryList(){
		return adminMapper.selectInquiryList();
	}
	
	@Override
	public void saveAnswer(InquiryReplyRequestDto inquiryReplyDto) {
	    // 현재 시간 세팅 (replyDate가 null이면)
	    if (inquiryReplyDto.getReplyDate() == null || inquiryReplyDto.getReplyDate().isEmpty()) {
	    	inquiryReplyDto.setReplyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	    }

	    // 답변 INSERT
	    adminMapper.insertInquiryReply(inquiryReplyDto);

	    // 문의 상태 "답변 완료"로 변경
	    adminMapper.updateInquiryStatusToAnswered(inquiryReplyDto.getInquiryNo());
	    
	    sendAnswerEmail(inquiryReplyDto.getInquiryNo());
	} 
	@Override
	public void sendEmail(AdminEmailRequestDto adminRequestDto) {

		String[] addresses = adminRequestDto.getAddress().split("\\s*,\\s*"); // 정규식
		String title = adminRequestDto.getTitle();
		String content = adminRequestDto.getContent();
		
		for(int i=0; i<addresses.length; i++) {
			emailServiceImpl.sendEmail(addresses[i], title, content);
		}
	}
	
	@Override
	public Map<String,Object> trafficMonitoring() {
		int todayUser = adminMapper.getTodayUser();
		int totalOrder = adminMapper.getTotalOrder();
		int newUser = adminMapper.getNewUser();
		
		
		Map<String,Object> result = new HashMap<>();
		result.put("today",todayUser);
		result.put("order",totalOrder);
		result.put("user",newUser);
		
		return result;
		
	}
	
	@Override
	public List<MonthlyOrderResponseDTO> getMonthlyOrderCount(){
		return adminMapper.getMonthlyOrderCount();
	}

	
	public Map<String,Object> bottomContent(){
		//최근 주문
		List<NewlyResponseDTO> newlyOrder = adminMapper.getNewlyOrder();
		
		//신고 내역
		
		
		//1:1 문의
		List<InquiryResponseDto> inquiry = adminMapper.getInquiries();
		
		
		Map<String,Object> result = new HashMap<>();
		result.put("new", newlyOrder);
		result.put("inquiry", inquiry);
		
		return result;
		
	}
	  private void sendAnswerEmail(int inquiryNo) {
	        InquiryEmailInfoDto info = adminMapper.getInquiryEmailInfo(inquiryNo);

	        if (info == null || info.getEmail() == null) {
	            logger.warn("이메일 전송 실패: 이메일 정보 없음 - inquiryNo: {}", inquiryNo);
	            return;
	        }

	        String subject = "[빵모아] 문의에 대한 답변이 등록되었습니다";
	        String body = """
	            안녕하세요, 빵모아입니다.<br><br>
	            문의 제목: %s<br>
	            문의 내용: %s<br><br>
	            <strong>[답변]</strong><br>
	            %s<br><br>
	            감사합니다.
	        """.formatted(info.getTitle(), info.getContent(), info.getReply());

	        emailServiceImpl.sendEmail(info.getEmail(), subject, body);
	    }
	  @Override
	  public InquiryResponseDto getInquiryByNo(int inquiryNo) {
	      return adminMapper.selectInquiryByNo(inquiryNo);
	  }

  @Override
	public List<MenuResponseDto> categoryList() {
		return adminMapper.categoryList();
	}
	
	@Override
	public void addCategory(String newCategory) {
		adminMapper.addCategory(newCategory);
	}
	
	@Override
	public void deleteCategory(Map<String, List<String>> requestBody) {
		
		List<String> categories = requestBody.get("selectedCategories");
		
		for(int i=0; i<categories.size(); i++) {
			String category = categories.get(i);  // 각 카테고리 이름을 꺼냄
			adminMapper.deleteCategory(category);
		}
	}
	
}
