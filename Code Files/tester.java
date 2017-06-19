
/*
* Coder: Bruce Decker, So Choi, Bao Chau
*/
public class tester
{
	public static void testMapDataInput()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");
		graph.showAdjTable();
	}

	public static void testBreadthFirstTraversal()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");
		LocationPoint startElement = graph.findLocationByName("A");
		graph.breadthFirstTraversal(startElement, new LocationPointVisitor());
	}
	
	public static void testDepthFirstTraversal()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");
		LocationPoint startElement = graph.findLocationByName("A");
		graph.depthFirstTraversal(startElement, new LocationPointVisitor());
	}
	
	public static void testFileSaving()
	{
		System.out.println("Testing File Saving ............");
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		System.out.println("saveGraphToFile returns: " + GraphIO.saveGraphToFile(graph, "output.txt"));
	}
	
	public static boolean testGetEulerCircuit()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		LocationPoint startVertex = graph.findLocationByName("A");
		graph.getEulerCircuit(startVertex);
		return true;
	}
	

	
	public static void testHasEulerCircuit()
	{
/*		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		LocationPoint startVertex = graph.findLocationByName("A");
		LocationPoint endVertex = graph.findLocationByName("B");
		
		System.out.println("Original graph, hasEulerCircuit returns: " + graph.hasEulerCircuit(startVertex));
		
		graph.remove(startVertex, endVertex);
		System.out.println("Remove A -> B, hasEulerCircuit returns: " + graph.hasEulerCircuit(startVertex));
		
		graph.addEdge(startVertex, endVertex, 0.0);
		System.out.println("Add A -> B back, hasEulerCircuit returns: " + graph.hasEulerCircuit(startVertex));
		return true;*/
		//return graph.hasEulerCircuit(startVertex);
		/*
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		LocationPoint startElement = graph.findLocationByName("A");
		graph.hasEulerCircuit();
		*/
	}
	
}
