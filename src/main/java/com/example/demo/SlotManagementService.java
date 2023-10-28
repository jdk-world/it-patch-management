package com.example.demo;

import java.awt.print.Book;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.models.HttpMethod;

@Service
public class SlotManagementService {
	@Autowired
	SpringBootJdbcController springBootJdbcController;
	@Autowired
	private RestTemplate restTemplate;
	// private static final String USER_MGMT_SERVICE_BASE_URL =
	// "http://localhost:8082/api/user";

	@Value("${SLOT_MGMT_SERVICE_BASE_URL}")
	private String SLOT_MGMT_SERVICE_BASE_URL;

	public String insertslot(SlotModel empModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SlotModel> request = new HttpEntity<>(empModel, headers);
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(SLOT_MGMT_SERVICE_BASE_URL + "/insertslot", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	public String insertslots(SlotModel empModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SlotModel> request = new HttpEntity<>(empModel, headers);
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(SLOT_MGMT_SERVICE_BASE_URL + "/insertslots", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	public String deleteslots(List<String> slotIdList) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<String>> request = new HttpEntity<>(slotIdList, headers);
	    
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(SLOT_MGMT_SERVICE_BASE_URL + "/deleteslots", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}


	
}
