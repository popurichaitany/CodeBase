package MatrixManip;

public class Matrix {

	private int rowCount;
	private int colCount;
	private double[][] value;

	public Matrix(double[][] dt) {
		this.value = dt;
		this.rowCount = dt.length;
		this.colCount = dt[0].length;
	}

	public Matrix(int nrow, int ncol) {
		this.rowCount = nrow;
		this.colCount = ncol;
		value = new double[nrow][ncol];
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getColCount() {
		return colCount;
	}

	public void setColCount(int colCount) {
		this.colCount = colCount;
	}

	public double[][] getValues() {
		return value;
	}

	public void setValues(double[][] values) {
		this.value = values;
	}

	public void setValueAt(int row, int col, double value) {
		this.value[row][col] = value;
	}

	public double getValueAt(int row, int col) {
		return value[row][col];
	}

	public boolean isSquare() {
		return rowCount == colCount;
	}

	public int size() {
		if (isSquare())
			return rowCount;
		return -1;
	} 

	public Matrix multiplyByConstant(double ctnt) {
		Matrix mtx = new Matrix(rowCount, colCount);
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				mtx.setValueAt(i, j, value[i][j] * ctnt);
			}
		}
		return mtx;
	}
	public Matrix insrtColmnWithVal() {
		Matrix insMtx = new Matrix(this.getRowCount(), this.getColCount()+1);
                int i = 0;
                while(i<insMtx.getRowCount()){
                    int j = 0;
                    while(j<insMtx.getColCount()){
                    if (j==0)
			insMtx.setValueAt(i, j, 1.0);
			else 
			 insMtx.setValueAt(i, j, this.getValueAt(i, j-1));
                    }
                } 
		return insMtx;
	}
}
