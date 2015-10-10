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
package psograph;

import java.io.File;

import psograph.graph.CalculatedGraph;
import psograph.graph.Graph;
import psograph.graph.NodeLocationCalculator;
import psograph.util.Util;


public class LoadAndPlotGraph {


	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		try
		{
			System.out.println("getting CG1");
			CalculatedGraph cg1 = Util.streaminCalculatedGraph(new File("C:\\Comparisions\\Seed0\\Toy\\COMPLETE.CalculatedGraph"));
			
			System.out.println("Fitness "+cg1.getFitnessValue()+ " edges "+cg1.getTotalEdges());
			
			System.out.println("got CG1 ");
			
			Util.printMFileSpecial(cg1, .16, new File("C:\\Comparisions\\SpecialGraphPlotCOMPLETE.m" ));
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		
	}

	

	


    	



    	

		

}
