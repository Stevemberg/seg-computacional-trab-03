import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class RSA {
	public static final int BITLEN = 1024;
	public static final BigInteger ZERO = BigInteger.ZERO;
	public static final BigInteger ONE = BigInteger.ONE;
	public static final BigInteger TWO = BigInteger.TWO;

	BigInteger p;
	BigInteger q;
	BigInteger n;
	BigInteger z;
	BigInteger e;
	BigInteger d;

	public BigInteger getRandomNumber() {
		SecureRandom sr = new SecureRandom();
//		return new BigInteger(BITLEN, sr).multiply(TWO).add(ONE);
		return new BigInteger(BITLEN, 100, sr);
	}

	public boolean testMillerRabin(BigInteger number) {
		if (number.equals(ZERO))
			return false;

		SecureRandom sr = new SecureRandom();
		BigInteger previous = number.subtract(ONE);
		BigInteger rest = previous;
		int power = 0;
		int k = 8;

		// Procura m e w para satisfazer (number - 1) = (2^w) * m
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

	public BigInteger generatePrimeNumber() {
		BigInteger result = ZERO;
		while (!testMillerRabin(result))
			result = getRandomNumber();
		return result;
	}

	public void calculateKeys() {
		p = generatePrimeNumber();
		q = generatePrimeNumber();

		n = p.multiply(q);

		BigInteger factor1 = p.subtract(RSA.ONE);
		BigInteger factor2 = q.subtract(RSA.ONE);
		z = factor1.multiply(factor2);

		e = RSA.TWO.add(RSA.ONE);
		while (z.gcd(e).intValue() > 1) {
			e = e.add(RSA.TWO);
		}

		d = e.modInverse(z);
		System.out.println("p: " + p);
		System.out.println("q: " + q);
		System.out.println("n: " + n);
		System.out.println("z: " + z);
		System.out.println("e: " + e);
		System.out.println("d: " + d + "\n");
	}

	public ArrayList<BigInteger> encript(String str) {
		ArrayList<BigInteger> cipherText = new ArrayList<BigInteger>();
		byte[] characters = str.getBytes();

		for (int i = 0; i < characters.length; i++) {
			byte[] fakeArray = new byte[] { characters[i] };
			cipherText.add(new BigInteger(fakeArray).modPow(e, n));
		}

		System.out.println("Mensagem cifrada (por caracter): " + cipherText);
		System.out.println("Mensagem cifrada (só números): " + cipherText.toString().replaceAll(", ", "").substring(1));

		return cipherText;
	}

	public String decript(ArrayList<BigInteger> cipherText) {
		StringBuilder result = new StringBuilder("");
		for (BigInteger character : cipherText)
			result.append((char) character.modPow(d, n).intValue());

		System.out.println("Mensagem decifrada: " + result.toString());
		return result.toString();
	}
}
