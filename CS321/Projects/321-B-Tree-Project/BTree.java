import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/*
*
*@authors EmmaLytle, IreneGalca, AdriannaMickle 
*/
public class BTree<T>{

	private BTreeNode root = null;//Null?
	private int degree;
	private int minKey, midKey, maxKey, maxChildren; //keys of the node
	private int numKeys=0; //number of key values in the node
	private int height; //height of B-Tree[might need??]
	private int size = 0; //size of the B-Tree?

	private int duplicateCount = 0;
	private int seqLength;
	int totalNodes = 0;


	BTree(int degree, String bTreeFile, int seqLength ) {
		this.seqLength = seqLength;
		root = new BTreeNode<T>(degree);
		root.setFilePointer(0);

		this.degree = degree;
		this.maxKey = (2 * degree)-1;
		this.maxChildren = 2 * degree;
		this.minKey = degree -1;


		BTreeDiskAccess.setBTreeFile(bTreeFile);
		BTreeDiskAccess.setNodeSizeOnDisk(degree);
		//root.setLeaf(true);	
	}
	
	public int isEmpty()
	{
		return size = 0;
	}

	public int GeneSearch(BTreeNode<T> x,Long k) throws FileNotFoundException, ClassNotFoundException, IOException {
		int i = 0;
		while((i < x.getKeyCount()) && (k.compareTo(x.getKey(i))>0)) {
			i++;
		}
		if((i < x.getKeyCount()) && (k.compareTo(x.getKey(i))==0)) 
		{
			return 1;
			//return(x,i);//from pseudocode
		}
		else if(x.isLeaf()==true)
		{			
			return -1;//element was not found
		}
		else {
		//	disk_read(x.getFilePointer());
			return GeneSearch(x.getPointer(i),k);
		}
	}

	public void split(BTreeNode<T> node, int i) throws FileNotFoundException, IOException {
		//may need to check if there is a root node that newParent can be added to.
		BTreeNode<T> rightNode = new BTreeNode(degree);
		BTreeNode<T> leftNode = node.getPointer(i);
		
		rightNode.setLeaf(leftNode.isLeaf());
		rightNode.childrenCount=degree-1;
		for(int j = 1; j<=degree-1; j++) {
			rightNode.insertKey(leftNode.getKey(j+degree-1), j-1);
			rightNode.setDup(leftNode.getdup(j+degree-1), j-1);
			rightNode.upKeyCount();
		}


		if(leftNode.isLeaf() == false) {
			for(int j = 0; j<=degree-1;j++) {
				if(leftNode.getPointer(j+degree)!=null) {
					rightNode.insertPointer(leftNode.getPointer(j+degree), j);
					rightNode.childrenCount++;
				}
			}
		}
		else {
			rightNode.setLeaf(true);
		}
		leftNode.setKeyCount(degree-1);
		for(int j = node.getKeyCount(); j >= i+1; j--) {
			if(node.getPointer(j)!=null) {
				node.insertPointer(node.getPointer(j),j+1);
				//node.childrenCount++;
			}
		}
		node.insertPointer(rightNode,i+1);
		node.childrenCount++;
		node.setLeaf(false);
		for(int j = node.getKeyCount()-1; j>=i;j--) {//just changed from j>=i+1 to j>=i
			node.insertKey(node.getKey(j), j+1);//CHECK HERE
			node.setDup(node.getdup(j), j+1);

		}
		node.insertKey(leftNode.getKey(degree-1), i);
		node.setDup(leftNode.getdup(degree-1), i);

		node.upKeyCount();
		for(int j = 0; j<=degree-1; j++) {

			leftNode.insertKey(null, j+degree-1);
			leftNode.setDup(0, j+degree-1);

		}
		for(int j = 0; j<=degree-1; j++) {

			leftNode.insertPointer(null, j+degree);
		}
		//node.upKeyCount();
//		diskWrite(rightNode.getFilePointer(),rightNode);
//		diskWrite(leftNode.getFilePointer(),leftNode);
//		diskWrite(node.getFilePointer(),node);
	}

