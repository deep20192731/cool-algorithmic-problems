import java.util.ArrayList;
import java.util.List;


class TreeNode 
{
	public char label;
	public boolean visited=false;
	public TreeNode(char l) {
		this.label=l;
	}
}

public class Graph 
{
	private TreeNode rootNode;
	private List<TreeNode> nodes=new ArrayList<TreeNode>();
	private int[][] adjMatrix; //Edges will be represented as adjacency Matrix
	private int size;
	
	public void setRootNode(TreeNode n) {
		this.rootNode=n;
	}
	
	public TreeNode getRootNode() {
		return this.rootNode;
	}
	
	public void addNode(TreeNode n) {
		nodes.add(n);
	}
	
	//This method will be called to make connect two nodes
	public void connectNode(TreeNode start,TreeNode end) {
		if(adjMatrix==null) {
			size=nodes.size();
			adjMatrix=new int[size][size];
		}

		int startIndex=nodes.indexOf(start);
		int endIndex=nodes.indexOf(end);
		adjMatrix[startIndex][endIndex]=1;
		adjMatrix[endIndex][startIndex]=1;
	}
	
	private TreeNode getUnvisitedChildNode(TreeNode n) {
		int index=nodes.indexOf(n);
		int j=0;
		while(j<size) {
			if(adjMatrix[index][j]==1 && ((TreeNode)nodes.get(j)).visited==false) {
				return (TreeNode)nodes.get(j);
			}
			j++;
		}
		return null;
	}
	
	/*//BFS traversal of a tree is performed by the bfs() function
	public void bfs() {
		//BFS uses Queue data structure
		Queue q=new LinkedList();
		q.add(this.rootNode);
		printNode(this.rootNode);
		rootNode.visited=true;
		while(!q.isEmpty()) {
			TreeNode n=(TreeNode)q.remove();
			TreeNode child=null;
			while((child=getUnvisitedChildNode(n))!=null) {
				child.visited=true;
				printNode(child);
				q.add(child);
			}
		}
		//Clear visited property of nodes
		clearNodes();
	}
	
	//DFS traversal of a tree is performed by the dfs() function
	public void dfs() {
		//DFS uses Stack data structure
		Stack s=new Stack();
		s.push(this.rootNode);
		rootNode.visited=true;
		printNode(rootNode);
		while(!s.isEmpty())
		{
			TreeNode n=(TreeNode)s.peek();
			TreeNode child=getUnvisitedChildNode(n);
			if(child!=null)
			{
				child.visited=true;
				printNode(child);
				s.push(child);
			}
			else
			{
				s.pop();
			}
		}
		//Clear visited property of nodes
		clearNodes();
	}
*/	
	
	//Utility methods for clearing visited property of node
	private void clearNodes()
	{
		int i=0;
		while(i<size)
		{
			TreeNode n=(TreeNode)nodes.get(i);
			n.visited=false;
			i++;
		}
	}
	
	//Utility methods for printing the node's label
	private void printNode(TreeNode n)
	{
		System.out.print(n.label+" ");
	}
	

	public static void main(String[] args) {
		//Lets create nodes as given as an example in the article
		TreeNode nA=new TreeNode('A');
		TreeNode nB=new TreeNode('B');
		TreeNode nC=new TreeNode('C');
		TreeNode nD=new TreeNode('D');
		TreeNode nE=new TreeNode('E');
		TreeNode nF=new TreeNode('F');

		//Create the graph, add nodes, create edges between nodes
		Graph g=new Graph();
		g.addNode(nA);
		g.addNode(nB);
		g.addNode(nC);
		g.addNode(nD);
		g.addNode(nE);
		g.addNode(nF);
		g.setRootNode(nA);
				
		g.connectNode(nA,nB);
		g.connectNode(nA,nC);
		g.connectNode(nA,nD);
				
		g.connectNode(nB,nE);
		g.connectNode(nB,nF);
		g.connectNode(nC,nF);
	}
}
