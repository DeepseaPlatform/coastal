/*
 * Origin of the benchmark:
 *     repo: https://babelfish.arc.nasa.gov/hg/jpf/jpf-symbc
 *     branch: updated-spf
 *     root directory: src/tests/gov/nasa/jpf/symbc
 * The benchmark was taken from the repo: 24 January 2018
 */
/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * Symbolic Pathfinder (jpf-symbc) is licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// package gov.nasa.jpf.symbc;
package svcomp;

import org.sosy_lab.sv_benchmarks.Verifier;

public class MainX {

	static final int INFINITY = Integer.MAX_VALUE;

	static int[] runBellmanFord(int N, int D[], int src) {
		// Initialize distances.
		int dist[] = new int[N];
		boolean infinite[] = new boolean[N];
		for (int i = 0; i < N; i++) { // V+1 branches
			dist[i] = INFINITY;
			infinite[i] = true;
		}
		dist[src] = 0;
		infinite[src] = false;

		// Keep relaxing edges until either:
		// (1) No more edges need to be updated.
		// (2) We have passed through the edges N times.
		int k;
		for (k = 0; k < N; k++) { // V+1 branches
			boolean relaxed = false;
			for (int i = 0; i < N; i++) { // V(V+1) branches
				for (int j = 0; j < N; j++) { // V^2(V+1) branches
					if (i == j)
						continue; // V^3 branches
					if (!infinite[i]) { // V^2(V-1) branches
						if (dist[j] > dist[i] + D[i * N + j]) { // V^2(V-1) branches
							dist[j] = dist[i] + D[i * N + j];
							infinite[j] = false;
							relaxed = true;
						}
					}
				}
			}
			if (!relaxed) // V branches
				break;
		}

		// Check for negative-weight egdes.
		if (k == N) { // 1 branch
			// We relaxed during the N-th iteration, so there must be
			// a negative-weight cycle.
		}

		// Return the computed distances.
		return dist;
	}

	public static void main(String[] args) {
		final int V = Verifier.nondetInt();
		Verifier.assume(V > 0 && V < 1000000);

		final int D[] = new int[V * V];

		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				if (i == j)
					continue;
				int tmp = Verifier.nondetInt();
				Verifier.assume(tmp >= 0 && tmp < 1000000);
				D[i * V + j] = tmp;
			}
		}

		int dist[] = runBellmanFord(V, D, 0);
		for (int d : dist) {
			// either there is no path to d from the source,
			// or it goes through at most V nodes
			assert (d == INFINITY || d <= V);
		}
	}
}
