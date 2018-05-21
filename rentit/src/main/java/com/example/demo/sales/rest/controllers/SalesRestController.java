package com.example.demo.sales.rest.controllers;


import com.example.demo.common.utils.ExtendedLink;
import com.example.demo.inventory.application.dto.PlantInventoryEntryDTO;
import com.example.demo.inventory.application.dto.PlantInventoryItemDTO;
import com.example.demo.inventory.application.exceptions.PlantNotFoundException;
import com.example.demo.inventory.application.services.InventoryService;
import com.example.demo.sales.application.dto.POExtensionDTO;
import com.example.demo.sales.application.dto.PurchaseOrderDTO;
import com.example.demo.sales.application.services.SalesService;
import com.example.demo.sales.domain.model.POStatus;
import com.example.demo.sales.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;


@RestController
@CrossOrigin
@RequestMapping("/api/sales")
public class SalesRestController {



    @Autowired
    InventoryService inventoryService;

    @Autowired
    SalesService salesService;

    @GetMapping("/plants")
    public Resources<?> findAvailablePlants(
            @RequestParam(name = "name") String plantName,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return inventoryService.findAvailable(plantName.toLowerCase(), startDate, endDate);
    }


    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<PurchaseOrderDTO> fetchPurchaseOrder(@PathVariable("id") Long id){
        return salesService.findPurchaseOrder(id);
    }


    //-------------------------------------------------------------------------------------------

    /**
     * Get PO by Status [Mahir]
     * @return List of PurchaseOrderDTO's
     */
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public Resources<?> findPurchaseOrders() {
        return salesService.findAllPurchaseOrders();
    }




    // Old Manual Plant allocation
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//    /**
//     * Get List of Plants (PlantInventoryItems) within the given period [Mahir]
//     * @param id order id
//     * @return List of available plants in the given period for the PO entry
//     */
//    @GetMapping("/orders/{oid}/plants")
//    @ResponseStatus(HttpStatus.OK)
//    public List<PlantInventoryItemDTO> findAvailableItems(
//            @PathVariable("oid") String id){
//        List<PlantInventoryItemDTO> resources = salesService.findAvailablePOItems(id);
//        for(PlantInventoryItemDTO dto : resources)
//        {
//            Link acceptLink = linkTo(SalesRestController.class).slash("orders").slash(id).slash("plants").slash(dto.get_id()).slash("accept").withRel("accept");
//            dto.add(new ExtendedLink(acceptLink,POST));
//        }
//
//        return resources;
//    }


//    /**
//     * Allocate the given pid plant and get updated purchase order [Mahir]
//     * @param oid purchase order Id
//     * @param pid plantInventoryItem Id
//     * @return updated purchase order with Status=OPEN
//     */
//    @PostMapping("/orders/{oid}/plants/{pid}/accept")
//    @ResponseStatus(HttpStatus.CREATED)
//    public PurchaseOrderDTO allocatePlant(
//            @PathVariable("oid") String oid,
//            @PathVariable("pid") Long pid)
//    {
//        return salesService.allocatePlant(oid, pid);
//    }




//    @DeleteMapping("/orders/{oid}/accept")
//    @ResponseStatus(HttpStatus.OK)
//    public PurchaseOrderDTO allocatePlant(
//            @PathVariable("oid") String oid)
//    {
//        return salesService.rejectPurchaseOrder(oid);
//    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @PostMapping("/orders")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrderDTO partialPODTO) {
//        System.out.println(partialPODTO.toString());
        Resource<PurchaseOrderDTO> resource = salesService.createPO(partialPODTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", resource.getRequiredLink("self").getHref());

        return new ResponseEntity<>(
                resource,
                headers,
                HttpStatus.CREATED);

    }

    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String bindExceptionHandler(Exception ex) {
        return ex.getMessage();
    }



    //-------------------------------------------------------------------------------------------------------------------------


    @PutMapping("/orders/{id}/allocation")
    public Resource<?> allocatePlant(@PathVariable("id") Long id) {
        return salesService.allocatePlantToPurchaseOrder(id);
    }

    @DeleteMapping("/orders/{id}/allocation")
    public Resource<?> rejectPurchaseOrder(@PathVariable Long id){

        return salesService.rejectPurchaseOrder(id);
    }


    @DeleteMapping("/orders/{id}")
    public Resource<PurchaseOrderDTO> handleDeleteOnPurchaseOrder(@PathVariable("id") Long id) {
        return salesService.deletePurchaseOrder(id);
    }

    @GetMapping("/orders/{id}/extensions")
    public Resources<?> retrievePurchaseOrderExtensions(@PathVariable("id") Long id) {
        return salesService.fetchPurchaseOrderExtensions(id);
    }

    @PostMapping("/orders/{id}/extensions")
    public Resource<?> requestPurchaseOrderExtension(@RequestBody POExtensionDTO extensionDTO , @PathVariable("id") Long id) {
        System.out.println(extensionDTO.getEndDate());

        return salesService.requestPurchaseExtension(id,extensionDTO.getEndDate());
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> resubmitPurchaseOrder(@PathVariable("id") Long id, @RequestBody PurchaseOrderDTO order) {
        Resource<PurchaseOrderDTO> resource = salesService.updatePurchaseOrder(id,order);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", resource.getRequiredLink("self").getHref());

        return new ResponseEntity<>(
                resource,
                headers,
                HttpStatus.OK);

    }

    @PatchMapping("/orders/{id}/extensions")
    public Resource<?> acceptPurchaseOrderExtension(@PathVariable("id") Long id, @RequestBody PlantInventoryItemDTO plant) {
        // todo salesserver.acceptpoextention
        System.out.println(plant);
        System.out.println(id);
        return salesService.acceptPurchaseExtension(id,plant);
//        return null;
    }

    @DeleteMapping("/orders/{id}/extensions")
    public Resource<?> rejectPurchaseOrderExtension(@PathVariable("id") Long id) {
        return salesService.rejectPurchaseExtension(id);
    }


    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePlantNotFoundException(PlantNotFoundException ex){

    }



}