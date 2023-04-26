@Entity
@Table(name = "deals")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String productName;

    @DecimalMin(value = "0.01", message = "Actual price should not be less than 0.01")
    private BigDecimal actualPrice;

    @DecimalMin(value = "0.01", message = "Final price should not be less than 0.01")
    private BigDecimal finalPrice;

    @Min(value = 1, message = "Total units should be at least 1")
    private Integer totalUnits;

    @Min(value = 0, message = "Available units should be at least 0")
    private Integer availableUnits;

    @Future(message = "Expiry time should be in the future")
    private LocalDateTime expiryTime;

    // getters and setters
}
