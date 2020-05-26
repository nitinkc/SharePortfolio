package com.share.portfolio.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.portfolio.data.SharePortfolioEntity;
import com.share.portfolio.data.SharePortfolioRepository;
import com.share.portfolio.model.SharesPortfolioRequestModel;
import com.share.portfolio.model.SharesPortfolioResponseModel;
import com.share.portfolio.exception.PortfolioNotFoundException;


@Service
public class SharePortfolioServices {

	@Autowired
	private SharePortfolioRepository sharePortfolioRepository;

	public SharesPortfolioResponseModel save(SharePortfolioEntity requestModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		requestModel.setId(UUID.randomUUID().toString());

		SharesPortfolioResponseModel ret = modelMapper.map(sharePortfolioRepository.save(requestModel),
				SharesPortfolioResponseModel.class);

		return ret;
	}

	public List<SharesPortfolioResponseModel> saveAll(List<SharePortfolioEntity> requestModel) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		requestModel.stream()
		.forEach(x -> x.setId(UUID.randomUUID().toString()));
		
		List<SharePortfolioEntity> savedPortfolio = sharePortfolioRepository.saveAll(requestModel);
		
		//TypeToken to be used by the ModelMapper
		Type listType = new TypeToken<List<SharesPortfolioResponseModel>>() {}.getType();
		List<SharesPortfolioResponseModel> entities = modelMapper.map(savedPortfolio, listType);
				
		return entities;
	}

	public List<SharesPortfolioResponseModel> getPortfolioByEmail(String email) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Optional<List<SharePortfolioEntity>> sharePortfolioEntity = sharePortfolioRepository
				.findByEmail(email);
		
		if(sharePortfolioEntity.isEmpty()) {
			throw new PortfolioNotFoundException(email);
		}else {
			//TypeToken to be used by the ModelMapper
			Type listType = new TypeToken<List<SharesPortfolioResponseModel>>() {}.getType();
			List<SharesPortfolioResponseModel> entities = modelMapper.map(sharePortfolioEntity.get(), listType);
					
			return entities;
		}
	}
}