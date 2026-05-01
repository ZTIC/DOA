package pt.ipp.estg.doa.store.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipp.estg.doa.store.model.dto.request.*;
import pt.ipp.estg.doa.store.model.dto.response.EarringResponse;
import pt.ipp.estg.doa.store.model.dto.response.JewelryResponse;
import pt.ipp.estg.doa.store.model.dto.response.NecklaceResponse;
import pt.ipp.estg.doa.store.model.dto.response.RingResponse;
import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.JewelryType;
import pt.ipp.estg.doa.store.service.JewelryService;
import pt.ipp.estg.doa.store.service.JewelryServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/jewelry")
@CrossOrigin(origins = "*")
public class JewelryController {

    private final JewelryServiceImpl jewelryService;

    public JewelryController(JewelryServiceImpl jewelryService) {
        this.jewelryService = jewelryService;
    }

    // ============= NECKLACE ENDPOINTS =============

    @PostMapping("/necklace")
    public ResponseEntity<NecklaceResponse> createNecklace(
             @RequestBody @Valid CreateNecklaceRequest request) {
        NecklaceResponse response = jewelryService.createNecklace(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/necklaces")
    public ResponseEntity<List<NecklaceResponse>> getAllNecklaces() {
        List<NecklaceResponse> responses = jewelryService.getAllNecklaces();
        return ResponseEntity.ok(responses);
    }

    // ============= EARRING ENDPOINTS =============

    @PostMapping("/earring")
    public ResponseEntity<EarringResponse> createEarring(
            @Valid @RequestBody CreateEarringRequest request) {
        EarringResponse response = jewelryService.createEarring(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/earrings")
    public ResponseEntity<List<EarringResponse>> getAllEarrings() {
        List<EarringResponse> responses = jewelryService.getAllEarrings();
        return ResponseEntity.ok(responses);
    }

    // ============= RING ENDPOINTS =============

    @PostMapping("/ring")
    public ResponseEntity<RingResponse> createRing(
            @Valid @RequestBody CreateRingRequest request) {
        RingResponse response = jewelryService.createRing(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/rings")
    public ResponseEntity<List<RingResponse>> getAllRings() {
        List<RingResponse> responses = jewelryService.getAllRings();
        return ResponseEntity.ok(responses);
    }

    // ============= COMMON JEWELRY ENDPOINTS =============

    @GetMapping
    public ResponseEntity<List<JewelryResponse>> getAllJewelry() {
        List<JewelryResponse> responses = jewelryService.getAllJewelry();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JewelryResponse> getJewelryById(@PathVariable Long id) {
        JewelryResponse response = jewelryService.getJewelryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<JewelryResponse>> getJewelryByType(
            @PathVariable JewelryType type) {
        List<JewelryResponse> responses = jewelryService.getJewelryByType(type);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<JewelryResponse>> getJewelryByCategory(
            @PathVariable Category category) {
        List<JewelryResponse> responses = jewelryService.getJewelryByCategory(category);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<JewelryResponse>> getInStockJewelry() {
        List<JewelryResponse> responses = jewelryService.getInStockJewelry();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<JewelryResponse>> getOutOfStockJewelry() {
        List<JewelryResponse> responses = jewelryService.getOutOfStockJewelry();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<JewelryResponse> updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePriceRequest request) {
        JewelryResponse response = jewelryService.updatePrice(id, request.getPrice());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<JewelryResponse> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStockRequest request) {
        JewelryResponse response = jewelryService.updateStock(id, request.getStock());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<JewelryResponse> decreaseStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        JewelryResponse response = jewelryService.decreaseStock(id, quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/increase-stock")
    public ResponseEntity<JewelryResponse> increaseStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        JewelryResponse response = jewelryService.increaseStock(id, quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable Long id,
            @RequestParam int quantity) {
        boolean available = jewelryService.checkAvailability(id, quantity);
        return ResponseEntity.ok(available);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJewelry(@PathVariable Long id) {
        jewelryService.deleteJewelry(id);
        return ResponseEntity.noContent().build();
    }

    // ============= STATISTICS ENDPOINTS =============

    @GetMapping("/statistics/total-value")
    public ResponseEntity<BigDecimal> getTotalInventoryValue() {
        BigDecimal totalValue = jewelryService.getTotalInventoryValue();
        return ResponseEntity.ok(totalValue);
    }

    @GetMapping("/statistics/items-in-stock")
    public ResponseEntity<Long> getTotalItemsInStock() {
        long totalItems = jewelryService.getTotalItemsInStock();
        return ResponseEntity.ok(totalItems);
    }

}
