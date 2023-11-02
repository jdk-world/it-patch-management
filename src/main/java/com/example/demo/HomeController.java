package com.example.demo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

	String filter_slot_end, filter_slot_start, filter_time_zone, filter_region, filter_is_booked;
	String filter_patch_release_date, filter_patch_compliance_date, filter_patch_time_zone, filter_patch_region,
			filter_patch_name, filter_patch_compliance, filter_roll_no_emp_name;

	List<String> filter_time_zones = new ArrayList<>();
	boolean passedfromPostFlag = false;
	boolean checkedMarkForAvailableFilterCheckbox = false;
	HashSet<Region> regions = new HashSet<>();
	HashSet<String> org_roles = new HashSet<>();
	TreeSet<Roles> roles = new TreeSet<>();
	HashSet<String> patches = new HashSet<>();

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	BookService bookService;

	@Autowired
	PatchManagementService patchManagementService;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	ComplianceManagementService complianceManagementService;

	@Autowired
	SlotManagementService slotManagementService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	SpringBootJdbcController springBootJdbcController;	

	private String currentServiceId;
	private String currentServiceName;

	private String msg = "";

	@GetMapping({ "/", "/user-clear" })
	public String usrClear(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		resetContext();
		return listBooks(model, Optional.empty(), Optional.empty());
	}
	
	@PostMapping({ "/", "/user-clear" })
	public String usrClearPost(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		resetContext();
		return listBooks(model, Optional.empty(), Optional.empty());
	}

	@GetMapping({ "/user" })
	public String listBooks(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(7);
		String[] filterArr;

		passedfromPostFlag = false;

		filterArr = new String[] { filter_slot_start, filter_slot_end, filter_time_zone, filter_region,
				filter_is_booked };

		Page<SlotModel> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize), filterArr);

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);

		return "home.html";
	}

	@PostMapping({ "/user" })
	public String listBooksPost(Model model, @RequestParam("filter_slot_start") String filter_slot_start1,
			@RequestParam("filter_slot_end") String filter_slot_end1,
			@RequestParam("time_zone") String filter_time_zone1, @RequestParam("region") String filter_region1,
			@RequestParam(value = "is_booked", required = false) String is_booked,
			@RequestParam("selectedTimeZones") String selectedTimeZones) {
		this.filter_slot_end = filter_slot_end1;
		this.filter_slot_start = filter_slot_start1;
		this.filter_time_zone = filter_time_zone1;
		this.filter_region = filter_region1;
		this.filter_is_booked = is_booked;
		
		if(StringUtils.isNotBlank(selectedTimeZones)) {
		String[] timeZoneArray = selectedTimeZones.split(",");

        // Convert the array to a list
        this.filter_time_zones = Arrays.asList(timeZoneArray);
		}
		
		if (StringUtils.isNotBlank(filter_is_booked) && StringUtils.equalsIgnoreCase("false", filter_is_booked))
			this.filter_is_booked = "true";

		if (StringUtils.isNotBlank(is_booked) && "false".equalsIgnoreCase(is_booked))
			checkedMarkForAvailableFilterCheckbox = true;
		else
			checkedMarkForAvailableFilterCheckbox = false;

		passedfromPostFlag = true;
		return listBooks(model, Optional.empty(), Optional.empty());

	}

	@RequestMapping(value = "/user-svc/emp/add", method = RequestMethod.POST)
	public String addEmp(@ModelAttribute Employee patchModel, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		/**
		 * patchManagementService.createPatch(patchModel.getName(),
		 * patchModel.getDescription(), patchModel.getVersion(),
		 * patchModel.getReleaseDate(), patchModel.getComplianceDate(),
		 * patchModel.getApplication(),
		 * "Applicable",patchModel.getCompatibility(),patchModel.getIsActive());
		 */
		msg = userManagementService.addEmp(patchModel);

		model.addAttribute("msg", msg);
		populateList(model);

		return "add_emp.html";

	}

	@GetMapping({ "/user-svc/admin" })
	public String listAdmin(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		String[] filterArr;

		Page<Admin> bookPage = userManagementService.findPaginatedAdmin(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);

		return "list_admin.html";
	}

	@RequestMapping(value = "/user-svc/admin/add", method = RequestMethod.POST)
	public String addAdmin(@ModelAttribute Admin patchModel, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		/**
		 * patchManagementService.createPatch(patchModel.getName(),
		 * patchModel.getDescription(), patchModel.getVersion(),
		 * patchModel.getReleaseDate(), patchModel.getComplianceDate(),
		 * patchModel.getApplication(),
		 * "Applicable",patchModel.getCompatibility(),patchModel.getIsActive());
		 */
		msg = userManagementService.addAdmin(patchModel);

		model.addAttribute("msg", msg);
		Page<Admin> bookPage = userManagementService.findPaginatedAdmin(PageRequest.of(0, 10));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);

		return "list_admin.html";

	}

	@RequestMapping(value = "/user-svc/emp/add", method = RequestMethod.GET)
	public String addEmp(Model model) {
		populateList(model);

		return "add_emp.html";
	}

	@RequestMapping(value = "/user-svc/admin/add", method = RequestMethod.GET)
	public String addAdmin(ModelMap model) {
		model.addAttribute("regions", getAllRegions());

		model.addAttribute("org_roles", getAllOrgRoles());

		return "add_admin.html";
	}

	@RequestMapping(value = "/user-svc/admin/remove", method = RequestMethod.GET)
	public String removeAdmin(ModelMap model) {
		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		return "remove_admin.html";
	}

	
	
	@RequestMapping(value = "/user-svc/admin/remove", method = RequestMethod.POST)
	public String removeAdmin(@RequestParam("adminIds") String slotIds, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		
		List<String> slotIdList = new ArrayList<>();
		if (StringUtils.contains(slotIds, "-")) {
			slotIdList = extractedIds(slotIds);

		} else {

			slotIdList = Arrays.asList(slotIds.split("\\s*,\\s*"));
		}
		
		msg = userManagementService.removeAdmin(slotIdList);

		model.addAttribute("msg", msg);
		
		return "remove_admin.html";

	}

	
	
	
	
	
	@RequestMapping(value = "/user-svc/emp/remove", method = RequestMethod.GET)
	public String removeEmp(ModelMap model) {
		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		return "remove_emp.html";
	}


	
	
	@RequestMapping(value = "/user-svc/emp/remove", method = RequestMethod.POST)
	public String removeEmp(@RequestParam("empIds") String slotIds, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		
		List<String> slotIdList = new ArrayList<>();
		if (StringUtils.contains(slotIds, "-")) {
			slotIdList = extractedIds(slotIds);

		} else {

			slotIdList = Arrays.asList(slotIds.split("\\s*,\\s*"));
		}
		
		msg = userManagementService.removeEmp(slotIdList);

		model.addAttribute("msg", msg);
		
		return "remove_emp.html";

	}

	
	
	@RequestMapping(value = "/user-svc/admin/add-role", method = RequestMethod.GET)
	public String addRole(ModelMap model) {
		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		return "add_role.html";
	}

	@RequestMapping(value = "/user-svc/admin/add-role", method = RequestMethod.POST)
	public String addRole(@ModelAttribute Roles patchModel, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		/**
		 * patchManagementService.createPatch(patchModel.getName(),
		 * patchModel.getDescription(), patchModel.getVersion(),
		 * patchModel.getReleaseDate(), patchModel.getComplianceDate(),
		 * patchModel.getApplication(),
		 * "Applicable",patchModel.getCompatibility(),patchModel.getIsActive());
		 */
		msg = userManagementService.addRole(patchModel);

		model.addAttribute("msg", msg);
		Page<Admin> bookPage = userManagementService.findPaginatedAdmin(PageRequest.of(0, 10));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);

		return "list_admin.html";

	}

	@RequestMapping(value = "/user-svc/admin/add-region", method = RequestMethod.GET)
	public String addRegion(ModelMap model) {
		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		return "add_region.html";
	}

	@RequestMapping(value = "/user-svc/admin/add-region", method = RequestMethod.POST)
	public String addRegion(@ModelAttribute Region patchModel, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		/**
		 * patchManagementService.createPatch(patchModel.getName(),
		 * patchModel.getDescription(), patchModel.getVersion(),
		 * patchModel.getReleaseDate(), patchModel.getComplianceDate(),
		 * patchModel.getApplication(),
		 * "Applicable",patchModel.getCompatibility(),patchModel.getIsActive());
		 */
		msg = userManagementService.addRegion(patchModel);

		model.addAttribute("msg", msg);
		Page<Admin> bookPage = userManagementService.findPaginatedAdmin(PageRequest.of(0, 10));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);

		return "list_admin.html";

	}

	@GetMapping({ "/user-svc/admin/regions" })
	public String listRegions(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);
		String[] filterArr;

		if (passedfromPostFlag) {

		} else
			resetContext();

		passedfromPostFlag = false;

		filterArr = new String[] { filter_slot_start, filter_slot_end, filter_time_zone, filter_region,
				filter_is_booked };

		Page<Regions> bookPage = userManagementService.findPaginatedRegion(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);
		// getAllRegions();

		return "list_regions.html";
	}

	@GetMapping({ "/user-svc/admin/roles" })
	public String listRoles(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		String[] filterArr;

		if (passedfromPostFlag) {

		} else
			resetContext();

		passedfromPostFlag = false;

		filterArr = new String[] { filter_slot_start, filter_slot_end, filter_time_zone, filter_region,
				filter_is_booked };

		Page<Roles> bookPage = userManagementService.findPaginatedRoles(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);
		// getAllRegions();

		return "list_roles.html";
	}

	/**
	 * @param startDateTime
	 * @param endDateTime
	 * @param emailId
	 * @param requestId
	 * @param timeZone
	 * @param is_booked
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws MessagingException 
	 */
	@RequestMapping(value = "/bookslot-and-create-event", method = RequestMethod.POST)
	public ResponseEntity bookSlotAndCreateEvent(@RequestParam String startDateTime, @RequestParam String endDateTime,
			@RequestParam String emailId, @RequestParam int requestId, @RequestParam String timeZone,
			@RequestParam String is_booked) throws IOException, GeneralSecurityException, MessagingException {
		// System.err.println("sku for adding to estimate----------->" + sku);
		// redirAttrs.addFlashAttribute("message", "This is message from flash");
		// Sample startDateTime-> 2022-08-12 19:30:00.0->2022-08-12T20:30:00.0
		String startDateTimeStr = StringUtils.replace(startDateTime, " ", "T");// + "-00:00";
		String endDateTimeStr = StringUtils.replace(endDateTime, " ", "T");// + "-00:00";

		String[] emailArr = StringUtils.stripAll(StringUtils.split(emailId, ","));
//2022-08-12T20:30:00.0
//2022-08-24T09:00:00.0
		String eventLink = springBootJdbcController.createEvent(startDateTimeStr, endDateTimeStr, timeZone, emailArr,
				requestId);
		System.err.println(emailId);

		springBootJdbcController.updateSlot(requestId, emailId, is_booked, eventLink);
		// return " Success ";
		return new ResponseEntity(HttpStatus.OK);

	}
	@RequestMapping(value = "/catelog-svc/create-patch-request", method = RequestMethod.POST)
	public ResponseEntity bookPatchingRequest(
	        @RequestParam String patchName,
	        @RequestParam String hostName,
	        @RequestParam String empId,
	        @RequestParam String preferredTime) {
	    
		PatchingRequest patchingRequest = new PatchingRequest(patchName, hostName, empId, preferredTime);
		System.err.println("patchingRequest");
		String eventLink = springBootJdbcController.createPatchingRequest(patchingRequest);	   

	    return new ResponseEntity(HttpStatus.OK);
	}


	@GetMapping({ "/catelog-svc/patch" })
	public String listCatelog(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(7);
		String[] filterArr;

		Page<Patch> bookPage = patchManagementService.findPaginatedCatelog(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);
		// getAllRegions();

		return "patch_catelog.html";
	}

	
	
	@RequestMapping(value = "/catelog-svc/patch/remove", method = RequestMethod.POST)
	public String removePatch(@RequestParam("patchIds") String slotIds, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		
		List<String> slotIdList = new ArrayList<>();
		if (StringUtils.contains(slotIds, "-")) {
			slotIdList = extractedIds(slotIds);

		} else {

			slotIdList = Arrays.asList(slotIds.split("\\s*,\\s*"));
		}
		
		msg = patchManagementService.deleteslots(slotIdList);

		model.addAttribute("msg", msg);
		
		return "remove_patch.html";

	}

	@RequestMapping(value = "/catelog-svc/patch/create", method = RequestMethod.GET)
	public String createPatch() {
		return "create_patch.html";
	}

	@RequestMapping(value = "/catelog-svc/patch/create", method = RequestMethod.POST)
	public String createPatch(@ModelAttribute Patch patchModel, ModelMap model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		patchModel.setIsActive("Yes");
		/**
		 * patchManagementService.createPatch(patchModel.getName(),
		 * patchModel.getDescription(), patchModel.getVersion(),
		 * patchModel.getReleaseDate(), patchModel.getComplianceDate(),
		 * patchModel.getApplication(),
		 * "Applicable",patchModel.getCompatibility(),patchModel.getIsActive());
		 */
		msg = patchManagementService.createPatch(patchModel);

		model.addAttribute("msg", msg);

		return "create_patch.html";

	}

	@RequestMapping(value = "/catelog-svc/patch/remove", method = RequestMethod.GET)
	public String removePatch(ModelMap model) {
		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		return "remove_patch.html";
	}
	
	@RequestMapping(value = "/catelog-svc/patch/tag", method = RequestMethod.GET)
	public String tagPatch(ModelMap model) {
		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		return "tag_patch.html";
	}

	@RequestMapping(value = "/catelog-svc/patch/tag", method = RequestMethod.POST)
	public String tagPatch(@RequestParam("patchIds") String patchIds, @RequestParam("empIds") String empIds, Model model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		
		List<String> patchIdsList = new ArrayList<>();
		if (StringUtils.contains(patchIds, "-")) {
			patchIdsList = extractedIds(patchIds);

		} else {

			patchIdsList = Arrays.asList(patchIds.split("\\s*,\\s*"));
		}
		
		List<String> empIdsList = new ArrayList<>();
		if (StringUtils.contains(empIds, "-")) {
			empIdsList = extractedIds(empIds);

		} else {

			empIdsList = Arrays.asList(empIds.split("\\s*,\\s*"));
		}
		
		
		TagRequest tagPatchObj = new TagRequest(patchIdsList, empIdsList);
		
		msg = patchManagementService.tagPatchsToEmps(tagPatchObj);

		model.addAttribute("msg", msg);
		
		return "tag_patch.html";

	}
	
	@GetMapping({ "/user-svc/emp" })
	public String listEmp(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		String[] filterArr;

		Page<Employee> bookPage = userManagementService.findPaginatedEmp(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("bookPage", bookPage);
		model.addAttribute("filter_slot_start", filter_slot_start);
		model.addAttribute("filter_slot_end", filter_slot_end);
		model.addAttribute("checkedMarkForAvailableFilterCheckbox", checkedMarkForAvailableFilterCheckbox);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateList(model);
		// getAllRegions();

		return "list_emp.html";
	}

	
    
    @GetMapping("/user-svc/emp-asset")
    public HashMap<Integer, String> getEmpAsset() {
        return userManagementService.getAllEmpAssetMap();
    }
    
    
	@GetMapping({ "/compliance-svc", "/compliance-svc/filter" })
	public String compFilter(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(7);
		String[] filterArr;

		// if (passedfromPostFlag) {

		// }else
		// resetContext();

		passedfromPostFlag = false;

		filterArr = new String[] { filter_patch_region, filter_patch_name, filter_patch_compliance,
				filter_roll_no_emp_name };

		Page<Employee> bookPage = complianceManagementService
				.findPaginatedComplianceReport(PageRequest.of(currentPage - 1, pageSize), filterArr);

		model.addAttribute("bookPage", bookPage);

		// boolean filter_is_booked_checkBox = !Boolean.valueOf(filter_is_booked) ;

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		populateListStickyCompliance(model, filterArr);
		// getAllRegions();
		// model.addAttribute("regions", getAllRegions());

		return "list_compliance.html";
	}

	@GetMapping({ "/compliance-svc/user-clear" })
	public String compClear(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		resetContext();

		return compFilter(model, Optional.empty(), Optional.empty());

	}

	@PostMapping({ "/compliance-svc/filter" })
	public String compFilter(Model model, @RequestParam("region") String filter_patch_region,
			@RequestParam(value = "patch") String filter_patch_name,
			@RequestParam(value = "compliant", required = false) String filter_patch_compliance,
			@RequestParam(value = "roll_no_name", required = false) String filter_roll_no_emp_name) {

		// this.filter_patch_release_date = filter_patch_release_date;
		// this.filter_patch_compliance_date = filter_patch_compliance_date;
		// this.filter_patch_time_zone = filter_patch_time_zone;
		this.filter_patch_region = filter_patch_region;
		this.filter_patch_name = filter_patch_name;
		this.filter_patch_compliance = filter_patch_compliance;
		this.filter_roll_no_emp_name = filter_roll_no_emp_name;

		return compFilter(model, Optional.empty(), Optional.empty());

	}

	@RequestMapping(value = "/compliance-svc/dashboard", method = RequestMethod.GET)
	public String dashboard(ModelMap model) throws JsonProcessingException {
		resetContext();

		// model.addAttribute("regions", getAllRegions());

		// model.addAttribute("org_roles", getAllOrgRoles());

		String filterArr[] = new String[] { filter_patch_region, filter_patch_name, filter_patch_compliance,
				filter_roll_no_emp_name };

		List<Employee> employees = complianceManagementService.findAllComplianceReports(filterArr);
		// String regionStatisticsJson = calculateRegionStatisticsAsJson(employees);
		// List<Employee> employees = convertMapListToEmployeeList(employeesList);
		String regionStatisticsJson = calculateRegionStatisticsAsJson(employees);

		model.addAttribute("regionStatisticsJson", regionStatisticsJson);
		ObjectMapper objectMapper = new ObjectMapper();
		String patchDataJson = objectMapper.writeValueAsString(employees);

		Map<String, List<Integer>> patchComplianceMap = new HashMap<>();

		for (Employee employee : employees) {
			String patchId = employee.getApplicable_patch_id();
			String patchCompliance = employee.getPatch_compliance();

			// Get the patch compliance counts list for the current patch ID
			List<Integer> patchCounts = patchComplianceMap.get(patchId);

			if (patchCounts == null) {
				// Initialize the patch compliance counts list if it doesn't exist
				patchCounts = new ArrayList<>();
				patchCounts.add(0); // Compliant count
				patchCounts.add(0); // Non-compliant count
			}

			if ("Compliant".equals(patchCompliance)) {
				// Increment the compliant count
				patchCounts.set(0, patchCounts.get(0) + 1);
			} else if ("Non-compliant".equals(patchCompliance)) {
				// Increment the non-compliant count
				patchCounts.set(1, patchCounts.get(1) + 1);
			}

			// Update the patch compliance counts list in the main map
			patchComplianceMap.put(patchId, patchCounts);
		}

		ObjectMapper objectMapper2 = new ObjectMapper();
		String patchDataJson2 = objectMapper.writeValueAsString(patchComplianceMap);

		// Add the JSON to the model
		model.addAttribute("patchDataJson", patchDataJson2);

		return "dashboard.html";
	}


	
	@GetMapping({ "/slot-svc/slot/add" }) 
	public String addSlot(ModelMap model) {
		model.addAttribute("regions", getAllRegions());
		model.addAttribute("time_zones", getTimeZones());

		return "add_slot.html";
	}

	@RequestMapping(value = "/slot-svc/slot/add", method = RequestMethod.POST)
	public String addSlot(@ModelAttribute SlotModel slotModel, ModelMap model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		getAllRegions();
			msg = slotManagementService.insertslot(slotModel);

		model.addAttribute("msg", msg);

		return "add_slot.html";

	}

	@GetMapping({"/slot-svc/slot/adds" }) 
	public String addSlots(ModelMap model) {
		model.addAttribute("regions", getAllRegions());
		model.addAttribute("time_zones", getTimeZones());

		return "add_slots.html";
	}

	@RequestMapping(value = "/slot-svc/slot/adds", method = RequestMethod.POST)
	public String addSlots(@ModelAttribute SlotModel slotModel, ModelMap model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		getAllRegions();
			msg = slotManagementService.insertslots(slotModel);

		model.addAttribute("msg", msg);

		return "add_slots.html";

	}
	
	@RequestMapping(value = "/slot-svc/slot/remove", method = RequestMethod.GET)
	public String slotRemove(ModelMap model) {
		model.addAttribute("regions", getAllRegions());
		model.addAttribute("time_zones", getTimeZones());

		return "remove_slot.html";
	}
	
	@RequestMapping(value = "/slot-svc/slot/remove", method = RequestMethod.POST)
	public String slotRemove(@RequestParam("slotIds") String slotIds, ModelMap model)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		msg = "";
		getAllRegions();
		List<String> slotIdList = new ArrayList<>();
		if (StringUtils.contains(slotIds, "-")) {
			slotIdList = extractedIds(slotIds);

		} else {

			slotIdList = Arrays.asList(slotIds.split("\\s*,\\s*"));
		}
		msg = slotManagementService.deleteslots(slotIdList);

		model.addAttribute("msg", msg);

		return "remove_slot.html";

	}

	private List<String> extractedIds(String slotIds) {
		int startId;
		int endId;
		int totalCount;
		int counter = 0;

		ArrayList<String> slotIdList = new ArrayList<>();
		startId = Integer.parseInt(StringUtils.substringBefore(slotIds, "-").trim());
		endId = Integer.parseInt(StringUtils.substringAfter(slotIds, "-").trim());
		totalCount = endId - startId;
		while (counter <= totalCount) {
			slotIdList.add(startId++ + "");
			counter ++;
		}
		return slotIdList;
	}
	

	private List<HashMap<String, Object>> getMapOfStringFormattedData(String data) {
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
		java.util.regex.Matcher matcher = pattern.matcher(data);

		List<HashMap<String, Object>> dataList = new ArrayList<>();

		while (matcher.find()) {
			String pair = matcher.group(1);
			String[] keyValue = pair.split(",\\s*");

			HashMap<String, Object> map = new HashMap<>();
			for (String item : keyValue) {
				String[] parts = item.split("=");
				if (parts.length == 2) {
					map.put(parts[0], parts[1]);
				}
			}

			dataList.add(map);
		}
		return dataList;
	}

	
	
	public static List<Map<String, Object>> calculateRegionStatistics(List<Employee> employees) {
		Map<String, Map<String, Integer>> regionData = new HashMap<>();

		for (Employee employee : employees) {
			String region = employee.getEmp_region();
			String patchCompliance = employee.getPatch_compliance();

			// Get the map for the region
			Map<String, Integer> regionMap = regionData.get(region);

			if (regionMap == null) {
				// If the region map doesn't exist, create a new one
				regionMap = new HashMap<>();
				regionData.put(region, regionMap);
			}

			// Update the counts in the region map
			regionMap.put(patchCompliance, regionMap.getOrDefault(patchCompliance, 0) + 1);
		}

		List<Map<String, Object>> result = new ArrayList<>();
		regionData.forEach((region, stats) -> {
			Map<String, Object> regionStat = new HashMap<>();
			regionStat.put("region", region);
			regionStat.put("compliant", stats.getOrDefault("Compliant", 0));
			regionStat.put("nonCompliant", stats.getOrDefault("Non-compliant", 0));
			result.add(regionStat);
		});

		return result;
	}

	public List<Employee> convertMapListToEmployeeList(List<Map<String, Object>> mapList) {
		List<Employee> employees = new ArrayList<>();

		for (Map<String, Object> map : mapList) {
			Employee employee = new Employee();
			employee.setRoll_no((int) map.get("roll_no"));
			employee.setEmp_name((String) map.get("emp_name"));
			employee.setEmp_region((String) map.get("emp_region"));
			employee.setApplicable_patch_id((String) map.get("applicable_patch_id"));
			employee.setPatch_compliance((String) map.get("patch_compliance"));
			employee.setDate_of_completion((String) map.get("date_of_completion"));
			employee.setE_mail_id((String) map.get("e_mail_id"));

			employees.add(employee);
		}

		return employees;
	}

	public static String calculateRegionStatisticsAsJson(List<Employee> employees) throws JsonProcessingException {
		List<Map<String, Object>> regionStatistics = calculateRegionStatistics(employees);

		// Convert the result to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(regionStatistics);
	}

	private void populateListStickyCompliance(Model model, String[] filterArr) {

		ArrayList<String> patches = new ArrayList<>(getAllPatches());
		ArrayList<String> compliance = new ArrayList<>(compliantOptions());

		// filterArr = new String[] { filter_patch_region, filter_patch_name,
		// filter_patch_compliance, filter_roll_no_emp_name};

		ArrayList<String> regions = new ArrayList<>();
		// regions.add("ALL");
		for (Region region : getAllRegions()) {
			regions.add(region.getName());
		}

		if (StringUtils.isNotBlank(filter_patch_region)) {
			int index = regions.indexOf(filter_patch_region);
			String indexZeroVal = regions.get(0);
			regions.set(0, filter_patch_region);
			regions.set(index, indexZeroVal);
		}

		if (StringUtils.isNotBlank(filter_patch_name)) {
			int index = patches.indexOf(filter_patch_name);
			String indexZeroVal = patches.get(0);
			patches.set(0, filter_patch_name);
			patches.set(index, indexZeroVal);
		}

		if (StringUtils.isNotBlank(filter_patch_compliance)) {
			int index = compliance.indexOf(filter_patch_compliance);
			String indexZeroVal = compliance.get(0);
			compliance.set(0, filter_patch_compliance);
			compliance.set(index, indexZeroVal);
		}

		model.addAttribute("regions", regions);
		model.addAttribute("patches", patches);
		model.addAttribute("compliantOptions", compliance);
		model.addAttribute("filter_roll_no_emp_name", filterArr[3]);

	}

	public ArrayList<String> compliantOptions() {

		ArrayList<String> options = new ArrayList<>();
		options.add("Any");
		options.add("Compliant");
		options.add("Non-Compliant");
		return options;

	}

	public String getRoles(Model model) {
		// Initialize roles HashSet with Roles objects
		roles = new TreeSet<>();

		getAllOrgRoles();
		// Convert the HashSet to a List
		List<Roles> rolesList = new ArrayList<>(roles);

		// Sort the list based on role_id using your compareTo method
		Collections.sort(rolesList);

		// Convert the sorted list back to a HashSet
		HashSet<Roles> sortedRolesHashSet = new HashSet<>(rolesList);

		model.addAttribute("roles", sortedRolesHashSet);
		return "list_roles.html";
	}

	@GetMapping
	public HashSet<Region> getAllRegions() {
		regions = new HashSet<>();
		regions.add(new Region("ALL"));
		Page<Regions> bookPage = userManagementService.findPaginatedRegion(PageRequest.of(0, 10));

		String data = bookPage.getContent().toString();
		String region_name, region_id;
		Region region;
		List<HashMap<String, Object>> dataList = getMapOfStringFormattedData(data);
		for (HashMap<String, Object> map : dataList) {
			region_name = (String) map.get("region_name");
			region_id = (String) map.get("region_id");
			region = new Region(region_name);
			regions.add(region);
		}
		return regions;
	}
	// Africa, Antarctica, Asia, Europe, North America, Oceania, South America

	public HashSet<String> getAllPatches() {
		patches = new LinkedHashSet<>();
		patches.add("ALL");
		Page<Patch> bookPage = patchManagementService.findPaginatedCatelog(PageRequest.of(0, 100));

		String data = bookPage.getContent().toString();
		String patch_name, region_id;
		Region region;
		List<HashMap<String, Object>> dataList = getMapOfStringFormattedData(data);
		for (HashMap<String, Object> map : dataList) {
			patch_name = (String) map.get("name");
			// region_id = (String) map.get("region_id");
			// region = new Region(region_name);
			patches.add(patch_name);
		}
		return patches;
	}

	public HashSet<String> getAllOrgRoles() {

		// Add regions to the list (you can load them from a database or another source)
		// org_roles.add("IT_ADMIN");
		// org_roles.add("Executive");
		// org_roles.add("Employee");
		roles = new TreeSet<>();
		org_roles = new HashSet<>();

		Page<Roles> bookPage = userManagementService.findPaginatedRoles(PageRequest.of(0, 100));

		String data = bookPage.getContent().toString();
		String region_name, region_id;
		Roles region;
		List<HashMap<String, Object>> dataList = getMapOfStringFormattedData(data);
		for (HashMap<String, Object> map : dataList) {
			region_name = (String) map.get("role_name");
			region_id = (String) map.get("role_id");
			org_roles.add(region_name);
			roles.add(new Roles(region_name, Integer.parseInt(region_id)));
		}

		return org_roles;
	}

	// @GetMapping
	public List<String> getTimeZones() {
		String[] availableIDs = TimeZone.getAvailableIDs();
		ArrayList<String> timeZonesList = new ArrayList<>();
		timeZonesList.add("ALL");
		timeZonesList.addAll(Arrays.asList(availableIDs));
		return timeZonesList;
	}
	
	
	public String getCurrentServiceId() {
		return currentServiceId;
	}

	public void setCurrentServiceId(String currentServiceId) {
		this.currentServiceId = currentServiceId;
	}

	public String getCurrentServiceName() {
		return currentServiceName;
	}

	public void setCurrentServiceName(String currentServiceName) {
		this.currentServiceName = currentServiceName;
	}

	private void populateList(Model model) {
		List<String> timeZonesList;
		if (org.springframework.util.CollectionUtils.isEmpty(filter_time_zones))
			timeZonesList = getTimeZones();
		else
			timeZonesList = filter_time_zones;

		LinkedHashSet<String> regionsSet = springBootJdbcController.findRegions();

		//ArrayList<String> timeZones = new ArrayList<>(timeZonesSet);

		ArrayList<String> regions = new ArrayList<>();
		//regions.add("ALL");
		for (Region region : getAllRegions()) {
			regions.add(region.getName());
		}

		if (StringUtils.isNotBlank(filter_time_zone)) {
			int index = timeZonesList.indexOf(filter_time_zone);
			String indexZeroVal = timeZonesList.get(0);
			timeZonesList.set(0, filter_time_zone);
			timeZonesList.set(index, indexZeroVal);
		}

		if (StringUtils.isNotBlank(filter_region)) {
			int index = regions.indexOf(filter_region);
			String indexZeroVal = regions.get(0);
			regions.set(0, filter_region);
			regions.set(index, indexZeroVal);
		}

		model.addAttribute("options", new LinkedHashSet<>(timeZonesList));
		model.addAttribute("regions", new LinkedHashSet<>(regions));
		model.addAttribute("patches", getAllPatches());
		model.addAttribute("empAssets", getEmpAsset());

		

	}

	private void resetContext() {
		filter_slot_end = null;
		filter_slot_start = null;
		filter_time_zone = null;
		filter_region = null;
		filter_is_booked = null;
		checkedMarkForAvailableFilterCheckbox = false;
		regions = new HashSet<>();
		org_roles = new HashSet<>();
		roles = new TreeSet<>();
		filter_patch_region = null;
		filter_patch_name = null;
		filter_patch_compliance = null;
		filter_roll_no_emp_name = null;
		filter_time_zones = new ArrayList<>();
	}

}
