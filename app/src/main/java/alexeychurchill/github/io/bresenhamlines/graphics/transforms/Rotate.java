package alexeychurchill.github.io.bresenhamlines.graphics.transforms;


import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;

public class Rotate extends Transform {
    private Point mCenterPoint = null;
    private double angle = 0.0;

    public Rotate() {
        getTransformMatrix().set(2, 2, 1.0);
        setRotationDegree(0.0);
    }

    public void setRotation(double angle) {
        this.angle = angle;
        double sinA = Math.sin(-angle);
        double cosA = Math.cos(-angle);
        getTransformMatrix().set(0, 0, cosA);
        getTransformMatrix().set(0, 1, -sinA);
        getTransformMatrix().set(1, 0, sinA);
        getTransformMatrix().set(1, 1, cosA);
    }

    public void setRotationDegree(double angleDegree) {
        setRotation(angleDegree / 180.0 * Math.PI);
    }

    public Point getCenterPoint() {
        return mCenterPoint;
    }

    public void setCenterPoint(Point centerPoint) {
        this.mCenterPoint = centerPoint;
    }

    @Override
    public Point transform(Point point) {
        if (mCenterPoint == null) {
            return super.transform(point);
        }
        Point sourcePoint = new Point();
        sourcePoint.setX(point.getX() - mCenterPoint.getX());
        sourcePoint.setY(point.getY() - mCenterPoint.getY());
        Point result = super.transform(sourcePoint);
        result.setX(result.getX() + mCenterPoint.getX());
        result.setY(result.getY() + mCenterPoint.getY());
        return result;
    }

    public double getRotationDegree() {
        return (getRotation() / Math.PI * 180.0);
    }

    public double getRotation() {
        return angle;
    }
}
