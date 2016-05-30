package multiplelinearregression;

import MatrixManip.MatrixManipulations;
import MatrixManip.NonSquarableException;
import MatrixManip.Matrix;
/**
 * 
 * @author anand
 *
 * This class calculates the coefficients for modelling multi-linear regression.
 *
 */
public class MLR {

	private Matrix X;
	final private Matrix Y;
	final private boolean bias;
	
	public MLR(final Matrix x, final Matrix y) {
		this(x,y,true);
	}
	
	public MLR(final Matrix x, final Matrix y, final boolean bias) {
		super();
		this.X = x;
		this.Y = y;
		this.bias = bias;
	}
	
	public Matrix calculate() throws NonSquarableException {
		if (bias)
			this.X = X.insrtColmnWithVal();
		checkDimention();
		final Matrix Xtranspose = MatrixManipulations.transpose(X); //X'
		final Matrix XXtranspose = MatrixManipulations.mtxMultiply(Xtranspose,X); //XX'
		final Matrix inverseOfXXtranspose = MatrixManipulations.mtxInverse(XXtranspose); //(XX')^-1
		if (inverseOfXXtranspose == null) {
			System.out.println("MLR failed to create the model for these data, since matrix X'X does not have any inverse");
			return null;
		}
		final Matrix XtrY = MatrixManipulations.mtxMultiply(Xtranspose,Y); //X'Y
		return MatrixManipulations.mtxMultiply(inverseOfXXtranspose,XtrY); //(XX')^-1 X'Y
	}

	void checkDimention() {
		if (X == null) 
			throw new NullPointerException("Descriptor matrix cannot be null.");
		if (Y == null) 
			throw new NullPointerException("Y matrix cannot be null.");
		
		if (X.getColCount() > X.getRowCount()) {
			throw new IllegalArgumentException("The number of columns in descriptors' matrix cannot be more than the number of rows");
		}
		if (X.getRowCount() != Y.getRowCount()) {
			throw new IllegalArgumentException("The number of rows in descriptors' matrix should be the same as the number of rows in Y matrix. ");
		}
	}
	
}
