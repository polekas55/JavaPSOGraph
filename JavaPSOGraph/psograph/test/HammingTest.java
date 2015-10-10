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
package psograph.test;

import org.junit.Before;
import org.junit.Test;

import psograph.pso.HammingDistance;

import static org.junit.Assert.*;




public class HammingTest
{
	int [][]A;
	int [][]B;
	
	@Before
	public void setUp() throws Exception 
	{
		
		A = new int[][] { 
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{0, 0, 0, 0, 1, 0, 1, 0, 1, 0 },
			{0, 1, 0, 1, 0, 1, 1, 1, 1, 1 },
			{0, 0, 0, 0, 1, 0, 1, 0, 1, 0 },
			{0, 0, 0, 1, 1, 1, 0, 1, 1, 1 },
			{1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{0, 0, 1, 1, 1, 1, 1, 1, 0, 1 },
			{0, 0, 1, 0, 1, 0, 1, 0, 1, 0 }};

		B = new int[][] { 
				{0, 1, 0, 0, 1, 1, 1, 0, 0, 0 },
				{1, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
				{0, 1, 0, 0, 1, 0, 1, 1, 0, 0 },
				{0, 1, 0, 0, 0, 0, 0, 1, 1, 0 },
				{1, 0, 1, 0, 0, 0, 0, 1, 1, 0 },
				{1, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
				{1, 0, 1, 0, 0, 0, 0, 1, 1, 0 },
				{0, 0, 1, 1, 1, 0, 1, 0, 1, 1 },
				{0, 0, 0, 1, 1, 1, 1, 1, 0, 1 },
				{0, 0, 0, 0, 0, 1, 0, 1, 1, 0 }};

	}
	
	@Test
	public void testHammingDistance() throws Exception
	{
		int result = HammingDistance.CalculateHammingDistance( A, B);
		assertTrue("Hamming Distance should be 22, but is " + result, result == 22);
	}
	
	
}
