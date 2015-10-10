// This is a library to be used to represent a Graph and various measurments for a Graph
//  and to perform optimization using Particle Swarm Optimization (PSO)
//    Copyright (C) 2008, 1015 Patrick Olekas
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//   You should have received a copy of the GNU General Public License
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.
package psograph.graph.measurements;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Vector;

import psograph.graph.Graph;
import psograph.graph.Node;
import psograph.graph.Path;

 
public class SPL  implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3199607653710066896L;
	public SPL(Graph g)
	{
		m_graph = g;
		m_isCalculated = false;
		m_solns = new Graph[m_graph.getNumberOfNodes()];
	}
	

	void determineSPLs() throws Exception
	{	

		Vector<Integer> vhn = new Vector<Integer>(m_graph.getHeaderNodesMap().keySet());
		
		for(int i =0; i < vhn.size(); i++)
		{
			//System.out.print("Iter "+ i);
			Graph totallyTemp = new Graph(m_graph);
			m_solns[i] = evaulateNodeWithBFS(vhn.get(i), totallyTemp);
			//System.out.println(" completed");
		}
		
		m_isCalculated = true;

		
	}
	

	
	public Graph[] Measure() throws Exception
	{
		if(m_graph.isFullyConnected() == false)
		{
			System.out.println("SPL - This measure is not accurate for a network with any disconnected nodes");
			return null;
		}
		
		determineSPLs();
		return m_solns;
	}
	
	Graph evaulateNodeWithBFS(int NodeId , Graph g) throws Exception
	{
		int adj[][] = g.getAdjecencyGraph2();

		Node n = g.getNode(NodeId);
		n.setDepth(0);
		Path p = new Path(n);
		n.addPath(p);

		LinkedList<Integer> workQ = new LinkedList<Integer>();

		workQ.add(n.getID());


		boolean isDone = false;
		while( isDone != true)
		{
			int row = workQ.remove();


			for(int i =0; i < g.getNumberOfNodes(); i++)
			{
				if(i == row)
					continue;

				if(adj[row][i] == 0)
					continue;

				workQ.add(i);

				Vector<Path> vPath = g.getNode(row).getPaths();
				
				Node destNode = g.getNode(i);

				for(int j =0; j < vPath.size(); j++)
				{
					Path tt = new Path(vPath.get(j), destNode);
					//tt.print();
					destNode.addPath(tt);
				}
				
				adj[row][i] =0;
				adj[i][row]=0;
			}
			
			if(workQ.isEmpty())
				isDone = true;
		}

		return g;
		
	}
		
		
		

	
	public boolean isCalculated()
	{
		return m_isCalculated;
	}
	
	Graph m_graph; // the original graph
	Graph m_solns[];
	boolean m_isCalculated;
	
	
	

	
}
