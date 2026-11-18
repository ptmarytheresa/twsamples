package samplejava8;

interface iBooking
{
    int paymentProcess(String mode,int amount );
}
public class HotelLambda {
    static void main() {

        iBooking bookingonline=(mode,amt)->
        {
            if(mode.equals("netbanking"))
            return(amt - amt*10/100 + 18/100);
            else
                return (amt+18/100);
        };

        System.out.println( bookingonline.paymentProcess("cash",5000));
    }
}
