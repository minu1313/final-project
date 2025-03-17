package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.common.dto.PageInfoDTO;

@Mapper
public interface BakeryMapper {

	
	public List<BakeryInfoDTO> bakeryList(@Param("pi") PageInfoDTO pi,
										  @Param("day") String day,
										  @Param("orderType")String orderType,
										  @Param("bakerySearchDTO")BakerySearchDTO bakerySearchDTO);
	
	public int totalCount(BakerySearchDTO bakerySearchDTO);
	
	public List<BakeryInfoDTO> bakeryImage(@Param("bakeryNo") int bakeryNo);
	

	

}
