import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class LightningDeal {
    private String productName;
    private double actualPrice;
    private double finalPrice;
    private int totalUnits;
    private int availableUnits;
    private LocalDateTime expiryTime;

    // Constructors, getters, and setters

    // Check if the deal is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}

class TopkartAPI {
    private List<LightningDeal> deals;

    public TopkartAPI() {
        deals = new ArrayList<>();
    }

    // Admin actions

    public void createLightningDeal(LightningDeal deal) {
        deals.add(deal);
    }

    public void updateLightningDeal(LightningDeal deal) {
        // Find the deal by its product name and update its details
        for (LightningDeal existingDeal : deals) {
            if (existingDeal.getProductName().equals(deal.getProductName())) {
                existingDeal.setActualPrice(deal.getActualPrice());
                existingDeal.setFinalPrice(deal.getFinalPrice());
                existingDeal.setTotalUnits(deal.getTotalUnits());
                existingDeal.setAvailableUnits(deal.getAvailableUnits());
                existingDeal.setExpiryTime(deal.getExpiryTime());
                break;
            }
        }
    }

    public void approveOrder(String productName, int quantity) {
        // Find the deal by its product name and reduce the available units
        for (LightningDeal deal : deals) {
            if (deal.getProductName().equals(productName)) {
                int availableUnits = deal.getAvailableUnits();
                deal.setAvailableUnits(availableUnits - quantity);
                break;
            }
        }
    }

    // Customer actions

    public List<LightningDeal> getAvailableDeals() {
        List<LightningDeal> availableDeals = new ArrayList<>();
        for (LightningDeal deal : deals) {
            if (!deal.isExpired() && deal.getAvailableUnits() > 0) {
                availableDeals.add(deal);
            }
        }
        return availableDeals;
    }

    public void placeOrder(String productName, int quantity) {
        // Find the deal by its product name and check if it is available
        for (LightningDeal deal : deals) {
            if (deal.getProductName().equals(productName)) {
                if (!deal.isExpired() && deal.getAvailableUnits() >= quantity) {
                    // Place the order logic here
                    // ...
                    break;
                }
            }
        }
    }

    public String getOrderStatus(String orderNumber) {
        // Get order status logic here
        // ...
        return "Order status: In Progress";
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an instance of TopkartAPI
        TopkartAPI topkartAPI = new TopkartAPI();

        // Example usage of the API
        LightningDeal deal1 = new LightningDeal();
        deal1.setProductName("Product 1");
        deal1.setActualPrice(100.0);
        deal1.setFinalPrice(80.0);
        deal1.setTotalUnits(50);
        deal1.setAvailableUnits(50);
        deal1.setExpiryTime(LocalDateTime.now().plusHours(12));

        LightningDeal deal2 = new LightningDeal();
        deal2.setProductName("Product 2");
        deal2.setActualPrice(200.0);
        deal2.setFinalPrice(150.0);
        deal2.setTotalUnits(100);
        deal2.setAvailableUnits(100);
        deal2.setExpiryTime(LocalDateTime.now().plusHours(6));

        topkartAPI.createLightningDeal(deal1);
        topkartAPI.createLightningDeal(deal2);

        List<LightningDeal> availableDeals = topkartAPI.getAvailableDeals();
        for (LightningDeal deal : availableDeals) {
            System.out.println("Product Name: " + deal.getProductName());
            System.out.println("Final Price: " + deal.getFinalPrice());
            System.out.println("Available Units: " + deal.getAvailableUnits());
            System.out.println("Expiry Time: " + deal.getExpiryTime());
            System.out.println("----------------------");
        }

        topkartAPI.placeOrder("Product 1", 2);
        topkartAPI.placeOrder("Product 2", 3);

        String orderStatus = topkartAPI.getOrderStatus("ORDER123");
        System.out.println(orderStatus);
    }
}
