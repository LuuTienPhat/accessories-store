package com.example.demo.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.InvoiceDetail;
import com.example.demo.entities.InvoiceType;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.models.ResponseObject;
import com.example.demo.service.InvoiceService;
import com.example.demo.service.ProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {
	private final InvoiceService invoiceService;
	private final ProductService productService;

	@GetMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> allButLimit(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "search", required = false) String keyword) {
		ResponseEntity<ResponseObject> responseEntity = null;

		List<Invoice> result = null;
		;
		if (keyword != null) {
//			result = invoiceService.getInvoices(keyword);
		} else {
			result = invoiceService.getInvoices();
		}
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch invoices successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch invoices!", result));
		}

		return responseEntity;
	}

	@GetMapping(value = "/types")
	@ResponseBody
	public ResponseEntity<ResponseObject> allInvoiceTypes() {
		ResponseEntity<ResponseObject> responseEntity = null;

		List<InvoiceType> result = null;

		result = invoiceService.getInvoiceTypes();
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch invoice types successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch invoice types!", result));
		}

		return responseEntity;
	}

	@PostMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> newInvoice(@RequestBody FormInvoice formInvoice) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Invoice result = null;

		try {
			Invoice newInvoice = new Invoice();
			newInvoice.setId(formInvoice.getId());
			newInvoice.setCreatedDate(LocalDateTime.parse(formInvoice.getCreatedDate(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

			InvoiceType invoiceType = new InvoiceType();
			invoiceType.setId(formInvoice.getInvoiceTypeId());
			newInvoice.setInvoiceType(invoiceType);

			User user = new User();
			user.setId(formInvoice.getUserId());
			newInvoice.setUser(user);

			List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();
			for (FormInvoiceDetail formInvoiceDetail : formInvoice.getInvoiceDetails()) {
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				invoiceDetail.setInvoice(newInvoice);
				Product product = new Product();
				product.setId(formInvoiceDetail.getProductId());
				invoiceDetail.setProduct(product);
				invoiceDetail.setPrice(formInvoiceDetail.getPrice());
				invoiceDetail.setQuantity(formInvoiceDetail.getQuantity());
				invoiceDetails.add(invoiceDetail);
			}

			newInvoice.setInvoiceDetails(invoiceDetails);

			result = invoiceService.insertInvoice(newInvoice);

			for (InvoiceDetail invoiceDetail : invoiceDetails) {
				if (newInvoice.getInvoiceType().getId() == 1) {
					Product product = productService.getProduct(invoiceDetail.getProduct().getId());
					int oldQuant = product.getQuantity();
					int newQuant = oldQuant + invoiceDetail.getQuantity();
					product.setQuantity(newQuant);
					productService.updateProduct(product.getId(), product);
				}

				else if (newInvoice.getInvoiceType().getId() == 2) {
					Product product = productService.getProduct(invoiceDetail.getProduct().getId());
					int oldQuant = product.getQuantity();
					int newQuant = oldQuant - invoiceDetail.getQuantity();
					product.setQuantity(newQuant);
					productService.updateProduct(product.getId(), product);
				}
			}

//		FormInvoice result = formInvoice;
			if (result != null) {
				responseEntity = ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("ok", HttpStatus.OK.value(), "Insert invoice successfully!", result));
			}
		} catch (Exception e) {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to insert invoice!", null));
			e.printStackTrace();
		}
		return responseEntity;

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> one(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Invoice result = invoiceService.getInvoice(id);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Invoice Found", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Invoice Not found!", result));
		}

		return responseEntity;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> replaceInvoice(@RequestBody Invoice newInvoice, @PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		Invoice result = invoiceService.updateInvoice(id, newInvoice);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Update invoice successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to update invoice!", result));
		}

		return responseEntity;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> deleteEmployee(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		try {
			invoiceService.deleteInvoice(id);
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Delete invoice successfully!", null));
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to delete invoice!", null));
		}

		return responseEntity;
	}

	@GetMapping(value = "/count")
	@ResponseBody
	ResponseEntity<ResponseObject> count() {
		Long result = invoiceService.count();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "Invoice count", result));

	}
}

@Data
class FormInvoice {
	Long id;
	String createdDate;
	Long invoiceTypeId;
	Long userId;
	ArrayList<FormInvoiceDetail> invoiceDetails;
}

@Data
class FormInvoiceDetail {
	Long productId;
	Integer quantity;
	Float price;
}
