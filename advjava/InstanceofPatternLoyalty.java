import java.util.List;

/**
 * FEATURE: pattern matching for instanceof (JDK 16)
 *   - test the type AND bind a variable in one step
 *   - no explicit cast
 *   - the bound variable can be used in the same condition (&&)
 *
 * BUSINESS FLOW (Hotel check-in welcome perks):
 *   At check-in, the system decides perks depending on who the guest is:
 *   loyalty member (by tier), corporate guest, or walk-in.
 *
 * Run: java InstanceofPatternLoyalty.java
 */
public class InstanceofPatternLoyalty {

    static class Guest {
        final String fullName;
        Guest(String fullName) { this.fullName = fullName; }
    }

    static class LoyaltyMember extends Guest {
        final String tier;          // SILVER, GOLD, PLATINUM
        final int nightsThisYear;
        LoyaltyMember(String fullName, String tier, int nightsThisYear) {
            super(fullName);
            this.tier = tier;
            this.nightsThisYear = nightsThisYear;
        }
    }

    static class CorporateGuest extends Guest {
        final String companyName;
        final boolean hasNegotiatedRate;
        CorporateGuest(String fullName, String companyName, boolean hasNegotiatedRate) {
            super(fullName);
            this.companyName = companyName;
            this.hasNegotiatedRate = hasNegotiatedRate;
        }
    }

    static class WalkInGuest extends Guest {
        WalkInGuest(String fullName) { super(fullName); }
    }

    public static void main(String[] args) {
        List<Guest> arrivalsToday = List.of(
                new LoyaltyMember("Arjun Mehta", "PLATINUM", 48),
                new LoyaltyMember("Sara Lopez", "GOLD", 12),
                new LoyaltyMember("Tom Becker", "SILVER", 3),
                new CorporateGuest("Lena Kim", "Globex Ltd", true),
                new WalkInGuest("John Doe"));

        System.out.println("=== Check-in welcome perks ===");
        for (Guest guest : arrivalsToday) {
            System.out.printf("%-12s -> %s%n", guest.fullName, decideWelcomePerk(guest));
        }
    }

    static String decideWelcomePerk(Object guest) {
        // OLD WAY:
        // if (guest instanceof LoyaltyMember) {
        //     LoyaltyMember member = (LoyaltyMember) guest;
        //     ...

        if (guest instanceof LoyaltyMember member && member.tier.equals("PLATINUM")) {
            return "Free suite upgrade + lounge access (" + member.nightsThisYear + " nights this year)";
        }
        if (guest instanceof LoyaltyMember member && member.nightsThisYear >= 10) {
            return member.tier + ": free breakfast + late checkout";
        }
        if (guest instanceof LoyaltyMember member) {
            return member.tier + ": welcome drink";
        }
        if (guest instanceof CorporateGuest corporate && corporate.hasNegotiatedRate) {
            return "Direct billing to " + corporate.companyName + " + free Wi-Fi";
        }
        if (!(guest instanceof WalkInGuest walkIn)) {
            return "Standard welcome";
        }
        // 'walkIn' is in scope here because the negative check above returned early
        return "Offer loyalty sign-up to " + walkIn.fullName + " (10% off tonight)";
    }
}