	public int insert(Long k, BTreeNode<T> currentNode) throws FileNotFoundException, IOException, ClassNotFoundException {//return int is to keep track of total duplicates. returns 0 if new element is inserted and 1 if it was found to be a duplicate
		BTreeNode<T> currentsRoot = this.root;
		for(int y = 0; y < currentNode.getKeyCount(); y++) {
			if(k.compareTo(currentNode.getKey(y))==0) {
				currentNode.upDup(1,y);
				this.duplicateCount++;
				System.out.print("Found dup!!");
				return 0;
			}
		}
		if(currentsRoot.getKeyCount() == maxKey) {
			BTreeNode<T> s = new BTreeNode<T>(degree);
			this.root = s;
			s.setLeaf(false);
			//s.numofkeys = 0;
			s.insertPointer(currentNode, 0);
			s.childrenCount++;
			split(s,0);
			return insertNon(s,k);
		}
		else {
			return insertNon(currentsRoot,k);
		}
	}
	
	public int insertNon(BTreeNode<T> currentNode, Long k) throws FileNotFoundException, ClassNotFoundException, IOException{
		for(int y = 0; y < currentNode.getKeyCount(); y++) {
			if(k.compareTo(currentNode.getKey(y))==0) {
				currentNode.upDup(1,y);
				this.duplicateCount++;
				//System.out.print("Found dup!!");
				return 0;
			}
		}
		int i = currentNode.getKeyCount();
		if(currentNode.isLeaf()==true) {
			while(i >= 1 &&  (k.compareTo(currentNode.getKey(i-1)) < 0)) {
				currentNode.insertKey(currentNode.getKey(i-1), i);
				currentNode.setDup(currentNode.getdup(i-1), i);
				i--;				
			}
			
			currentNode.insertKey(k, i);
			currentNode.setDup(0, i);

			currentNode.upKeyCount();
			//diskWrite(currentNode.getFilePointer(), currentNode);
			return 1;
		}
		else {
			while(i >= 1 && (k.compareTo(currentNode.getKey(i-1))<0)) {
				i--;
			}
			if(i >=1 && k.compareTo(currentNode.getKey(i-1))==0) {
				currentNode.upDup(1,(i-1));
				this.duplicateCount++;
				//System.out.print("Found dup!!");
				return 0;
			}
			i++;
			//disk_read(currentNode.getFilePointer());
			if(currentNode.getPointer(i-1).getKeyCount() == maxKey) {
				split(currentNode,(i-1));
				if(k.compareTo(currentNode.getKey(i-1))>0) {
					i++;
				}
				else if(k.compareTo(currentNode.getKey(i-1))==0) {
					currentNode.upDup(1, (i-1));
					this.duplicateCount++;
					//System.out.print("Found dup!!");
					return 0;
				}
			}
			return insertNon(currentNode.getPointer(i-1),k);
		}
		//return 1;
	}


	public BTreeNode<T> getRoot() {
		return this.root;
	}
	
	public int totalDups() {
		return this.duplicateCount;
	}
	
	public int totalNodes() {
		return totalNodes;
	}

	public void printInorder(BTreeNode<T> node) {
		if (node.isLeaf() == true) {
			for (int i = 0; i < node.getKeyCount(); i++) {
				System.out.println(GeneBankParser.convertToGeneSequence(node.getKey(i),seqLength) + ": " + (node.getdup(i) + 1));
				//System.out.println(node);
			}
			return;
		}
		for (int i = 0; i < node.getKeyCount() + 1; ++i) {
			//int offset = node.getChild(i);
			//BTreeNode y = readNode(offset);
			BTreeNode y = node.getPointer(i);
			printInorder(y);
			if (i < node.getKeyCount())
				System.out.println(GeneBankParser.convertToGeneSequence(node.getKey(i),seqLength) + ": " + (node.getdup(i) +1));
			//System.out.println(node);

		}

	}


}
