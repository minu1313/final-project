package kr.kro.bbanggil.email.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	
	List<String> getAllSubscriberEmails();
	Map<String, String> getRandomBakery();
	
}
