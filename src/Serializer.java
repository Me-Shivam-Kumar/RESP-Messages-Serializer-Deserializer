import java.util.ArrayList;
import java.util.List;

public class Serializer{
	
	public byte[] serialize(Object obj) {
		if(obj instanceof Integer) {
			return String.format(":%s\r\n",obj).getBytes();
		}else if(obj instanceof String){
			return String.format("+%s\r\n", obj).getBytes();
		}else if(obj instanceof List) {
			List<byte[]> elements=new ArrayList<>();
			for(Object item:(List<?>)obj) {
				elements.add(serialize(item));
			}
			StringBuilder arrayResp=new StringBuilder("*"+elements.size()+"\r\n");
			for(byte[]element : elements) {
				arrayResp.append(new String(element));
			}
			return arrayResp.toString().getBytes();
		}else if(obj instanceof byte[]){
			String content=new String((byte[])obj);
			return String.format("$%l\r\n%s\r\n", content.length(),content).getBytes();
		}else {
			throw new IllegalArgumentException("Unsupported Data Type");
		}
	}
}
