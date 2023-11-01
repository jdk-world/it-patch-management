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
public class PatchManagementService {
	@Autowired
	SpringBootJdbcController springBootJdbcController;
	@Autowired
	private RestTemplate restTemplate;

	// private static final String PATCH_MGMT_SERVICE_BASE_URL =
	// "http://localhost:8081/api/patch";

	@Value("${PATCH_MGMT_SERVICE_BASE_URL}")
	private String PATCH_MGMT_SERVICE_BASE_URL;

	public Page<Patch> findPaginatedCatelog(Pageable pageable) {

		// List<Book> books = BookUtils.buildBooks();
		// List<Patch> books = springBootJdbcController.findAllSlots(filterArr);

		// List<Patch> books = (List<Patch>)
		// restTemplate.getForObject("http://localhost:8081/api/patchcatelog",
		// Patch.class);
		// HttpHeaders headers = new HttpHeaders();
		// HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		// Create an HTTP request headers object.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HTTP request entity object.
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		List<Patch> books = new ArrayList<Patch>();
		try {

			HttpEntity<String> response = restTemplate.getForEntity(PATCH_MGMT_SERVICE_BASE_URL + "/catelog",
					String.class);
			System.err.println("hello");
			books = restTemplate.getForObject(PATCH_MGMT_SERVICE_BASE_URL + "/catelog", List.class);

		} catch (Exception e) {
			// TODO: handle exception
		}

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Patch> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Patch> bookPage = new PageImpl<Patch>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;

	}

	public String createPatch(Patch patchModel) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Patch> request = new HttpEntity<>(patchModel, headers);

		// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
		String response = restTemplate.postForObject(PATCH_MGMT_SERVICE_BASE_URL + "/create-patch", request,
				String.class);
		return response;
	}

	public String deleteslots(List<String> slotIdList) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<String>> request = new HttpEntity<>(slotIdList, headers);
	    
		String response = "";
		try {

			// HttpEntity<Patch> request = new HttpEntity<>(patchModel);
			response = restTemplate.postForObject(PATCH_MGMT_SERVICE_BASE_URL + "/remove-patch", request, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
	
	
}
