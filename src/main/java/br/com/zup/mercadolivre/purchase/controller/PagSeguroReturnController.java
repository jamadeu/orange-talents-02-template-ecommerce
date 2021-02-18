package br.com.zup.mercadolivre.purchase.controller;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.purchase.dto.IncreaseRankingRequest;
import br.com.zup.mercadolivre.purchase.dto.NewInvoiceRequest;
import br.com.zup.mercadolivre.purchase.dto.ReturnGatewayRequest;
import br.com.zup.mercadolivre.purchase.model.Purchase;
import br.com.zup.mercadolivre.purchase.model.PurchaseStatus;
import br.com.zup.mercadolivre.purchase.model.Transaction;
import br.com.zup.mercadolivre.purchase.model.TransactionStatus;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
public class PagSeguroReturnController implements PaymentReturn {

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/return-payment/pagseguro/{id}")
    @Transactional
    @Override
    public ResponseEntity<?> processesPayment(@PathVariable("id") Long purchaseId, ReturnGatewayRequest request) {
        Purchase purchase = em.find(Purchase.class, purchaseId);
        if (request.getStatus().equals("ERRO")) {
            purchase.changeStatus(PurchaseStatus.FAIL);
            /*
             * send email to buyer
             **/
            return ResponseEntity.badRequest().body("Payment fail");
        }
        purchase.changeStatus(PurchaseStatus.SUCCESS);
        Transaction transactional = request.toModel(purchaseId, TransactionStatus.SUCCESS);
        em.persist(transactional);

        // Call invoice controller to generate the invoice
        NewInvoiceRequest newInvoiceRequest = new NewInvoiceRequest(purchaseId, purchase.getBuyer());
        new RestTemplate().exchange("http://localhost:8080/invoice",
                HttpMethod.POST,
                new HttpEntity<>(newInvoiceRequest, createJsonHeader()),
                ResponseEntity.class);

        // Call ranking controller to increase seller ranking
        Product product = em.find(Product.class, purchase.getProduct());
        IncreaseRankingRequest increaseRankingRequest = new IncreaseRankingRequest(purchaseId, product.getOwner().getId());
        new RestTemplate().exchange("http://localhost:8080/ranking",
                HttpMethod.POST,
                new HttpEntity<>(increaseRankingRequest, createJsonHeader()),
                ResponseEntity.class);

        /*
         * Send email to buyer to confirm the success
         **/

        return ResponseEntity.noContent().build();
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
