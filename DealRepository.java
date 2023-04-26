@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByAvailableUnitsGreaterThanAndExpiryTimeAfter(Integer availableUnits, LocalDateTime expiryTime);

}
