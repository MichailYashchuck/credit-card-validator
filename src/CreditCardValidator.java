import java.util.*;
import java.io.*;

public class CreditCardValidator
{
    public static final int INVALID          = -1;
    public static final int VISA             = 0;
    public static final int MASTERCARD       = 1;
    public static final int AMERICAN_EXPRESS = 2;
    public static final int Discover         = 3;
    public static final int DINERS_CLUB      = 4;

    private static final String [] cardNames =
            {       "Visa" ,
                    "Mastercard",
                    "American Express",
                    "Discover",
                    "Diner's CLub",
            };

    public static boolean ValidCreditCard(String number)
            throws Exception
    {
        int Card;
        if ( (Card = getCard(number)) != -1)
            return ValidationCreditCard(number);
        return false;
    }

    public static int getCard(String number)
    {
        int valid = INVALID;

        String digit1 = number.substring(0,1);
        String digit2 = number.substring(0,2);
        String digit3 = number.substring(0,3);
        String digit4 = number.substring(0,4);

        if (Number(number))
        {

            if (digit1.equals("4"))
            {
                if (number.length() == 13 || number.length() == 16)
                    valid = VISA;
            }

            else if (digit2.compareTo("51")>=0 && digit2.compareTo("55")<=0)
            {
                if (number.length() == 16)
                    valid = MASTERCARD;
            }

            else if (digit2.equals("34") || digit2.equals("37"))
            {
                if (number.length() == 15)
                    valid = AMERICAN_EXPRESS;
            }

            else if (digit4.equals("6011") || digit4.equals("65"))
            {
                if (number.length() == 16)
                    valid = Discover;
            }

            else if (digit2.equals("36") || digit2.equals("38") ||
                    (digit3.compareTo("300")>=0 && digit3.compareTo("305")<=0))
            {
                if (number.length() == 14)
                    valid = DINERS_CLUB;
            }
        }
        return valid;


    }

    public static boolean Number(String n)
    {
        try
        {
            double d = Double.valueOf(n).doubleValue();
            return true;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String getCardName(int id) {
        return (id > -1 && id < cardNames.length ? cardNames[id] : "");
    }

    public static boolean ValidationCreditCard(String n)
    {
        try
        {

            int j = n.length();

            String [] s1 = new String[j];
            for (int i=0; i < n.length(); i++) s1[i] = "" + n.charAt(i);

            int checksum = 0;

            for (int i=s1.length-1; i >= 0; i-= 2)
            {
                int k = 0;

                if (i > 0)
                {
                    k = Integer.valueOf(s1[i-1]).intValue() * 2;
                    if (k > 9) {
                        String s = "" + k;
                        k = Integer.valueOf(s.substring(0,1)).intValue() +
                                Integer.valueOf(s.substring(1)).intValue();
                    }
                    checksum += Integer.valueOf(s1[i]).intValue() + k;
                }
                else
                    checksum += Integer.valueOf(s1[0]).intValue();
            }
            return ((checksum % 10) == 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String args[]) throws Exception
    {
        String aCard = "";

        if (args.length > 0)
            aCard = args[0];
        else {
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введите номер карты : ");
            aCard = input.readLine();
        }
        if (getCard(aCard) > -1)
        {
            System.out.println("Эта карта поддерживается.");
            System.out.println("Это " + getCardName(getCard(aCard)));
            System.out.println("Ваш номер карты: " + aCard + ", проверка прошла" + (ValidCreditCard(aCard)?" успешно.":" неудачно."));
        }
        else
        {
            System.out.println("Эта карта не поддерживается или её не существует!");
            System.out.println("Проверьте чтобы номер карты состоял только из цифр.");
            System.out.println("Проверьте чтобы длинна номера была не больше 16");
        }
    }
}