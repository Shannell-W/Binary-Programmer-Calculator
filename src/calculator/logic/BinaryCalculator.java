package calculator.logic;

public class BinaryCalculator {
    public String addBinary(String a, String b) {
        validateBinary(a);
        validateBinary(b);

        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        StringBuilder result = new StringBuilder();

        while (i >= 0 || j >= 0 || carry == 1) {
            int bitA = (i >= 0) ? a.charAt(i) - '0' : 0;
            int bitB = (j >= 0) ? b.charAt(j) - '0' : 0;

            int sum = bitA + bitB + carry;
            result.append(sum % 2);
            carry = sum / 2;
            i--;
            j--;

        }
        return result.reverse() .toString();
    }
    public String subtractBinary(String a, String b) {
        validateBinary(a);
        validateBinary(b);

        int borrow = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        StringBuilder result = new StringBuilder();

        while (i >= 0) {
            int bitA = a.charAt(i) - '0' - borrow;
            int bitB = (j >= 0) ? b.charAt(j) - '0' : 0;

            if (bitA < bitB) {
                bitA += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.append(bitA - bitB);
            i--;
            j--;
        }
        return result.reverse().toString().replaceFirst("^0+(?!$)","");
    }

    public String multiplyBinary(String a, String b) {
        validateBinary(a);
        validateBinary(b);

        int num1 = Integer.parseInt(a, 2);
        int num2 = Integer.parseInt(b, 2);

        int product = num1 * num2;

        return Integer.toBinaryString(product);
    }
    public String divideBinary(String a, String b) {
        validateBinary(a);
        validateBinary(b);

        int divisor = Integer.parseInt(b,2);

        if (divisor == 0) {
            throw new ArithmeticException("cannot divide by zero");
        }

        int dividend = Integer.parseInt(a, 2);
        int quotient = dividend / divisor;

        return Integer.toBinaryString(quotient);

    }
    private void validateBinary(String input) {
        if (!input.matches("[01]+")) {
            throw new IllegalArgumentException("Invalid Binary number");
        }
    }
}
