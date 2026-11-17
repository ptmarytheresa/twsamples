# Modern Java Features – Hotel & Restaurant Examples

Every file stands alone and runs directly with JDK 25 (no build tool, no preview flags):

```bash
java jdk09-17/VarRoomBooking.java
```

| JDK | Feature | File | Business flow |
|-----|---------|------|---------------|
| 10/11 | `var` | `jdk09-17/VarRoomBooking.java` | Group available rooms, price a 4-night stay with city tax |
| 14 | Switch expressions | `jdk09-17/SwitchExpressionMealPricing.java` | Meal-plan surcharges, happy-hour discounts by day/hour |
| 15 | Text blocks | `jdk09-17/TextBlockBookingEmail.java` | Confirmation e-mail, channel-manager JSON, inventory SQL |
| 16 | Records | `jdk09-17/RecordGuestInvoice.java` | Validated order lines, invoice with service charge and tax |
| 17 | Sealed classes | `jdk09-17/SealedPaymentMethods.java` | Closed set of checkout payment methods, fees, settlement days |
| 16 | Pattern matching for `instanceof` | `jdk09-17/InstanceofPatternLoyalty.java` | Check-in perks by loyalty tier / corporate / walk-in |
| 21 | Virtual threads | `jdk21/VirtualThreadsOrderProcessing.java` | 1,000 rush-hour delivery orders: platform pool vs virtual threads |
| 21 | Record patterns | `jdk21/RecordPatternsDeliveryFee.java` | Dine-in / takeaway / delivery charges using nested address data |
| 21 | Pattern matching for `switch` | `jdk21/SwitchPatternGuestRequests.java` | Route guest-app requests with `when` guards and `case null` |
| 21 | Sequenced collections | `jdk21/SequencedCollectionsWaitlist.java` | Restaurant waitlist, VIP to front, check-ins, order history |
| 22 | Unnamed variables `_` | `jdk22-25/UnnamedVariablesDailyReport.java` | End-of-day report: covers, delivery revenue, tip pool |
| 25 | Compact source files & instance `main` | `jdk22-25/KitchenTicketPrinter.java` | Script printing kitchen tickets per station |
| 25 | Flexible constructor bodies | `jdk22-25/FlexibleConstructorReservation.java` | Validate group bookings and compute the deposit before `super(...)` |
| 25 | Module import declarations | `jdk22-25/ModuleImportMenuAnalytics.java` | Top dishes, slowest dish, revenue per weekday |

The JDK 9–17 files also run on JDK 17 and the JDK 21 files on JDK 21.
