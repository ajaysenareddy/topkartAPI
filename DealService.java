@Service
public class DealService {

    @Autowired
    private DealRepository dealRepository;

    public List<Deal> getAvailableDeals() {
        LocalDateTime now = LocalDateTime.now();
        return dealRepository.findByAvailableUnitsGreaterThanAndExpiryTimeAfter(0, now);
    }

    public Deal getDealById(Long id) {
        return dealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deal not found"));
    }

}
