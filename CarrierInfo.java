

import java.util.LinkedList;

public class CarrierInfo {
    private String carrierId;
    private String carrierName;
    private String fleetType;
    private LinkedList<ShipmentInfo> shipments = new LinkedList<>();

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;   
    }

    public void setFleetType(String fleetType) {
        this.fleetType = fleetType;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getFleetType() {
        return fleetType;
    }

    public LinkedList<ShipmentInfo> getShipments() {
        return shipments;
    }
}