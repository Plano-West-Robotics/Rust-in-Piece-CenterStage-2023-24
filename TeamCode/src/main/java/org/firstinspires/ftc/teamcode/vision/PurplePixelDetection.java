package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;


public class PurplePixelDetection extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();

    public enum Prop {
        BLUE, RED
    }

    public enum Location {
        Left,
        Center,
        Right
    }
    private Location location;
    static final Rect LEFT_ROI = new Rect(
            new Point(30*3, 35*2.25),
            new Point(210*3, 445*2.25));
    static final Rect RIGHT_ROI = new Rect(
            new Point(420*3, 35*2.25),
            new Point(610*3,445*2.25 ));

    static final Rect CENTER_ROI = new Rect(
            new Point(230*3, 35*2.25),
            new Point(400*3,445*2.25 ));
    static double PERCENT_COLOR_THRESHOLD = 0.2;

    public Prop propColor = Prop.RED;

    public PurplePixelDetection(Telemetry t) {telemetry = t;}

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV;
        Scalar highHSV;
        if (propColor == Prop.BLUE) {
            lowHSV = new Scalar(55.3, 62.3, 53.8);
            highHSV = new Scalar(213.9, 240.8, 255);
        } else {
            lowHSV = new Scalar(0, 106.3, 198.3);
            highHSV = new Scalar(14.2, 255, 255);
        }

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);
        Mat center = mat.submat(CENTER_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;
        double centerValue = Core.sumElems(center).val[0] / CENTER_ROI.area() / 255;

        left.release();
        right.release();
        center.release();

        boolean stoneLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean stoneRight = rightValue > PERCENT_COLOR_THRESHOLD;
        boolean stoneCenter = centerValue > PERCENT_COLOR_THRESHOLD;
        /*
        if (stoneCenter) {
            location = Location.Center;
        }
        else if (stoneLeft) {
            location = Location.Left;
        }
        else {
            location = Location.Right;
        }
        */

        double max = Math.max(leftValue, Math.max(rightValue, centerValue));

        if (leftValue == max){
            location = Location.Left;
        } else if (rightValue== max) {
            location = Location.Right;
        } else {
            location = Location.Center;
        }

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar red = new Scalar(255, 0, 0);
        Scalar green = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, location == Location.Left? green: red);
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.Right? green: red);
        Imgproc.rectangle(mat, CENTER_ROI, location == Location.Center? green: red);
        Imgproc.putText(mat, location.name(), new Point(220*3,60*2.25),1,5, red);

        return mat;
    }
    public Location getLocation() {
        return location;
    }

}
