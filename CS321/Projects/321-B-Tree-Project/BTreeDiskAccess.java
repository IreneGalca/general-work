import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

public class BTreeDiskAccess {

    private static String bTreeFile="";
    private static int nodeSizeOnDisk;
    private static int offsetRootNode = 20;

    public static void setBTreeFile(String filename){
        bTreeFile = filename;
    }
    public static void setNodeSizeOnDisk(int degree){
		nodeSizeOnDisk = 4096; //I think length will be a multiple of 4096, depending on degree.  Sequence has a max size 64 bit long...
    }


	public static BTreeNode disk_read(int pointer) throws FileNotFoundException,IOException,ClassNotFoundException {
		// BTreeNode implements Serializable		
		// BtreeNode has array of pointers to offsets for childnodes	
        int pointerOffset = pointer * nodeSizeOnDisk;
		try(RandomAccessFile file = new RandomAccessFile(bTreeFile, "r")){
			byte[] buffer = new byte[nodeSizeOnDisk];
			file.seek(pointerOffset);	
            file.read(buffer, 0, nodeSizeOnDisk);
			file.close();
            
			BTreeNode node = (BTreeNode)convertFromBytes(buffer);
			return node;
		}
	}

	public static void diskWrite(int pointer, BTreeNode nodeToAdd ) throws FileNotFoundException,IOException {
        int pointerOffset = pointer * nodeSizeOnDisk;
		
		try (RandomAccessFile file = new RandomAccessFile(bTreeFile, "rw")){			
			 
			byte[] data = convertToBytes(nodeToAdd);
			file.seek(pointerOffset);
			file.write(data);
			file.write(new byte[nodeSizeOnDisk-data.length]);  //overwrite unused portion reserved for node.
			file.close();
		}		
		
	}

	//https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
	private static byte[] convertToBytes(Object object) throws IOException {		
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 ObjectOutput out = new ObjectOutputStream(bos)) {
			out.writeObject(object);
			return bos.toByteArray();
		} 
	}
	
	private static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			 ObjectInput in = new ObjectInputStream(bis)) {
			return in.readObject();
		} 
	}
	

}