package com.threerings.getdown.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;
import static java.lang.System.getProperty;

/**
 * Main class that bootstraps a getdown deployment by writing a getdown.txt
 * file with an <strong><i>appbase</i></strong>.
 * <br>
 * Arguments:
 * <ol>
 *     <li>(required) appdir - getdown appdir relative to the user's home directory</li>
 *     <li>(required) appbase - getdown appbase url</li>
 * </ol>
 */
public class Bootstrap {

    static final File HOME = new File(getProperty("user.home"));

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("usage: Bootstrap [appdir] [appbase]");
        }

        // setup appdir
        File appdir = new File(HOME, args[0]);
        appdir.mkdir();

        // write out getdown.txt with appbase
        writeGetdownTxt(HOME, args[1]); // appbase

        // call Getdown using "user.dir" as appdir
        GetdownApp.main(new String[] {HOME.getAbsolutePath()});
    }

    /**
     * Write out a getdown.txt file with an {@code appbase}.
     *
     * @param appbase String
     */
    public static void writeGetdownTxt(File home, String appbase) {
        File f = new File(home, "getdown.txt");
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(f));
            w.write(format("appbase = %s", appbase));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write getdown.txt.", e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing writer.");
                }
            }
        }
    }
}
