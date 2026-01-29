package model;
public interface ITrackable {
    String getTrackingInfo();
    default String getTrackingInfoPretty() {
        return "[TRACK] " + getTrackingInfo();
    }
    static void printTrackingHeader() {
        System.out.println("=== Tracking Summary ===");
    }
}
