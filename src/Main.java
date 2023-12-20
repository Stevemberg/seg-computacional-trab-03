import java.math.BigInteger;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		RSA rsa = new RSA();
		Util u = new Util();
		ArrayList<BigInteger> array;

		String msg = "Stevemberg12@#";
		rsa.calculateKeys();
		array = rsa.encript(msg);
		rsa.decript(array);

	}
}
