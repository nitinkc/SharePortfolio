package com.share.portfolio.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.share.portfolio.data.SharePortfolioEntity;
import com.share.portfolio.model.SharesPortfolioRequestModel;
import com.share.portfolio.model.SharesPortfolioResponseModel;
import com.share.portfolio.service.SharePortfolioServices;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

	@Autowired
	private SharePortfolioServices sharePortfolioServices;

	@PostMapping(value = "/add", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SharesPortfolioResponseModel> createPortfolio(
			@Validated @RequestBody SharesPortfolioRequestModel incoming) {
		System.err.println("###################################### POST Begins ######################################");

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SharePortfolioEntity entity = modelMapper.map(incoming, SharePortfolioEntity.class);

		SharesPortfolioResponseModel savedPortfolio = sharePortfolioServices.save(entity);
		System.err.println("###################################### POST Ends ######################################");

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPortfolio)
				.toUri();

		return ResponseEntity.created(location).build().status(HttpStatus.CREATED).header("MyResponseHeader", "MyValue")
				.body(savedPortfolio);
	}

	@PostMapping(value = "/addAll", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<SharesPortfolioResponseModel>> createAllPortfolio(
			@Validated @RequestBody List<SharesPortfolioRequestModel> incoming) {
		System.err.println("###################################### POST Begins ######################################");

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		// List<SharesPortfolioRequestModel> incomingList = incoming.stream().collec
		// List<SharePortfolioEntity> entityList = new
		// ArrayList<SharePortfolioEntity>();

		// TypeToken to be used by the Model Mapper
		Type listType = new TypeToken<List<SharePortfolioEntity>>() {
		}.getType();
		List<SharePortfolioEntity> entities = modelMapper.map(incoming, listType);

		List<SharesPortfolioResponseModel> savedPortfolio = sharePortfolioServices.saveAll(entities);
		System.err.println("###################################### POST Ends ######################################");

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPortfolio)
				.toUri();

		return ResponseEntity.created(location).build().status(HttpStatus.CREATED).header("MyResponseHeader", "MyValue")
				.body(savedPortfolio);
	}

	@GetMapping(value = "/{email}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<SharesPortfolioResponseModel>> getUser(@PathVariable("email") String email) {

		List<SharesPortfolioResponseModel> sharesPortfolioResponseModel = sharePortfolioServices.getPortfolioByEmail(email);
		
		// Returning HTTP Response body
		return ResponseEntity.status(HttpStatus.OK).body(sharesPortfolioResponseModel);
	}
}
