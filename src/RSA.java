import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
	private static final int BITLEN = 100;
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.TWO;

	public BigInteger getRandomNumber() {
		SecureRandom sr = new SecureRandom();
//		return new BigInteger(BITLEN, sr).multiply(TWO).add(ONE);
		return new BigInteger(BITLEN, 100, sr);
	}

	public boolean testMillerRabin(BigInteger number) {
		SecureRandom sr = new SecureRandom();
		BigInteger previous = number.subtract(ONE);
		BigInteger rest = previous;
		int power = 0;
		int k = 8;

		// Procura m e w para satisfazer (number - 1) = (2^w) * m
		System.out.println(number);
		while (rest.mod(TWO).equals(ZERO)) {
			rest = rest.divide(TWO);
			power++;
		}

		for (int i = 0; i < k; i++) {
			BigInteger aux = new BigInteger(number.bitLength() - 1, sr);
			aux = aux.equals(ZERO) || aux.equals(ONE) ? TWO : aux;

			BigInteger a = aux.modPow(rest, number);

			if (!a.equals(ONE) && !a.equals(previous)) {
				boolean auxChecker = true;
				for (int r = 0; r < power; r++) {
					a = a.modPow(TWO, number);
					if (a.equals(ONE))
						return false;
					if (a.equals(previous)) {
						auxChecker = false;
						break;
					}
				}
				if (auxChecker)
					return false;
			}
		}
		return true;
	}
}
