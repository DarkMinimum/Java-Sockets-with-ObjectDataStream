package sample.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SenderObject implements Serializable {

    private int centerX;
    private int centerY;
    private int size;

    private Color_ colorGradient_1;
    private Color_ colorGradient_2;
    private Color_ colorBorder;

    @Getter
    @Setter
    @AllArgsConstructor
    static class Color_ implements Serializable {
        double r;
        double g;
        double b;

    }
}
