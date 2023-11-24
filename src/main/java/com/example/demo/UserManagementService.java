package com.example.demo;

import java.awt.print.Book;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.swagger.models.HttpMethod;

@Service
public class UserManagementService {
	@Autowired
	SpringBootJdbcController springBootJdbcController;
	@Autowired
	private RestTemplate restTemplate;
	// private static final String USER_MGMT_SERVICE_BASE_URL =
	// "http://localhost:8082/api/user";

	@Value("${USER_MGMT_SERVICE_BASE_URL}")
	private String USER_MGMT_SERVICE_BASE_URL;

	public Page<Employee> findPaginatedEmp(Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		List<Employee> books = new ArrayList<Employee>();

		try {

			ResponseEntity<String> response = restTemplate.getForEntity(USER_MGMT_SERVICE_BASE_URL + "/emp",
					String.class);
			System.err.println("Making Call to -> "+USER_MGMT_SERVICE_BASE_URL);
			books = restTemplate.getForObject(USER_MGMT_SERVICE_BASE_URL + "/emp", List.class);

		} catch (Exception e) {
			// TODO: handle exception
		}

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

	public Page<Admin> findPaginatedAdmin(Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		List<Admin> books = new ArrayList<>();
		try {

			ResponseEntity<String> response = restTemplate.getForEntity(USER_MGMT_SERVICE_BASE_URL + "/admin",
					String.class);
			System.err.println("Making Call to -> "+USER_MGMT_SERVICE_BASE_URL);
			books = restTemplate.getForObject(USER_MGMT_SERVICE_BASE_URL + "/admin", List.class);

		} catch (Exception e) {
			// TODO: handle exception
		}
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Admin> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Admin> bookPage = new PageImpl<Admin>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;

	}

	public String addEmp(Employee empModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Employee> request = new HttpEntity<>(empModel, headers);
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(USER_MGMT_SERVICE_BASE_URL + "/add-emp", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}



	public String removeEmp(List<String> slotIdList) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<String>> request = new HttpEntity<>(slotIdList, headers);
	    
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(USER_MGMT_SERVICE_BASE_URL + "/remove-emp", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}


	public HashMap<Integer, String> getAllEmpAssetMap() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HashMap<Integer, String> books = new HashMap<Integer, String>();

		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);

			books = restTemplate.getForObject(USER_MGMT_SERVICE_BASE_URL + "/emp-asset", HashMap.class);

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return books;
	}

	
	
	public String addAdmin(Admin empModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Admin> request = new HttpEntity<>(empModel, headers);
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(USER_MGMT_SERVICE_BASE_URL + "/add-admin", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	public String addRole(Roles empModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Roles> request = new HttpEntity<>(empModel, headers);
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(USER_MGMT_SERVICE_BASE_URL + "/add-role", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	public String addRegion(Region empModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Region> request = new HttpEntity<>(empModel, headers);
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(USER_MGMT_SERVICE_BASE_URL + "/add-region", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}



	public String removeAdmin(List<String> slotIdList) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<String>> request = new HttpEntity<>(slotIdList, headers);
	    
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(USER_MGMT_SERVICE_BASE_URL + "/remove-admin", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	
	
	
	public Page<Regions> findPaginatedRegion(Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		List<Regions> books = new ArrayList<>();
		try {

			ResponseEntity<String> response = restTemplate.getForEntity(USER_MGMT_SERVICE_BASE_URL + "/region",
					String.class);
			System.err.println("Making Call to -> "+USER_MGMT_SERVICE_BASE_URL);
			books = restTemplate.getForObject(USER_MGMT_SERVICE_BASE_URL + "/region", List.class);
		} catch (Exception e) {
			// TODO: handle exception
		}

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Regions> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Regions> bookPage = new PageImpl<Regions>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;

	}

	public Page<Roles> findPaginatedRoles(Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		List<Roles> books = new ArrayList<>();
		try {

			ResponseEntity<String> response = restTemplate.getForEntity(USER_MGMT_SERVICE_BASE_URL + "/role",
					String.class);
			System.err.println("Making Call to -> "+USER_MGMT_SERVICE_BASE_URL);
			books = restTemplate.getForObject(USER_MGMT_SERVICE_BASE_URL + "/role", List.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Roles> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Roles> bookPage = new PageImpl<Roles>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;

	}

}
