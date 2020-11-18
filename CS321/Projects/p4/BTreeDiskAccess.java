/*************************************************
 *CS321:P4
 *Btree Project
 *@authors EmmaLytle, IreneGalca, AdriannaMickle 
 *************************************************/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class BTreeDiskAccess {

    private static String bTreeFile="";
    private static int nodeSizeOnDisk;
    private static int offsetMetaData = 20;  //allow area to store BTree info.
    private static ArrayList<Integer> dataFilePointers = new ArrayList<Integer>();
  

    
    public static void setBTreeFile(String filename){
        bTreeFile = filename;
	}
	
	@SuppressWarnings("rawtypes")
	public static int getOptimalDegree(){
		//Get overhead of BTreeNode structure.  Serializing entire object to disk.
		//Basically the size on disk has additional overhead because we are serializing the entire node.  
		//In general, serializing the node adds about 422 bytes.  Each degree 56bytes.
		//Since the BTreeNode object was having properties added/subtracted during dev
		
		BTreeNode nodea = new BTreeNode(1);  
		BTreeNode nodeb = new BTreeNode(2);

		int dataSize = 0;
		int serializationOverhead = 0;
		int optimalDegree = 15;
		nodea.fillNodeToMax();
		nodeb.fillNodeToMax();
		
        try {

            byte[] nodeaDisk = convertToBytes(nodea);
			byte[] nodebDisk = convertToBytes(nodeb);
			dataSize = nodebDisk.length - nodeaDisk.length;
			serializationOverhead = nodeaDisk.length - dataSize;
			
			optimalDegree = (4096-serializationOverhead )/dataSize;
            

        } catch (Exception e) {
        }
		if (optimalDegree <= 0 ){
			return 15; //default
		}
		else{
			return optimalDegree;
		}
        
    }

    @SuppressWarnings("rawtypes")
	public static void setNodeSizeOnDisk(int degree){
		//Get overhead of BTreeNode structure.  Serializing entire object to disk.
		//Basically the size on disk has additional overhead because we are serializing the entire node.  
		//In general, serializing the node adds about 422 bytes.  Each degree 56bytes.
		//Since the BTreeNode object was having properties added/subtracted during dev

		BTreeNode overhead = new BTreeNode(degree);  
        overhead.fillNodeToMax();
		int totalsize = 0; 
        try {

            byte[] objectOverhead = convertToBytes(overhead);
            totalsize += objectOverhead.length;

        } catch (Exception e) {
        }

        nodeSizeOnDisk = totalsize; //Use totalsize, this won't be correct until removal of BtreeNode[];
        //nodeSizeOnDisk = 10000;
    }


	@SuppressWarnings("rawtypes")
	public static BTreeNode disk_read(int dataBlockNumber) throws FileNotFoundException,IOException,ClassNotFoundException {
		// BTreeNode implements Serializable		
        int dataOffset = (dataBlockNumber * nodeSizeOnDisk) + offsetMetaData;

		try(RandomAccessFile file = new RandomAccessFile(bTreeFile, "r")){
			byte[] buffer = new byte[nodeSizeOnDisk];
			file.seek(dataOffset);	
            file.read(buffer, 0, nodeSizeOnDisk);
            BTreeNode node = (BTreeNode)convertFromBytes(buffer);
			file.close();
            return node;
            
		}
	}

	@SuppressWarnings("rawtypes")
	public static void diskWrite(int dataBlockNumber, BTreeNode nodeToWrite ) throws FileNotFoundException,IOException {
		byte[] data;
        int dataOffset = (dataBlockNumber * nodeSizeOnDisk) + offsetMetaData;

		try (RandomAccessFile file = new RandomAccessFile(bTreeFile, "rw")){			
            if(nodeToWrite == null){
                data = new byte[nodeSizeOnDisk];
            }
            else{
                data  = convertToBytes(nodeToWrite);
            }
          
			file.seek(dataOffset);
			file.write(data);
			file.write(new byte[nodeSizeOnDisk-data.length]);  //overwrite unused portion reserved for node.
			file.close();
		}		
		
	}

	public static int diskReadBTreeMetaData(int offset)  throws FileNotFoundException,IOException {
		int val = 0;
		try (RandomAccessFile file = new RandomAccessFile(bTreeFile, "rw")){			
            file.seek(offset);
			val = file.readInt();
			
			file.close();
			return val;
		}		

	}



	public static void diskWriteBTreeMetaData(int degree, int sequenceLen, int rootDataBlockNumber)  throws FileNotFoundException,IOException {
		
		try (RandomAccessFile file = new RandomAccessFile(bTreeFile, "rw")){			
            file.seek(0);
			file.writeInt(degree);
			file.writeInt(sequenceLen);
			file.writeInt(rootDataBlockNumber);
			
			
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
	



    public static int getNextDataBlockNumber(){
		// Collections.sort(dataFilePointers);
		int i = 0;
		while (i < dataFilePointers.size()){
			Integer curr = dataFilePointers.get(i);
			if(i+1 < dataFilePointers.size())
			{
				Integer next = dataFilePointers.get(i+1);
				//are they sequential
				if(curr.intValue() + 1 < next.intValue())
				{
					i++;
					break;
				}
			}
			i++;
		}
		
		dataFilePointers.add(i);
        return i;
      
	}
	public static void removeDataBlockNumber(Integer pointer){
		dataFilePointers.remove(pointer);

	}
}