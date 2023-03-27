package com.example.demo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Invoice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	@Column(name = "date")
	/* @Temporal(TemporalType.DATE) */
//	private String date;
//	@Column(name = "time")
	/* @Temporal(TemporalType.TIMESTAMP) */
//	private String time;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "invoice_type_id")
	private InvoiceType invoiceType;

	@JsonManagedReference(value = "invoice_invoice_detail")
	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
	private List<InvoiceDetail> invoiceDetails;

	public static List<Invoice> getImportInvoices(List<Invoice> list) {
		List<Invoice> invoices = new ArrayList<Invoice>();
		for (Invoice invoice : list) {
			if (invoice.getInvoiceType().getId() == 1) {
				invoices.add(invoice);
			}
		}
		return invoices;
	}

	public static List<Invoice> getExportInvoices(List<Invoice> list) {
		List<Invoice> invoices = new ArrayList<Invoice>();
		for (Invoice invoice : list) {
			if (invoice.getInvoiceType().getId() == 2) {
				invoices.add(invoice);
			}
		}
		return invoices;
	}

	@JsonGetter
	private Long getUserId() {
		return user != null ? user.getId() : null;
 	}
//	public boolean hasProduct(String productId) {
//		for (InvoiceDetail invoiceDetail : invoiceDetails) {
//			if (invoiceDetail.getProduct().getId().equals(productId))
//				return true;
//		}
//		return false;
//	}

	// GET TOTAL QUANTITY OF PRODUCT IN INVOICE
//	public int getTotalQuantity() {
//		int totalQuantity = 0;
//		for (InvoiceDetail invoiceDetail : invoiceDetails) {
//			totalQuantity += invoiceDetail.getQuantity();
//		}
//		return totalQuantity;
//	}

	// GET TOTAL PRICE OF INVOICE
//	public float getTotalPrice() {
//		float totalPrice = 0;
//		for (InvoiceDetail invoiceDetail : invoiceDetails) {
//			totalPrice += (invoiceDetail.getPrice() * invoiceDetail.getQuantity());
//		}
//		return totalPrice;
//	}
//
//	public int getQuantity(String productId) {
//		for (InvoiceDetail invoiceDetail : invoiceDetails) {
//			if (invoiceDetail.getProduct().getId().equals(productId))
//				return invoiceDetail.getQuantity();
//		}
//		return 0;
//	}
//
//	public float getPrice(String productId) {
//		for (InvoiceDetail invoiceDetail : invoiceDetails) {
//			if (invoiceDetail.getProduct().getId().equals(productId))
//				return invoiceDetail.getPrice();
//		}
//		return 0;
//	}

}
