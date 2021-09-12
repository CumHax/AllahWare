// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.util;

import org.lwjgl.util.vector.Quaternion;

public class RubikUtil
{
    public static Quaternion[] cubeletStatus;
    public static int[][][] cubletLookup;
    public static int[][] cubeSides;
    public static int[][] cubeSideTransforms;
    
    public static double easeInOutCubic(final double t) {
        return (t < 0.5) ? (4.0 * t * t * t) : (1.0 - Math.pow(-2.0 * t + 2.0, 3.0) / 2.0);
    }
    
    static {
        RubikUtil.cubeletStatus = new Quaternion[] { new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion(), new Quaternion() };
        RubikUtil.cubletLookup = new int[][][] { { { 17, 9, 0 }, { 20, 16, 3 }, { 23, 15, 6 } }, { { 18, 10, 1 }, { 21, -1, 4 }, { 24, 14, 7 } }, { { 19, 11, 2 }, { 22, 12, 5 }, { 25, 13, 8 } } };
        RubikUtil.cubeSides = new int[][] { { 0, 1, 2, 3, 4, 5, 6, 7, 8 }, { 19, 18, 17, 22, 21, 20, 25, 24, 23 }, { 0, 1, 2, 9, 10, 11, 17, 18, 19 }, { 23, 24, 25, 15, 14, 13, 6, 7, 8 }, { 17, 9, 0, 20, 16, 3, 23, 15, 6 }, { 2, 11, 19, 5, 12, 22, 8, 13, 25 } };
        RubikUtil.cubeSideTransforms = new int[][] { { 0, 0, 1 }, { 0, 0, -1 }, { 0, 1, 0 }, { 0, -1, 0 }, { -1, 0, 0 }, { 1, 0, 0 } };
    }
}
