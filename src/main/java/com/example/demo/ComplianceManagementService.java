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
public class ComplianceManagementService {
	@Autowired
	SpringBootJdbcController springBootJdbcController;
	@Autowired
	private RestTemplate restTemplate;
	// private static final String USER_MGMT_SERVICE_BASE_URL =
	// "http://localhost:8082/api/user";

	@Value("${COMPLIANCE_MGMT_SERVICE_BASE_URL}")
	private String COMPLIANCE_MGMT_SERVICE_BASE_URL;

	public Page<Employee> findPaginatedComplianceReport(Pageable pageable, String[] filterArr) {
		// List<Book> books = BookUtils.buildBooks();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		HttpEntity<String[]> request = new HttpEntity<>(filterArr, headers);
		String response = "";
		List<Employee> books = new ArrayList<Employee>();
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			// String response = restTemplate.postForObject(PATCH_MGMT_SERVICE_BASE_URL +
			// "/create-patch", request, String.class);
			response = restTemplate.postForObject(COMPLIANCE_MGMT_SERVICE_BASE_URL + "/filter", request, String.class);

			System.err.println("hello");
			books = restTemplate.postForObject(COMPLIANCE_MGMT_SERVICE_BASE_URL + "/filter", request, List.class);

		} catch (Exception e) {
			// TODO: handle exception
		}
		// List<Employee> books =
		// springBootJdbcController.findAllComplianceReports(filterArr);

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Employee> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Employee> bookPage = new PageImpl<Employee>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;

	}

	public List<Employee> findAllComplianceReports(String[] filterArr) {
		// List<Book> books = BookUtils.buildBooks();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		String responseBody = "";
		HttpStatus httpStatus = HttpStatus.OK; // You can use a different status code as needed
		ResponseEntity<String> response = new ResponseEntity<>(responseBody, httpStatus);

		try {

			response = restTemplate.getForEntity(COMPLIANCE_MGMT_SERVICE_BASE_URL + "/dashboard", String.class);
			System.err.println("hello");
			List<Employee> books = restTemplate.getForObject(COMPLIANCE_MGMT_SERVICE_BASE_URL + "/dashboard",
					List.class);

		} catch (Exception e) {
			// TODO: handle exception
		}

		// List<Employee> books =
		// springBootJdbcController.findAllComplianceReports(filterArr);

		String jsonResponse = response.getBody();

		// Create an ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		List<Employee> employees = new ArrayList<Employee>();

		try {
			// Use the ObjectMapper to parse the JSON array into a list of Employee objects
			employees = objectMapper.readValue(jsonResponse, new TypeReference<List<Employee>>() {
			});
		} catch (Exception e) {
			// Handle any parsing exceptions
		}

		return employees;

	}

}
