// This is a library to be used to represent a Graph and various measurments for a Graph
//  and to perform optimization using Particle Swarm Optimization (PSO)
//    Copyright (C) 2008, 2015 Patrick Olekas
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

import java.util.TreeMap;
import java.util.Vector;

import psograph.graph.Graph;
import psograph.graph.Node;
import psograph.graph.NodeLocationCalculator;
import psograph.graph.calc.NonLinearCostFunction;


public class NodesWithinRadius1Radius2 {


	public NodesWithinRadius1Radius2(Graph g) 
	{
		m_graph = g;
		isCalculated = false;
	}
	
	public void setRadius1(double r)
	{
		m_radius1 = r;
	}
	
	public void setRadius2(double r)
	{
		m_radius2 = r;
	}

	public TreeMap<Integer, Integer> Measure() throws Exception 
	{
		m_resultById = new TreeMap<Integer, Integer>();
		TreeMap<Integer, Integer> result = new TreeMap<Integer, Integer>();
		
		NodeLocationCalculator nodeLocationCalculator = 
			new NodeLocationCalculator(m_graph);
		nodeLocationCalculator.calculateResults();
		
		
		Vector<Node> nodes = new Vector<Node>(nodeLocationCalculator.getHeaderNodesMap().values() );
		
		int[][] adjMatrix = nodeLocationCalculator.getAdjecencyGraph2();
		
		for(int i = 0; i < nodeLocationCalculator.getNumberOfNodes(); i++)
		{
			int node_i_id = nodes.get(i).getID();
			Node Nodei = nodeLocationCalculator.getNode(node_i_id);
			
			int connectedNodesWithinRadius = 0;
			
			for(int j = 0; j < nodeLocationCalculator.getNumberOfNodes(); j++)
			{
				if(adjMatrix[i][j]== 1)
				{
					int node_j_id = nodes.get(j).getID();
					Node Nodej = nodeLocationCalculator.getNode(node_j_id);
					
					boolean isConnected = nodeLocationCalculator.isNodeAConnectedToNodeB(Nodei, Nodej);
					
					if(!isConnected)
						throw new Exception("Connnected Nodes withing R - node mismatch");
					
					double cost = Nodei.getConnectionInfo(Nodej).getWeight();
					
					double distance = NonLinearCostFunction.getDistance(cost);
					
					if(Double.compare(distance, m_radius1 ) > 0.0 
							&&  Double.compare(distance, m_radius2 ) < 0.0 )
					{
						connectedNodesWithinRadius++;
					}
				}
				
			}
			m_resultById.put(node_i_id, connectedNodesWithinRadius);
			
		}
			
		Vector<Integer> vNodeIds = new Vector<Integer>(m_resultById.keySet());
		for(int i=0; i < vNodeIds.size(); i++)
		{
			int connectNodeWithinR = m_resultById.get(vNodeIds.get(i));
			
			int value = 0;
			if(result.containsKey(connectNodeWithinR))
			{
				value = result.get(connectNodeWithinR);
			}
			
			result.put(connectNodeWithinR, value +1);
		}
			
			
		

		isCalculated = true;
		return result;
	}
	
	


	public boolean isCalculated() {

		return isCalculated;
	}

	double m_radius1 = 0;
	double m_radius2 = 0;
	
	TreeMap<Integer, Integer> m_resultById;
	
	Graph m_graph;
	boolean isCalculated;
	public TreeMap<Integer, Integer> ById() 
	{
		return m_resultById;
	}

	
}
