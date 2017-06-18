
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
		//LocationPoint fixVertex = startVertex;
		graph.getEulerCircuit(startVertex);
		return true;
	}
	
	public static void display_path()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		graph.display_path();
	}
	
	public void testHasEulerCircuit()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		//LocationPoint startElement = graph.findLocationByName("A");
		graph.hasEulerCircuit();
	}
	
	public  static void testIsValidEdge() {
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("AbornNieman Map.txt");	
		LocationPoint startLocation = graph.findLocationByName("B");
		LocationPoint endLocation = graph.findLocationByName("N");
		
		graph.isNextEdgeValid(startLocation, endLocation);
	}
	
}
