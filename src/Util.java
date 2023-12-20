public class Util {
	public byte[] convertStringToByte(String str) {
		byte[] aux = new byte[str.length()];
		for (int i = 0; i < str.length(); i++)
			aux[i] = (byte) str.charAt(i);
		return aux;
	}

//	public String convertIntToString(int n) {
//
//	}
}
