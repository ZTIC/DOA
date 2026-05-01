package pt.ipp.estg.doa.store.service;

import pt.ipp.estg.doa.store.model.dto.request.CreateEarringRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateNecklaceRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateRingRequest;
import pt.ipp.estg.doa.store.model.dto.response.EarringResponse;
import pt.ipp.estg.doa.store.model.dto.response.JewelryResponse;
import pt.ipp.estg.doa.store.model.dto.response.NecklaceResponse;
import pt.ipp.estg.doa.store.model.dto.response.RingResponse;
import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.JewelryType;

import java.math.BigDecimal;
import java.util.List;

public interface JewelryService {
    // Create operations
    NecklaceResponse createNecklace(CreateNecklaceRequest request);
    EarringResponse createEarring(CreateEarringRequest request);
    RingResponse createRing(CreateRingRequest request);

    // Read operations
    JewelryResponse getJewelryById(Long id);
    List<JewelryResponse> getAllJewelry();
    List<JewelryResponse> getJewelryByType(JewelryType type);
    List<JewelryResponse> getJewelryByCategory(Category category);
    List<JewelryResponse> getInStockJewelry();
    List<JewelryResponse> getOutOfStockJewelry();

    // Update operations
    JewelryResponse updatePrice(Long id, BigDecimal newPrice);
    JewelryResponse updateStock(Long id, Integer newStock);

    // Delete operations
    void deleteJewelry(Long id);

    // Business operations
    JewelryResponse decreaseStock(Long id, int quantity);
    JewelryResponse increaseStock(Long id, int quantity);
    boolean checkAvailability(Long id, int requestedQuantity);

    // Specific type operations
    List<NecklaceResponse> getAllNecklaces();
    List<EarringResponse> getAllEarrings();
    List<RingResponse> getAllRings();

    // Statistics
    BigDecimal getTotalInventoryValue();
    long getTotalItemsInStock();

}
