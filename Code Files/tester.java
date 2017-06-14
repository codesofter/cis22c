
public class tester
{
	public static void testMapDataInput()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("NeighborhoodMap1.txt");
		graph.showAdjTable();
	}

	public static void testBreadthFirstTraversal()
	{
		NeighborhoodGraph<LocationPoint> graph = GraphIO.getNeighborhoodMap("NeighborhoodMap1.txt");
		LocationPoint startElement = graph.findLocationByName("A");
		graph.breadthFirstTraversal(startElement, new LocationPointVisitor());
	}
}
