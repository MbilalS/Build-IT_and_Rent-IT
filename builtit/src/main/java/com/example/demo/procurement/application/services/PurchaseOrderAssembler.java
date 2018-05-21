package com.example.demo.procurement.application.services;


import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.domain.model.Employee;
import com.example.demo.procurement.domain.model.PurchaseOrder;
import com.example.demo.procurement.rest.controller.EmployeeRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class PurchaseOrderAssembler {


    public Resource<PurchaseOrderDTO> toResource(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) {
            return null;
        }
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.set_id(purchaseOrder.getId());
        dto.setHref(purchaseOrder.getHref());
        return new Resource<>(
                dto
        );
    }
}
