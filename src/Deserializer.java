import java.util.ArrayList;
import java.util.List;

public class Deserializer {
	
	public Object deserialize(byte[] inpu) {
		char fir=(char)inpu[0];
		String content=new String(inpu);
		switch(fir) {
		case '+':
			return deserializeSimpleString(content);
		case ':':
			return deserializeIntegers(content);
		case '$':
			return deserializeBulkStrings(content);
		case '*':
			return deserializeArrays(content);
		default:
			 throw new IllegalArgumentException("Unsupported RESP Type");		}
	}

	private Object deserializeArrays(String inp) {
		int elementsCount = Integer.parseInt(inp.substring(1, inp.indexOf("\r\n")));
        List<Object> elements = new ArrayList<>();
        int currentIndex = inp.indexOf("\r\n") + 2;
        for (int i = 0; i < elementsCount; i++) {
            Object element = deserialize(inp.substring(currentIndex).getBytes());
            elements.add(element);
            currentIndex += inp.indexOf("\r\n", currentIndex) + 2;
        }
        return elements;
	}

	private Object deserializeBulkStrings(String inp) {
		int length = Integer.parseInt(inp.substring(1, inp.indexOf("\r\n")));
        if (length == -1) {
            return null;
        }
        String bulkStringContent = inp.substring(inp.indexOf("\r\n") + 2, inp.length() - 2);
        return bulkStringContent.getBytes();
	}

	private Object deserializeIntegers(String inp) {
		return Integer.parseInt(inp.substring(1,inp.length()-2));
	}
	
	private String deserializeSimpleString(String inp) {
		return inp.substring(1,inp.length()-2);
	}
	
}
