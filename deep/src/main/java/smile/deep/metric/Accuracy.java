/*
 * Copyright (c) 2010-2021 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */
package smile.deep.metric;

import smile.deep.tensor.Tensor;

/**
 * The accuracy is the proportion of true results (both true positives and
 * true negatives) in the population.
 *
 * @author Haifeng Li
 */
public class Accuracy implements Metric {
    long correct = 0;
    long size = 0;

    /**
     * Constructor.
     */
    public Accuracy() {

    }

    @Override
    public String toString() {
        return String.format("Accuracy %.2f", 100 * compute());
    }

    @Override
    public String name() {
        return "Accuracy";
    }

    @Override
    public void update(Tensor output, Tensor target) {
        Tensor prediction = output.argmax(1, false);  // get the index of the max log - probability
        correct += prediction.eq(target).sum().toInt();
        size += target.size(0);
    }

    @Override
    public double compute() {
        return (double) correct / size;
    }

    @Override
    public void reset() {
        correct = 0;
        size = 0;
    }

    /**
     * Calculates the classification accuracy.
     * @param output the model output.
     * @param target the ground truth.
     * @return the metric.
     */
    public static double of(Tensor output, Tensor target) {
        Tensor prediction = output.argmax(1, false);  // get the index of the max log - probability
        long correct = prediction.eq(target).sum().toInt();
        return (double) correct / target.size(1);
    }
}