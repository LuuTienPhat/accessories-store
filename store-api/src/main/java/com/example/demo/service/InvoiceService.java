package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.InvoiceType;

public interface InvoiceService {
	Invoice insertInvoice(Invoice Invoice);

	Invoice updateInvoice(Long id, Invoice newInvoice);

	void deleteInvoice(Long id);

	Invoice getInvoice(Long id);

	Invoice getInvoice(String word);
	
	List<Invoice> getInvoices();
	
	List<InvoiceType> getInvoiceTypes();
	
	List<Invoice> getInvoices(String keyword);
	
	Long count();
}