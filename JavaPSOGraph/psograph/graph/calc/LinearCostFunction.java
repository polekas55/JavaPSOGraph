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
package psograph.graph.calc;

import java.io.Serializable;

/**
 * Linear  Cost Function. The distance is the cost y(x) = x
 * @author Patrick
 *
 */
public class LinearCostFunction extends CostFunction implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8776989826938445394L;

	/**
	 * default constructor
	 */
	public LinearCostFunction() {}
	
	/**
	 * Calculate cost.
	 */
	public double calculate (double t_sig) throws Exception
	{
		
		if(Double.compare(t_sig , 0.0) == 0    )
		{
			System.out.println(" cost_sig is zero!!!!  Setting cost to .01");
			t_sig = .01;
		}
		return t_sig;
		
	}
	
}
