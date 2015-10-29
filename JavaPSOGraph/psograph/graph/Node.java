// This is a library to be used to represent a Graph and various measurments for a Graph
//  and to perform optimization using Particle Swarm Optimization (PSO)
//    Copyright (C) 2008, 2015 
//       Patrick Olekas - polekas55@gmail.com
//       Ali Minai - minaiaa@gmail.com
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
package psograph.graph;

import java.io.Serializable;
import java.util.*;


//TODO this should probably have a reference to parent graph.  And then when doing operations
//involvling Node objects (not ids), check to make sure the Node is from the same graph.

/**
 * This represents a Node in a graph.
 * @author Patrick
 *
 */
public class Node implements Serializable 
{
	static final long serialVersionUID = 35L;	
	
	private Vector<Path> m_path;
	
	private int m_id;
	
	private int m_depth =-1;	
	
	private double m_x;
	
	private double m_y;
	
	private TreeMap<Integer,ConnectionInfo> m_connectivityList;
	
	private boolean m_visited = false;
	
    private int m_longestPath = -1;
	
	/** create empty node with specified ID and X,Y location
     * 
     * @param id
     * @param x
     * @param y
     */
	Node(int id, double x, double y)
	{
		m_id = id;
		m_x = x;
		m_y = y;
		m_connectivityList = null;
		
	}
	
	

	/** Creates a node with specified id, location, and connected to the nodes specified
	 *  in the array.  All weights are assumed to be 1.
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param connecting_nodes
	 */
	Node (int id, double x, double y, int connecting_nodes[])
	{
		m_path = new Vector<Path>();
		m_id = id;
		m_x = x;
		m_y = y;
		
		int size = connecting_nodes.length;
		m_connectivityList = new TreeMap<Integer,ConnectionInfo>();
		
		for(int i=0; i < size; i++)
		{
			m_connectivityList.put(connecting_nodes[i], new ConnectionInfo(1));
		}
	}
	
	//TODO need to copy the m_path vector, does not copy it
	/**
	 * Copy constructor.  need to copy the m_path vector, does not copy it
	 * @param n
	 */
	Node (Node n)
	{		
		m_path = new Vector<Path>();
		m_id = n.m_id;
		m_x = n.m_x;
		m_y = n.m_y;
		
		if(n.m_connectivityList != null)
		{
			m_connectivityList = new TreeMap<Integer,ConnectionInfo>();
			Vector<Integer> vi = new Vector<Integer>(n.m_connectivityList.keySet());
			int i;
	
			for(i = 0; i < vi.size(); i++)
				m_connectivityList.put(vi.get(i),new ConnectionInfo(n.m_connectivityList.get(vi.get(i))));		
		}
		else
			m_connectivityList =null;
		
		m_visited = n.m_visited;	
	}

	/**
	 * Adds in a connection from this node to node ID, with the weight
	 * @param id
	 * @param weight
	 * @throws Exception
	 */
	public void addConnection(int id, double weight) throws Exception
	{
		if(m_connectivityList == null)
		{
			m_connectivityList = new TreeMap<Integer,ConnectionInfo>();
		}
		else if(id == m_id)
		{
			throw new Exception("Node::addConnection - trying to self");
		}
		else if(isConnectedTo(id)== true)
		{
			throw new Exception("Node::addConnection - trying to add connection to a node that already exists");
		}
		
		m_connectivityList.put(id, new ConnectionInfo(weight));

	}	
	
	
	/**
	 * Adds the specified path if this one is not longer than
	 * the stored paths.
	 * @param p
	 */
	public void addPath(Path p)
	{
		//Only store the path if it is not longer than paths stored
		int longest = getLPLength();
		
		if(m_path.size() > 0)
		{
			if((longest != 0) && p.getLength() > longest)
			{
				//System.out.println("Not a worthwile path");
				return;
			}
		}
		
		m_longestPath = p.getLength();
		
		m_path.add(p);
		//System.out.println("Path size is now "+m_path.size());
	}
	
