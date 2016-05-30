/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixManip;

/**
 *
 * @author anand
 */
public class MatrixManipulations {
    private MatrixManipulations(){}
	
	public static Matrix transpose(Matrix matrix) {
		Matrix transposedMatrix = new Matrix(matrix.getColCount(), matrix.getRowCount());
		for (int i=0;i<matrix.getRowCount();i++) {
			for (int j=0;j<matrix.getColCount();j++) {
				transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
			}
		}
		return transposedMatrix;
	}
	
	public static Matrix mtxInverse(Matrix matrix) throws NonSquarableException {
		return (transpose(mtxCofactor(matrix)).multiplyByConstant(1.0/mtxDeterminant(matrix)));
	}
	
	public static double mtxDeterminant(Matrix matrix) throws NonSquarableException {
		if (!matrix.isSquare())
			throw new NonSquarableException("Error : Matrix is expected to be square.");
		if (matrix.size() == 1){
			return matrix.getValueAt(0, 0);
		}
			
		if (matrix.size()==2) {
			return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - ( matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
		}
		double sum = 0.0;
		for (int i=0; i<matrix.getColCount(); i++) {
			sum += changeSign(i) * matrix.getValueAt(0, i) * mtxDeterminant(createSubMatrix(matrix, 0, i));
		}
		return sum;
	}

	private static int changeSign(int i) {
		if (i%2==0)
			return 1;
		return -1;
	}
	
	public static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) {
		Matrix mat = new Matrix(matrix.getRowCount()-1, matrix.getColCount()-1);
		int r = -1;
		for (int i=0;i<matrix.getRowCount();i++) {
			if (i==excluding_row)
				continue;
				r++;
				int c = -1;
			for (int j=0;j<matrix.getColCount();j++) {
				if (j==excluding_col)
					continue;
				mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
			}
		}
		return mat;
	}
	
	public static Matrix mtxCofactor(Matrix matrix) throws NonSquarableException {
		Matrix mat = new Matrix(matrix.getRowCount(), matrix.getColCount());
		for (int i=0;i<matrix.getRowCount();i++) {
			for (int j=0; j<matrix.getColCount();j++) {
				mat.setValueAt(i, j, changeSign(i) * changeSign(j) * mtxDeterminant(createSubMatrix(matrix, i, j)));
			}
		}
		
		return mat;
	}
	
	public static Matrix mtxAdd(Matrix matrix1, Matrix matrix2) throws IllicitDimensionException {
		if (matrix1.getColCount() != matrix2.getColCount() || matrix1.getRowCount() != matrix2.getRowCount())
			throw new IllicitDimensionException("Error : Matrices with same dimension can be added.");
		Matrix sumMatrix = new Matrix(matrix1.getRowCount(), matrix1.getColCount());
		for (int i=0; i<matrix1.getRowCount();i++) {
			for (int j=0;j<matrix1.getColCount();j++) 
				sumMatrix.setValueAt(i, j, matrix1.getValueAt(i, j) + matrix2.getValueAt(i,j));
			
		}
		return sumMatrix;
	}
	
	public static Matrix mtxSubtract(Matrix matrix1, Matrix matrix2) throws IllicitDimensionException {
		return mtxAdd(matrix1,matrix2.multiplyByConstant(-1));
	}
	
	public static Matrix mtxMultiply(Matrix matrix1, Matrix matrix2)  {
		Matrix mtplyMatrix = new Matrix(matrix1.getRowCount(), matrix2.getColCount());
		
		for (int i=0;i<mtplyMatrix.getRowCount();i++) {
			for (int j=0;j<mtplyMatrix.getColCount();j++) {
				double summation = 0.0;
				for (int k=0;k<matrix1.getColCount();k++) {
					summation += matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j);
				}
				mtplyMatrix.setValueAt(i, j, summation);
			}
		}
		return mtplyMatrix;
	}
}
