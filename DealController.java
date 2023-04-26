@RestController
@RequestMapping("/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    @GetMapping
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = dealService.getAllDeals();
        return ResponseEntity.ok(deals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deal> getDealById(@PathVariable Long id) {
        Optional<Deal> deal = dealService.getDealById(id);
        if (deal.isPresent()) {
            return ResponseEntity.ok(deal.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {
        LocalDateTime expiryTime = deal.getExpiryTime();
        LocalDateTime currentTime = LocalDateTime.now();
        if (expiryTime.isBefore(currentTime)) {
            return ResponseEntity.badRequest().build();
        }
        Deal newDeal = dealService.createOrUpdateDeal(deal);
        return ResponseEntity.ok(newDeal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) {
        Optional<Deal> existingDeal = dealService.getDealById(id);
        if (existingDeal.isPresent()) {
            Deal updatedDeal = existingDeal.get();
            updatedDeal.setProductName(deal.getProductName());
            updatedDeal.setActualPrice(deal.getActualPrice());
            updatedDeal.setFinalPrice(deal.getFinalPrice());
            updatedDeal.setTotalUnits(deal.getTotalUnits());
            updatedDeal.setAvailableUnits(deal.getAvailableUnits());
            updatedDeal.setExpiryTime(deal.getExpiryTime());
            LocalDateTime expiryTime = updatedDeal.getExpiryTime();
            LocalDateTime currentTime = LocalDateTime.now();
            if (expiryTime.isBefore(currentTime)) {
                return ResponseEntity.badRequest().build();
            }
            Deal savedDeal = dealService.createOrUpdateDeal(updatedDeal);
            return ResponseEntity.ok(savedDeal);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        return ResponseEntity.ok().build();
    }
}
