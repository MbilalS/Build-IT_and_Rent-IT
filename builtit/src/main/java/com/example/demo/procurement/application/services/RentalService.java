package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.PurchaseOrderAcceptDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RentalService {
    @Autowired
    RestTemplate restTemplate;

    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderAcceptDTO poDTO) {
        ResponseEntity<PurchaseOrderDTO> result = restTemplate.postForEntity("http://localhost:8090/api/sales/orders", poDTO, PurchaseOrderDTO.class);

        if (result == null &&  result.getStatusCode() != HttpStatus.CREATED)
            return new PurchaseOrderDTO();
        PurchaseOrderDTO  po= new PurchaseOrderDTO();
        po.setHref(result.getHeaders().getLocation().toString());
        return po;
    }
}
