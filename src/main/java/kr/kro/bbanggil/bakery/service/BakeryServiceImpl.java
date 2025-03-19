package kr.kro.bbanggil.bakery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import kr.kro.bbanggil.bakery.request.dto.MenuRequestDto;
import kr.kro.bbanggil.bakery.response.dto.MenuResponseDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BakeryServiceImpl implements BakeryService {

	private static final Logger logger = LogManager.getLogger(BakeryServiceImpl.class);
	private final BakeryMapper bakeryMapper;

	@Override
	public void saveBakery(BakeryDto bakery) {

		try {
			bakeryMapper.insertBakery(bakery);
			logger.info("빵집 정보 저장 성공: {}",bakery.getName());
		}catch(Exception e) {
			logger.error("빵집 정보 저장 실패 : {}, 오류 메세지 :{}",bakery.getName(),e.getMessage(),e);
			throw new RuntimeException("빵집 정보를 저장하는 중 오류 발생");
		}
		

	}

	@Override
	public List<BakeryDto> getBakeriesByRegion(String region) {

		return bakeryMapper.getBakeriesByRegion(region);
	}

	@Override
	public List<BakeryDto> getPopularBakeries() {

		return bakeryMapper.getPopularBakeries();
	}

	@Override
	public List<BakeryDto> getRecentBakeries() {

		return bakeryMapper.getRecentBakeries();
	}

	@Override
	public List<BakeryDto> getCategoryBakeries(List<BakeryDto> topBakeries) {

		List<String> categoryNames = topBakeries.stream().map(bakery -> bakery.getResponse().getCategory()).distinct()
				.collect(Collectors.toList());

		return bakeryMapper.getCategoryBakeries(categoryNames);
	}

	@Override
	public List<BakeryDto> getTopFiveOrders() {
		return bakeryMapper.getTopFiveOrders();
	}

	@Override
	public List<BakeryDto> getBakeryImages(double no) {

		return bakeryMapper.findBakeryImages(no);
	}
	
	@Override
	public List<BakeryDto> getBakeriesInfo(double no){
		return bakeryMapper.findBakeriesInfo(no);
	}

	@Override
	public List<MenuResponseDto> getMenuInfo(double no){
		return bakeryMapper.getMenuInfo(no);
	}

	@Override
	public void addCart(int userNo, List<MenuRequestDto> menuDto) {

		Integer cartNo = bakeryMapper.getCartNoByUserNo(userNo);

		if (cartNo == null) {
			bakeryMapper.insertCart(userNo);
			cartNo = bakeryMapper.getLastCartNo();
		}

		for (MenuRequestDto item : menuDto) {
			bakeryMapper.insertCartInfo(cartNo, item.getMenuNo(), item.getMenuCount());
		}

	}

	public BakeryDto getBakeryByNo(double bakeryNo) {
		return bakeryMapper.findBakeryByNo(bakeryNo);
	}

	public List<BakeryDto> getBakeryDetail(double no) {
		
		return bakeryMapper.getBakeryDetail(no);
	}

	
	
	
	
	
	
	
}
