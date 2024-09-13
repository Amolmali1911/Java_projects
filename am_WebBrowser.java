
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

/**
 * A basic multi-window web browser. This class is responsible for
 * creating new windows and for maintaining a list of currently open
 * windows. The program ends when all windows have been closed.
 * The windows are of type am_BrowserWindow. The program also requires
 * the class SimpleDialogs. The first window, which opens when the
 * program starts, goes to "https://www.core2web.in/privacypolicy.html.
 */
public class am_WebBrowser extends Application {

    public static void main(String[] am_args) {
        launch(am_args);
    }
    // ----------------------------------------------------------------------------------------------------

    private ArrayList<am_BrowserWindow> am_openWindows; // list of currently open web browser windows
    private Rectangle2D am_screenRect; // usable area of the primary screen
    private double am_locationX, am_locationY; // location for next window to be opened
    private double am_windowWidth, am_windowHeight; // window size, computed from am_screenRect
    private int am_untitledCount; // how many "Untitled" window titles have been used

    /*
     * Opens a window that will load the am_URL
     * https://www.core2web.in/privacypolicy.html
     * (the front page of the textbook in which this program is an example).
     * Note that the Stage parameter to this method is never used.
     */
    public void start(Stage stage) {

        am_openWindows = new ArrayList<am_BrowserWindow>(); // List of open windows.

        am_screenRect = Screen.getPrimary().getVisualBounds();

        // (am_locationX,am_locationY) will be the location of the upper left
        // corner of the next window to be opened. For the first window,
        // the window is moved a little down and over from the top-left
        // corner of the primary screen's visible bounds.
        am_locationX = am_screenRect.getMinX() + 30;
        am_locationY = am_screenRect.getMinY() + 20;

        // The window size depends on the height and width of the screen's
        // visual bounds, allowing some extra space so that it will be
        // possible to stack several windows, each displaced from the
        // previous one. (For aesthetic reasons, limit the width to be
        // at most 1.6 times the height.)
        am_windowHeight = am_screenRect.getHeight() - 160;
        am_windowWidth = am_screenRect.getWidth() - 130;
        if (am_windowWidth > am_windowHeight * 1.6)
            am_windowWidth = am_windowHeight * 1.6;

        // Open the first window, showing the front page of this textbook.
        am_newBrowserWindow("https://www.google.com/webhp?authuser=1");

    } // end start()

    /**
     * Get the list of currently open windows. The browser windows use this
     * list to construct their Window menus.
     * A package-private method that is meant for use only in
     * am_BrowserWindow.java.
     */
    ArrayList<am_BrowserWindow> getOpenWindowList() {
        return am_openWindows;
    }

    /**
     * Get the number of window titles of the form "Untitled XX" that have been
     * used. A new window that is opened with a null am_URL gets a title of
     * that form. This method is also used in am_BrowserWindow to provide a
     * title for any web page that does not itself provide a title for the page.
     * A package-private method that is meant for use only in
     * am_BrowserWindow.java.
     */
    int am_getNextUntitledCount() {
        return ++am_untitledCount;
    }

    /**
     * Open a new browser window. If am_url is non-null, the window will load that
     * am_URL.
     * A package-private method that is meant for use only in
     * am_BrowserWindow.java.
     * This method manages the locations for newly opened windows. After a window
     * opens, the next window will be offset by 30 pixels horizontally and by 20
     * pixels vertically from the location of this window; but if that makes the
     * window extend outside am_screenRect, the horizontal or vertical position
     * will
     * be reset to its minimal value.
     */
    void am_newBrowserWindow(String am_url) {
        am_BrowserWindow window = new am_BrowserWindow(this, am_url);
        am_openWindows.add(window); // Add new window to open window list.
        window.setOnHidden(e -> {
            // Called when the window has closed. Remove the window
            // from the list of open windows.
            am_openWindows.remove(window);
            System.out.println("Number of open windows is " + am_openWindows.size());
            if (am_openWindows.size() == 0) {
                // Program ends automatically when all windows have been closed.
                System.out.println("Program will end because all windows have been closed");
            }
        });
        if (am_url == null) {
            window.setTitle("am_Untitled " + am_getNextUntitledCount());
        }
        window.setX(am_locationX); // set location and size of the window
        window.setY(am_locationY);
        window.setWidth(am_windowWidth);
        window.setHeight(am_windowHeight);
        window.show();
        am_locationX += 30; // set up location of NEXT window
        am_locationY += 20;
        if (am_locationX + am_windowWidth + 10 > am_screenRect.getMaxX()) {
            // Window would extend past the right edge of the screen,
            // so reset am_locationX to its original value.
            am_locationX = am_screenRect.getMinX() + 30;
        }
        if (am_locationY + am_windowHeight + 10 > am_screenRect.getMaxY()) {
            // Window would extend past the bottom edge of the screen,
            // so reset am_locationY to its original value.
            am_locationY = am_screenRect.getMinY() + 20;
        }
    }

} // end WebBrowser