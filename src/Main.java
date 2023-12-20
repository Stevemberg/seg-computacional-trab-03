public class Main {
	public static void main(String[] args) {
		RSA rsa = new RSA();

		boolean r = rsa.testMillerRabin(rsa.getRandomNumber());
//		boolean r = rsa.testMillerRabin(BigInteger.valueOf(900093));
		System.out.println(r);

	}
}
