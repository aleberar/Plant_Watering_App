package DAO;

//import java.awt.*;

import javafx.scene.paint.Color;

/** Colors is a class that simplifies the work of translating different color formats across the project**/
public class Colors {
    private Colors() {

    }

    /** toHex class turns the javafx version of color into a HEX format
     *
     * @param color is the javafx color format ro be converted
     * @return is the HEX version (converted)
     */
    public static String toHex(javafx.scene.paint.Color color) {
        int r = (int)Math.round(color.getRed() * 255);
        int g = (int)Math.round(color.getGreen() * 255);
        int b = (int)Math.round(color.getBlue() * 255);
        int a = (int)Math.round(color.getOpacity() * 255);

        return String.format("#%02X%02X%02X%02X", r, g, b, a);
    }

    /**from,Hex is a class that converts the HEX version of a color to the javafx version of color
     *
     * @param hex is the hex version of the color
     * @return is the javafx converted version of the hex color
     */
    public static Color fromHex(String hex) {
        return Color.web(hex);
    }

}
