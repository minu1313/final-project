package kr.kro.bbanggil.admin.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;

public interface AdminService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();

	AdminResponseDto bakeryDetailList(int bakeryNo, int userNo);

	AdminResponseDto userDetailList(int userNo);

	AdminResponseDto acceptList(int bakeryNo, int userNo);

	List<MenuResponseDto> menuList(int bakeryNo);
	
	void update(String action, int bakeryNo, String rejectReason);

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

	void saveInquiry(InquiryRequestDto inquiryRequestDto);

	List<InquiryResponseDto> getInquiryList();

	void saveAnswer(InquiryReplyRequestDto inquiryReplyDto);

	Map<String, Object> trafficMonitoring();

	Map<String, Object> bottomContent();

	List<MonthlyOrderResponseDTO> getMonthlyOrderCount();

	List<MenuResponseDto> categoryList();

	void addCategory(String newCategory);

	void deleteCategory(Map<String, List<String>> requestBody);

	List<AdminResponseDto> reportList();

	AdminResponseDto reportDetail(int reportNo);

	void insertReport(ReportRequestDTO reportDTO, String userId, int reportNo);

	InquiryResponseDto getInquiryByNo(int inquiryNo);

}
