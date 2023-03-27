package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.InvoiceDetail;
import com.example.demo.entities.InvoiceType;
import com.example.demo.repo.InvoiceDetailRepo;
import com.example.demo.repo.InvoiceRepo;
import com.example.demo.repo.InvoiceTypeRepo;
import com.example.demo.service.InvoiceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
	private final InvoiceRepo invoiceRepo;
	private final InvoiceTypeRepo invoiceTypeRepo;
	private final InvoiceDetailRepo invoiceDetailRepo;

	@Override
	public Invoice insertInvoice(Invoice invoice) {
		Invoice result = null;
		try {
			log.info("Saving new invoice '{}' to the database", invoice.getId());
			result = invoiceRepo.save(invoice);
			
			for(InvoiceDetail invoiceDetail : invoice.getInvoiceDetails()) {
				invoiceDetailRepo.save(invoiceDetail);
			}
		} catch (Exception e) {
			log.info("Error while inserting invoice '{}' to the database", invoice.getId());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Invoice updateInvoice(Long id, Invoice newInvoice) {
		Invoice updatedInvoice = null;
		try {
			log.info("Updating invoice '{}' to the database", newInvoice.getId());
			updatedInvoice = invoiceRepo.findById(id).map(invoice -> {
				return invoiceRepo.save(invoice);
			}).orElseGet(() -> {
				newInvoice.setId(id);
				return invoiceRepo.save(newInvoice);
			});
		} catch (Exception e) {
			log.info("Error while updating invoice '{}' to the database", newInvoice.getId());
			e.printStackTrace();
		}

		return updatedInvoice;
	}

	@Override
	public void deleteInvoice(Long id) {
		invoiceRepo.deleteById(id);
	}

	@Override
	public Invoice getInvoice(Long id) {
		Invoice invoice = null;
		try {
			invoice = invoiceRepo.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoice;
	}

	@Override
	public Invoice getInvoice(String name) {
//		Invoice Invoice = invoiceRepo.findByName(name);
		return null;
	}

	@Override
	public List<Invoice> getInvoices() {
		return invoiceRepo.findAll();
	}

	@Override
	public Long count() {
		return invoiceRepo.count();
	}

	@Override
	public List<Invoice> getInvoices(String keyword) {
		// TODO Auto-generated method stub
//		return invoiceRepo.search(keyword);
		return null;
	}

	@Override
	public List<InvoiceType> getInvoiceTypes() {
		// TODO Auto-generated method stub
		return invoiceTypeRepo.findAll();
	}

//	@Override
//	public List<Invoice> getInvoices(int from, int limit) {
//		// TODO Auto-generated method stub
//		Page<Invoice> pages = invoiceRepo.findAll(PageRequest.of(from, limit));
//		return pages.toList();
//	}
}
