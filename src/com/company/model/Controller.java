package com.company.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    public Canvas canvas;
    @FXML
    public TextField depthTextField;
    @FXML
    public TextField angleTextField;
    private int colorIndex = -1;
    private List<Color> colors = new ArrayList<>();
    {
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.ORANGE);
    }
    @FXML
    public void draw(ActionEvent event){
        double angle = Double.parseDouble(angleTextField.getText());
        int depth = Integer.parseInt(depthTextField.getText());

        int startX = new Double(canvas.getWidth() / 2).intValue();
        int startY = new Double(canvas.getHeight() - 50).intValue();

        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        //canvas.getGraphicsContext2D().setStroke(Color.GREEN);
        recursion(startX, startY, 500, Math.toRadians(angle), depth);
    }

    private void recursion(double x0, double y0, double a, double angle, int depth) {
        if(depth == 0) return;
        double b = a * 0.5;//2 * a / (1 + Math.sqrt(3.0));
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double h = Math.sqrt(a*a - b*b);

        double dx = x0 - (a/2 + b)*cos;
        double dy = y0 + (a/2 +b)*sin;
        double cx = x0 + (a/2 +b)*cos;
        double cy = y0 - (a/2 +b)*sin;

        canvas.getGraphicsContext2D().setStroke(nextColor());
        canvas.getGraphicsContext2D().strokeLine(dx, dy, cx, cy);

        double x00 = x0 - h * sin;
        double y00 = y0 - h * cos;

        double ax = x00 - a/2*cos;
        double ay = y00 + a/2*sin;
        double bx = x00 + a/2*cos;
        double by = y00 - a/2*sin;

        canvas.getGraphicsContext2D().strokeLine(ax, ay, bx, by);
        canvas.getGraphicsContext2D().strokeLine(bx, by, cx, cy);
        canvas.getGraphicsContext2D().strokeLine(ax, ay, dx, dy);

        //__________________________________---
        recursion(x0, y0, b, angle, depth-1);
        recursion(x00, y00, b, angle + Math.PI, depth-1);
        recursion((bx+cx)/2, (by+cy)/2, b, angle + Math.PI/2 + Math.asin(b/a), depth-1);
        recursion((ax+dx)/2, (ay+dy)/2, b, angle - Math.asin(b/a) - Math.PI/2, depth-1);
    }

    private Color nextColor(){
        colorIndex++;
        if(colorIndex == colors.size())
            colorIndex = 0;
        return colors.get(colorIndex);
    }
}