	/**
	 * Comparison of two objects
	 */
	public boolean equals (Object obj)
	{
		boolean ret = false;
		
		Node n = (Node)obj;
		if(n.m_id == m_id)
			ret = true;
			
		return ret;
	}
	
	
	/**
	 * Returns the connection info between his node and Node n.
	 * @param n
	 * @return
	 */
	public ConnectionInfo getConnectionInfo(int id)
	{
		ConnectionInfo ci = null;
		ci = m_connectivityList.get(id);
		return ci;
	}
	
	/**
	 * Returns the connection info between his node and Node n.
	 * @param n
	 * @return
	 */
	public ConnectionInfo getConnectionInfo(Node n)
	{
		ConnectionInfo ci = null;
		ci = m_connectivityList.get(n.m_id);
		return ci;
	}
	
	/**
	 * Returns the Node's degree
	 * @return
	 */
	public int getDegree()
	{
		if(m_connectivityList==null)
			return 0;
		else
			return m_connectivityList.size();
	}
	
	/**
	 * Returns Depth of this node, this is field used DFS and so on.
	 * @return
	 */
	public int getDepth(){return m_depth;}	
	
	/**
	 * Get Node's ID.
	 * @return
	 */
	public int getID(){return m_id;}
		
	/**
	 * Returns the stored paths.
	 * @return
	 */
	public Vector<Path> getPaths()
	{
		return m_path;
	}
	
	/**
	 * Return longest Path's length
	 * @return
	 */
	public int getLPLength()
	{
//		int result = -1;
//		
//		for(int i =0; i < m_path.size(); i++)
//		{
//			if(i ==0)
//			{
//				result = m_path.get(i).getLength();
//			}
//			
//			if(result < m_path.get(i).getLength())
//			{
//				result = m_path.get(i).getLength();
//			}
//		}
//		
//		return result;
		return m_longestPath;
	}
	public double getMeanEdgeCost()
	{
		double value = 0;
		
		value = getTotalEdgeCost()/getDegree();
		
		return value;
	}
	
	/**
	 * This returns a TreeMap with the key being the distance, and the values being
	 * node ids
	 * @return
	 */
	public TreeMap<Double,Vector<Integer>> getNeighborDistribution ()
	{
		TreeMap<Double,Vector<Integer>> neighborDist = new TreeMap<Double,Vector<Integer>>();
		
		TreeMap<Integer,ConnectionInfo> neighbors = getNeighbors();
		
		if(neighbors == null || neighbors.size() == 0)
			return null;
		
		Vector<Integer> keylist = new Vector<Integer>(neighbors.keySet());
		
		int i ;
		for(i = 0; i < keylist.size(); i++)
		{
				ConnectionInfo ci = neighbors.get(keylist.get(i));
				double weight = ci.getWeight();
				
				if(neighborDist.get(weight) == null)
				{
					neighborDist.put(weight, new Vector<Integer>());
				}
				neighborDist.get(weight).add(keylist.get(i));
		}
		return neighborDist;
	}
	
	/**
	 * returns a Map, where the key is the node id, and the value is the
	 * ConnectionInfo
	 * @return
	 */
	public TreeMap<Integer,ConnectionInfo> getNeighbors()
	{
		return m_connectivityList;
	}
	
	/** Returns stored Paths
	public Vector<Path> getPaths()
	{
		return m_path;
	}
	
	/**
	 * Returns SPL length, if not paths stored returns -1.
	 */
	public int getSPLength()
	{
		int result = -1;
		
		for(int i =0; i < m_path.size(); i++)
		{
			if(i ==0)
			{
				result = m_path.get(i).getLength();
			}
			
			if(result > m_path.get(i).getLength())
			{
				result = m_path.get(i).getLength();
			}
		}
		
		return result;
	}
	
