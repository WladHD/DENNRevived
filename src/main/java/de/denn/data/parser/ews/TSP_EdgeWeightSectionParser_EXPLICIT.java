package de.denn.data.parser.ews;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.model.TSP_RawData;
import de.denn.data.parser.interfaces.TSP_EdgeWeightSectionParserInterface;

public class TSP_EdgeWeightSectionParser_EXPLICIT implements TSP_EdgeWeightSectionParserInterface {

	@Override
	public TSP_EdgeWeightType getEdgeWeightType() {
		return TSP_EdgeWeightType.EXPLICIT;
	}

	@Override
	public int[][] parseMatrix(TSP_RawData data) {
		int[][] matrix = new int[data.getDimension()][data.getDimension()];
		String[] nums = convertToString(data.getEdgeWeightSection()).split(" ");

		int g = 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < i + 1; j++) {
				matrix[i][j] = Integer.parseInt(nums[g]);
				matrix[j][i] = Integer.parseInt(nums[g]);
				g++;
			}
		}

		return matrix;
	}

}
