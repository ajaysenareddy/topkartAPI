@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DealService dealService;

    public Order placeOrder(OrderRequest orderRequest) {
        Long dealId = orderRequest.getDealId();
        Long userId = orderRequest.getUserId();

        Deal deal = dealService.getDealById(dealId);
        if (deal.getAvailableUnits() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deal has no available units");
        }
        if (deal.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deal has expired");
        }

        deal.setAvailableUnits(deal.getAvailableUnits() - 1);
        dealService.saveDeal(deal);

        Order order = new Order();
        order.setDealId(dealId);
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id, Long userId) {
        return orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

}
