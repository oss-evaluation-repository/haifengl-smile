;   Copyright (c) 2010-2020 Haifeng Li. All rights reserved.
;
;   Smile is free software: you can redistribute it and/or modify
;   it under the terms of the GNU Lesser General Public License as
;   published by the Free Software Foundation, either version 3 of
;   the License, or (at your option) any later version.
;
;   Smile is distributed in the hope that it will be useful,
;   but WITHOUT ANY WARRANTY; without even the implied warranty of
;   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;   GNU Lesser General Public License for more details.
;
;   You should have received a copy of the GNU Lesser General Public License
;   along with Smile.  If not, see <https://www.gnu.org/licenses/>.

(ns smile.manifold
  "Manifold Learning"
  {:author "Haifeng Li"}
  (:import [smile.manifold IsoMap LLE LaplacianEigenmap TSNE]))
  
(defn isomap
  "Isometric feature mapping. Isomap is a widely used low-dimensional embedding methods,
   where geodesic distances on a weighted graph are incorporated with the
   classical multidimensional scaling. Isomap is used for computing a
   quasi-isometric, low-dimensional embedding of a set of high-dimensional
   data points. Isomap is highly efficient and generally applicable to a broad
   range of data sources and dimensionalities."
  ([data k] (isomap data k 2 true))
  ([data k d c-isomap] (IsoMap/of data k d c-isomap)))

(defn lle
  "Locally Linear Embedding. It has several advantages over Isomap, including
   faster optimization when implemented to take advantage of sparse matrix
   algorithms, and better results with many problems. LLE also begins by
   finding a set of the nearest neighbors of each point. It then computes
   a set of weights for each point that best describe the point as a linear
   combination of its neighbors. Finally, it uses an eigenvector-based
   optimization technique to find the low-dimensional embedding of points,
   such that each point is still described with the same linear combination
   of its neighbors. LLE tends to handle non-uniform sample densities poorly
   because there is no fixed unit to prevent the weights from drifting as
   various regions differ in sample densities."
  ([data k] (lle data k 2 true))
  ([data k d] (LLE/of data k d)))

(defn laplacian
  "Laplacian Eigenmap. Using the notion of the Laplacian of the nearest
   neighbor adjacency graph, Laplacian Eigenmap compute a low dimensional
   representation of the dataset that optimally preserves local neighborhood
   information in a certain sense. The representation map generated by the
   algorithm may be viewed as a discrete approximation to a continuous map
   that naturally arises from the geometry of the manifold."
  ([data k] (laplacian data k 2 -1.0))
  ([data k d t] (LaplacianEigenmap/of data k d t)))

(defn tsne
  "t-distributed stochastic neighbor embedding. t-SNE is a nonlinear
   dimensionality reduction technique that is particularly well suited
   for embedding high-dimensional data into a space of two or three
   dimensions, which can then be visualized in a scatter plot. Specifically,
   it models each high-dimensional object by a two- or three-dimensional
   point in such a way that similar objects are modeled by nearby points
   and dissimilar objects are modeled by distant points."
  ([data] (tsne data 2 20.0 200.0 1000))
  ([data d perplexity eta iterations] (TSNE. data d perplexity eta iterations)))