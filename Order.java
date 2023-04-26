@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dealId;

    private Long userId;

    private LocalDateTime orderTime;

    private OrderStatus status;

    // getters and setters
}