	/**
	 * Get Total Edge costs.
	 * @return
	 */
	public double getTotalEdgeCost()
	{
		double value = 0;

		Vector<Integer> nodes = new Vector<Integer>(m_connectivityList.keySet());
		
		for(int i =0; i < nodes.size(); i++)
		{
			value += m_connectivityList.get(nodes.get(i)).getWeight();
		}

		return value;
	}
	
	/**
	 * Returns visited flag.  Used in BFS algorithms.
	 * @return
	 */
	public boolean getVisited()
	{
		return m_visited;
	}
	
	/**
	 * Get X coordinate
	 * @return
	 */
	public double getX(){return m_x;}
	/**
	 * Get Y coordinate
	 * @return
	 */
	public double getY(){return m_y;}
	
	/**
	 * REturns true is we have a SPL to specified ID
	 * @param toId
	 * @return
	 */
	public boolean hasSPLTo(int toId) 
	{
		for(int i =0; i < m_path.size(); i++)
		{
			int idComp = m_path.get(i).getEnd().getID();
			if(idComp == toId)
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	/**Returns if Node is connected to node specifed by id
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public boolean isConnectedTo(int node_id) throws Exception
	{
		TreeMap<Integer,ConnectionInfo> neighbors = getNeighbors();
		if(neighbors == null)
		{
			return false;
		}
		
		if(neighbors.containsKey(node_id) == true)
			return true;
		else
			return false;
			
	}
	
	/**Returns if Node is connected to Node n
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public boolean isConnectedTo(Node n)throws Exception
	{
		return isConnectedTo(n.getID());
	}
	
	/**
	 * Returns the number SPL this node participates to specified node.
	 * @param toId
	 * @param participatingNode
	 * @return
	 */
	public int numberOfSPLNodeParticipatesIn(int toId, Node participatingNode)
	{
		int count = 0;
		
		for(int i =0; i < m_path.size(); i++)
		{
			if(m_path.get(i).getEnd().getID() == toId)
			{
				if(m_path.get(i).isNodeOnPath(participatingNode))
				{
					count++;
				}
			}
		}

		return count;
	}
	/** Returns number of SPL (as we can multiple shortest paths)
	 * 
	 * @return
	 */
	public int numOfSPLength()
	{
		int value = getSPLength();
		
		int result = 0;
		
		for(int i =0; i < m_path.size(); i++)
		{
			if(value == m_path.get(i).getLength())
				result++;
		}
		
		return result;
		
	}
	/**
	 * Removes a connection between this node and the specified node id.  NOTE:
	 * This will not remove a connect from the other node to this one.  Example, if 
	 * Node A and Node B were connected.  You would need to call both:
	 * NodeA.removeconnection(NodeB.getID());
	 * NodeB.removeConnection(NodeA.getID());
	 * 
	 * This was implemented this way as to keep the implementation possible to 
	 * do bi-directed graphs.
	 * 
	 * An exception will be throw if we try to remove a connection to it's self.
	 * Or if we attempt remove a connection to an unconnected node.
	 * @param id ID of node to remove connection from
	 * @throws Exception  
	 */
	public void removeConnection(int id) throws Exception
	{
		if(m_connectivityList == null)
		{
			throw new Exception("Node::removeConnection - connection list is null");
		}
		else if(id == m_id)
		{
			throw new Exception("Node::removeConnection - trying to self");
		}
		else if(isConnectedTo(id)== false)
		{
			throw new Exception("Node::removeConnection - trying to remove connection that does not exist");
		}
		m_connectivityList.remove(id);	
	}
	/** Sets the Depth
	 * 
	 * @param depth
	 */
	public void setDepth(int depth){ m_depth=depth;}
	
	/**
	 * Sets the path vector
	 * @param p
	 */
	public void setPath(Vector<Path> p)
	{
		m_path = p;
	}
	/**
	 * Sets the visited flag.
	 * @param b
	 */
	public void setVisited(boolean b) 
	{
		m_visited=true;
	}
	
}
