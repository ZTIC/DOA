package pt.ipp.estg.doa.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ipp.estg.doa.store.exception.InsufficientStockException;
import pt.ipp.estg.doa.store.exception.InvalidOperationException;
import pt.ipp.estg.doa.store.exception.ResourceNotFoundException;
import pt.ipp.estg.doa.store.model.dto.request.CreateEarringRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateNecklaceRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateRingRequest;
import pt.ipp.estg.doa.store.model.dto.response.EarringResponse;
import pt.ipp.estg.doa.store.model.dto.response.JewelryResponse;
import pt.ipp.estg.doa.store.model.dto.response.NecklaceResponse;
import pt.ipp.estg.doa.store.model.dto.response.RingResponse;
import pt.ipp.estg.doa.store.model.entity.*;
import pt.ipp.estg.doa.store.repository.EarringRepository;
import pt.ipp.estg.doa.store.repository.JewelryRepository;
import pt.ipp.estg.doa.store.repository.NecklaceRepository;
import pt.ipp.estg.doa.store.repository.RingRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JewelryServiceImpl implements JewelryService {
    private final JewelryRepository jewelryRepository;
    private final NecklaceRepository necklaceRepository;
    private final EarringRepository earringRepository;
    private final RingRepository ringRepository;

    public JewelryServiceImpl(JewelryRepository jewelryRepository,
                              NecklaceRepository necklaceRepository,
                              EarringRepository earringRepository,
                              RingRepository ringRepository) {
        this.jewelryRepository = jewelryRepository;
        this.necklaceRepository = necklaceRepository;
        this.earringRepository = earringRepository;
        this.ringRepository = ringRepository;
    }

    // ============= CREATE OPERATIONS =============

    @Override
    public NecklaceResponse createNecklace(CreateNecklaceRequest request) {
        // Validate unique constraints if needed
        if (jewelryRepository.existsByNameAndMaterial(request.getName(), request.getMaterial())) {
            throw new InvalidOperationException("create necklace",
                    "Jewelry with same name and material already exists");
        }

        Necklace necklace = new Necklace(
                request.getName(),
                request.getMaterial(),
                request.getWeight(),
                request.getPrice(),
                request.getStock(),
                request.getCategory(),
                request.getLength()
        );

        Necklace saved = necklaceRepository.save(necklace);
        return mapToNecklaceResponse(saved);
    }

    @Override
    public EarringResponse createEarring(CreateEarringRequest request) {
        if (jewelryRepository.existsByNameAndMaterial(request.getName(), request.getMaterial())) {
            throw new InvalidOperationException("create earring",
                    "Jewelry with same name and material already exists");
        }

        Earring earring = new Earring(
                request.getName(),
                request.getMaterial(),
                request.getWeight(),
                request.getPrice(),
                request.getStock(),
                request.getCategory(),
                request.getClaspType()
        );

        Earring saved = earringRepository.save(earring);
        return mapToEarringResponse(saved);
    }

    @Override
    public RingResponse createRing(CreateRingRequest request) {
        if (jewelryRepository.existsByNameAndMaterial(request.getName(), request.getMaterial())) {
            throw new InvalidOperationException("create ring",
                    "Jewelry with same name and material already exists");
        }

        Ring ring = new Ring(
                request.getName(),
                request.getMaterial(),
                request.getWeight(),
                request.getPrice(),
                request.getStock(),
                request.getCategory(),
                request.getSize()
        );

        Ring saved = ringRepository.save(ring);
        return mapToRingResponse(saved);
    }

    // ============= READ OPERATIONS =============

    @Override
    public JewelryResponse getJewelryById(Long id) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));
        return mapToJewelryResponse(jewelry);
    }

    @Override
    public List<JewelryResponse> getAllJewelry() {
        return jewelryRepository.findAll()
                .stream()
                .map(this::mapToJewelryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JewelryResponse> getJewelryByType(JewelryType type) {
        return jewelryRepository.findByType(type)
                .stream()
                .map(this::mapToJewelryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JewelryResponse> getJewelryByCategory(Category category) {
        return jewelryRepository.findByCategory(category)
                .stream()
                .map(this::mapToJewelryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JewelryResponse> getInStockJewelry() {
        return jewelryRepository.findInStock()
                .stream()
                .map(this::mapToJewelryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JewelryResponse> getOutOfStockJewelry() {
        return jewelryRepository.findOutOfStock()
                .stream()
                .map(this::mapToJewelryResponse)
                .collect(Collectors.toList());
    }

    // ============= UPDATE OPERATIONS =============

    @Override
    public JewelryResponse updatePrice(Long id, BigDecimal newPrice) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));

        jewelry.updatePrice(newPrice);
        Jewelry updated = jewelryRepository.save(jewelry);

        return mapToJewelryResponse(updated);
    }

    @Override
    public JewelryResponse updateStock(Long id, Integer newStock) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));

        if (newStock < 0) {
            throw new InvalidOperationException("update stock", "Stock cannot be negative");
        }

        jewelry.setStock(newStock);
        Jewelry updated = jewelryRepository.save(jewelry);

        return mapToJewelryResponse(updated);
    }

    // ============= DELETE OPERATIONS =============

    @Override
    public void deleteJewelry(Long id) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));

        // Check if jewelry can be deleted (e.g., not in any orders)
        // This will be implemented when Order module is created

        jewelryRepository.delete(jewelry);
    }

    // ============= BUSINESS OPERATIONS =============

    @Override
    public JewelryResponse decreaseStock(Long id, int quantity) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));

        if (!jewelry.hasSufficientStock(quantity)) {
            throw new InsufficientStockException(
                    jewelry.getName(),
                    jewelry.getId(),
                    jewelry.getStock(),
                    quantity
            );
        }

        jewelry.decreaseStock(quantity);
        Jewelry updated = jewelryRepository.save(jewelry);

        return mapToJewelryResponse(updated);
    }

    @Override
    public JewelryResponse increaseStock(Long id, int quantity) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));

        jewelry.increaseStock(quantity);
        Jewelry updated = jewelryRepository.save(jewelry);

        return mapToJewelryResponse(updated);
    }

    @Override
    public boolean checkAvailability(Long id, int requestedQuantity) {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id", id));

        return jewelry.hasSufficientStock(requestedQuantity);
    }

    // ============= SPECIFIC TYPE OPERATIONS =============

    @Override
    public List<NecklaceResponse> getAllNecklaces() {
        return necklaceRepository.findAll()
                .stream()
                .map(this::mapToNecklaceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EarringResponse> getAllEarrings() {
        return earringRepository.findAll()
                .stream()
                .map(this::mapToEarringResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RingResponse> getAllRings() {
        return ringRepository.findAll()
                .stream()
                .map(this::mapToRingResponse)
                .collect(Collectors.toList());
    }

    // ============= STATISTICS =============

    @Override
    public BigDecimal getTotalInventoryValue() {
        return jewelryRepository.getTotalInventoryValue();
    }

    @Override
    public long getTotalItemsInStock() {
        return jewelryRepository.findInStock().stream()
                .mapToInt(Jewelry::getStock)
                .sum();
    }

    // ============= MAPPING METHODS =============

    private JewelryResponse mapToJewelryResponse(Jewelry jewelry) {
        if (jewelry instanceof Necklace) {
            return mapToNecklaceResponse((Necklace) jewelry);
        } else if (jewelry instanceof Earring) {
            return mapToEarringResponse((Earring) jewelry);
        } else if (jewelry instanceof Ring) {
            return mapToRingResponse((Ring) jewelry);
        } else {
            throw new IllegalArgumentException("Unknown jewelry type: " + jewelry.getClass());
        }
    }

    private NecklaceResponse mapToNecklaceResponse(Necklace necklace) {
        return new NecklaceResponse(
                necklace.getId(),
                necklace.getName(),
                necklace.getType(),
                necklace.getMaterial(),
                necklace.getWeight(),
                necklace.getPrice(),
                necklace.getStock(),
                necklace.getCategory(),
                necklace.getLength()
        );
    }

    private EarringResponse mapToEarringResponse(Earring earring) {
        return new EarringResponse(
                earring.getId(),
                earring.getName(),
                earring.getType(),
                earring.getMaterial(),
                earring.getWeight(),
                earring.getPrice(),
                earring.getStock(),
                earring.getCategory(),
                earring.getClaspType()
        );
    }

    private RingResponse mapToRingResponse(Ring ring) {
        return new RingResponse(
                ring.getId(),
                ring.getName(),
                ring.getType(),
                ring.getMaterial(),
                ring.getWeight(),
                ring.getPrice(),
                ring.getStock(),
                ring.getCategory(),
                ring.getSize()
        );
    }
}
